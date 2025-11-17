# Kunden je Produkt bei exakt 3,00 € — mit Preisrevisions-Treue

Kompaktes, demo-taugliches Szenario: Pro Produkt wird gezählt, wie viele eindeutige Kundinnen/Kunden es zu exakt 3,00 €
gekauft haben — und zwar unter genau derselben Preisrevision, die zum Referenzzeitpunkt T gültig ist.

Die Demo ist bewusst join-intensiv genug, ohne auszuufern.

---

## Was wird gemessen?

Für einen Referenzzeitpunkt T:

- Ermittele die Menge S der Paare (product_id, revision_id), bei der der zum Zeitpunkt T gültige Preis genau 3,00 € (
  brutto, EUR) beträgt.
- Zähle je Produkt die Anzahl distinct Kunden, die dieses Produkt zu 3,00 € gekauft haben und deren OrderLine dieselbe
  `revision_id` referenziert, die in S für T gilt.

Warum „Revisionstreue“ wichtig ist:

- Der numerische Preis (3,00 €) kann über die Zeit identisch sein, aber unterschiedlichen Preisrevisionen angehören. Nur
  mit exakter Revision wird vermieden, Käufe aus verschiedenen Preiszuständen zu vermischen.

---

## Geltungsbereich und Annahmen

- Währung/Preis: EUR, brutto, zwei Dezimalstellen. Bei Fließkommazahlen eine Toleranz festlegen (z. B. 2,995–3,004 →
  3,00).
- Gültigkeitsregel: `valid_from <= T < valid_to` (halb-offen). `valid_to = NULL` = offen.
- Zulässige Bestellungen: Nur realisierte Zustände (z. B. `paid`, `fulfilled`). Vollständig stornierte/retournierte
  Zeilen ausschließen.
- Identitätsebene: Produkt = SKU (keine Familien-Aggregation).
- Zeitzone: Einheitlich (z. B. Europe/Berlin) für T und alle Gültigkeiten.

Außerhalb des Scopes:

- Wie der Preis entstand (Promo, Override, Umrechnung) — ggf. upstream modellierbar, aber hier abstrahiert in Revision +
  bezahltem Preis.

---

## Minimales Datenmodell

```
+-----------+        1         n        +-----------------+
|  Product  |-------------------------->|  PriceRevision  |
|-----------|                           |-----------------|
| product_id|                           | revision_id     |
| sku/name  |                           | product_id (FK) |
+-----------+                           | price_gross     |
                                        | currency        |
                                        | valid_from      |
                                        | valid_to (null=open)
                                        | recorded_at     |
                                        +-----------------+

+----------+      1        n     +--------+      1         n      +---------+
| Customer |-------------------->| Order  |------------------------>| OrderLn |
|----------|                     |--------|                        |---------|
| customer_id                    | order_id                        | line_id |
| ...                            | ordered_at                      | order_id|
+----------+                     | status                          | product_id (FK)
                                 +--------+                        | customer_id (FK)
                                                                   | qty
                                                                   | price_paid_gross
                                                                   | currency
                                                                   | priced_at
                                                                   | price_revision_id_at_pricing (FK -> PriceRevision.revision_id)
                                                                   | price_source [optional]
                                                                   +---------+
```

Design-Kernpunkte:

- PriceRevision ist zeitlich gültig (SCD2-ähnlich). Mehrere Revisionen können den Preis 3,00 € haben, sind aber durch
  `revision_id` unterscheidbar.
- OrderLine speichert den effektiv bezahlten Preis UND die beim Checkout verwendete Preisrevision.

---

## Formale Definition: Kunden je Produkt bei 3,00 € und gleicher Revision

1) Set S (Produkte und Revisionen mit 3,00 € zum Stichtag T)

- S = {(product_id, revision_id)} mit:
    - `valid_from <= T < valid_to` (oder `valid_to IS NULL`)
    - `currency = 'EUR'`
    - `price_gross = 3,00` (ggf. nach definierter Rundung/Toleranz)

2) Kundenanzahl je Produkt

- Für jedes `product_id` in S:
    - Zähle `COUNT(DISTINCT customer_id)` auf OrderLine mit:
        - `OrderLine.product_id = product_id`
        - `OrderLine.price_paid_gross = 3,00` (EUR, gleiche Rundung/Toleranz)
        - `OrderLine.price_revision_id_at_pricing = S.revision_id`
        - `Order.status IN ('paid','fulfilled')`
- Ergebnis: eine Zeile je `product_id` mit `customers_cnt`.

Absichtliche Strenge:

- Nur bei exakter Revisionsgleichheit zählen Käufe — identischer Zahlenwert allein reicht nicht.

---

## Auswertungsfluss (konzeptionell)

```
          Stichtag T
              |
              v
   +----------------------------+
   | PriceRevision              |
   | Preis = 3,00 € @ T         |
   +--------------+-------------+
                  |
                  |  S = {(product_id, revision_id)}
                  v
   +----------------------------------------------+
   | OrderLine JOIN Order                         |
   | Filter:                                      |
   | - product_id ∈ S                             |
   | - price_paid_gross = 3,00 € (EUR)            |
   | - price_revision_id_at_pricing = S.revision  |
   | - Order.status ∈ {paid, fulfilled}           |
   +----------------------+-----------------------+
                          |
                          v
   Gruppiere nach product_id; zähle DISTINCT customer_id
```

---

## Zeitachsen-Beispiel (warum Revision zählt)

```
Zeit →   2024-05-01        2024-05-15          2024-05-31   2024-06-01        …
          |------------------|-------------------|           |---------------------->

Revision  R17 (3,00 €)                                   R23 (3,00 €)

Stichtag T = 2024-05-15 → R17 ist zu T gültig

Käufe:
- 2024-05-16: Kunde A kauft zu 3,00 € mit revision_id_at_pricing = R17 → zählt
- 2024-06-02: Kunde B kauft zu 3,00 € mit revision_id_at_pricing = R23 → zählt NICHT für T
```

---

## Bewusst modellierte Randfälle

- Mehrere 3,00-€-Revisionen über die Zeit → nur die zu T gültige Revision qualifiziert.
- Offene Gültigkeit (`valid_to = NULL`) → gilt bis zur Ablösung.
- Teil-Rückerstattungen → vorab Entscheidung; Empfehlung: zählen, wenn Umsatz realisiert.
- Retouren/Stornos → vollständig retournierte/abgebrochene Zeilen ausschließen.
- Floating-Point-Artefakte → Geldfeld (Decimal) bevorzugen oder klare Rundungsregel.
- Zeitzone → T und Gültigkeiten in derselben TZ behandeln.

---

## Demo-Datenumfang (klein, aber aussagekräftig)

- 10–15 SKUs
- Je SKU 2–4 Preisrevisionen über ~6 Monate; einige Fenster mit exakt 3,00 €
- 200–500 Kunden
- 1.000–2.000 OrderLines; ~10–20 % zu 3,00 %

---

## Validierungs-Playbook

1) Sanity-Checks

- Keine überlappenden PriceRevision-Gültigkeiten je Produkt.
- Alle `OrderLine.price_revision_id_at_pricing` existieren in PriceRevision.
- Währung/Rundung konsistent (EUR, 2 Nachkommastellen).

2) Konzeptionelle Unit-Tests

- Exakt gleiche Revision → zählt; gleicher Preis, andere Revision → zählt nicht.
- Verschiebung von T in anderes Revisionsfenster ändert S und die Counts nachvollziehbar.
- Stornierte/voll retournierten Zeilen tragen nicht bei.

3) Stichproben

- 2–3 SKUs mit bekannter Timeline manuell nachverfolgen.

---

## FAQ

- Kann ich nach Kanal/Segment filtern?
    - Ja. Entsprechende Attribute an Order/OrderLine ergänzen und im Filter berücksichtigen. Die Kernlogik bleibt
      gleich.
- Was ist mit Promotions/Overrides?
    - Deren Effekt steckt im bezahlten Preis und der erfassten Revision. Für die Zählung reichen Revisions-Treue und
      3,00-€-Match.
- Darf T ein Datum statt Timestamp sein?
    - Ja — die Grenzdefinition (z. B. Tagesende) muss konsistent auf alle Gültigkeiten angewendet werden.

---

## Warum das funktioniert

- Trennt numerische Gleichheit (3,00 €) von der fachlichen Identität eines Preiszustands (Revision).
- Reproduzierbar: Gleiches T → gleiches Set S → gleiche Zählung.
- Technologie-agnostisch: Funktioniert unabhängig davon, wie komplex die Preisfindung upstream ist.

## SQL Beispiele:

````sql
WITH s_prices AS (SELECT pr.product_id, pr.revision_id
                  FROM price_revision pr
                  WHERE pr.currency = 'EUR'
                    AND pr.price_gross = 3.00
                    AND pr.valid_from <= :T
                    AND (pr.valid_to > :T OR pr.valid_to IS NULL))
SELECT COUNT(DISTINCT ol.customer_id) AS customers_paid_3_eur_same_revision
FROM order_lines ol
         JOIN orders o
              ON o.order_id = ol.order_id
         JOIN s_prices s
              ON s.product_id = ol.product_id
                  AND s.revision_id = ol.price_revision_id_at_pricing
WHERE ol.currency = 'EUR'
  AND ol.price_paid_gross = 3.00 -- alternativ: ROUND(ol.price_paid_gross, 2) = 3.00
  AND o.status IN ('paid', 'fulfilled');
-- Optional: Käufe auf einen Zeitraum einschränken (z. B. um T herum)
-- AND o.ordered_at BETWEEN :T - INTERVAL '30 day' AND :T + INTERVAL '30 day'
````

````sql
WITH s_prices AS (SELECT pr.product_id, pr.revision_id
                  FROM price_revision pr
                  WHERE pr.currency = 'EUR'
                    AND pr.price_gross = 3.00
                    AND pr.valid_from <= :T
                    AND (pr.valid_to > :T OR pr.valid_to IS NULL))
SELECT s.product_id,
       COUNT(DISTINCT ol.customer_id) AS customers_cnt
FROM s_prices s
         JOIN order_lines ol
              ON ol.product_id = s.product_id
                  AND ol.price_revision_id_at_pricing = s.revision_id
                  AND ol.currency = 'EUR'
                  AND ol.price_paid_gross = 3.00
         JOIN orders o
              ON o.order_id = ol.order_id
                  AND o.status IN ('paid', 'fulfilled')
GROUP BY s.product_id
ORDER BY s.product_id;
````
