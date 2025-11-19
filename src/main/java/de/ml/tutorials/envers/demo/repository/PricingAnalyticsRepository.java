package de.ml.tutorials.envers.demo.repository;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import de.ml.tutorials.envers.demo.entity.cte.SPricesCte;
import de.ml.tutorials.envers.demo.entity.views.line.OrderLineAudView;
import de.ml.tutorials.envers.demo.entity.views.order.OrderAudView;
import de.ml.tutorials.envers.demo.entity.views.price.PriceRevisionAudView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PricingAnalyticsRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PricingAnalyticsRepository.class);
    private static final BigDecimal PRICE_TOLERANCE = new BigDecimal("0.005");

    private final CriteriaBuilderFactory cbf;
    private final EntityManager em;

    @Transactional(readOnly = true)
    public Map<Long, Long> countCustomersPerProductAt(
            Instant asOf,
            BigDecimal targetPrice,
            String currency
    ) {
        LOG.debug("Query customers per product at {} for price {} {}", asOf, targetPrice, currency);

        BigDecimal priceLow = targetPrice.subtract(PRICE_TOLERANCE);
        BigDecimal priceHigh = targetPrice.add(PRICE_TOLERANCE);

        CriteriaBuilder<Tuple> cb = cbf.create(em, Tuple.class);

        buildValidPricesCte(cb, priceLow, priceHigh, asOf, currency);
        buildMainQuery(cb, priceLow, priceHigh, currency);
        setQueryParameters(cb, priceLow, priceHigh);

        List<Tuple> rows = cb.getResultList();
        Map<Long, Long> result = mapResults(rows);

        LOG.debug("Found {} products with matching customers", result.size());
        return result;
    }

    private void buildValidPricesCte(
            CriteriaBuilder<Tuple> cb,
            BigDecimal priceLow,
            BigDecimal priceHigh,
            Instant asOf,
            String currency
    ) {
        cb.with(SPricesCte.class)
                .from(PriceRevisionAudView.class, "pr")
                .bind("productId").select("pr.productId")
                .bind("revisionId").select("pr.revisionId")
                .where("pr.currency").eq(currency)
                .where("pr.priceGross").betweenExpression(":priceLow").andExpression(":priceHigh")
                .where("pr.validFrom").le(asOf)
                .whereOr()
                .where("pr.validTo").gt(asOf)
                .where("pr.validTo").isNull()
                .endOr()
                .end();
    }

    private void buildMainQuery(
            CriteriaBuilder<Tuple> cb,
            BigDecimal priceLow,
            BigDecimal priceHigh,
            String currency
    ) {
        cb.from(SPricesCte.class, "s")
                // JOIN OrderLine mit Revisions-Match
                .innerJoinOn(OrderLineAudView.class, "ol")
                .on("ol.productId").eqExpression("s.productId")
                .on("ol.priceRevisionIdAtPricing").eqExpression("s.revisionId")
                .on("ol.currency").eq(currency)
                .on("ol.pricePaidGross").betweenExpression(":priceLow").andExpression(":priceHigh")
                .end()
                // JOIN Order mit Status-Filter
                .innerJoinOn(OrderAudView.class, "o")
                .on("o.orderId").eqExpression("ol.orderId")
                .on("lower(o.status)").in("paid", "fulfilled")
                .end()
                // Aggregation
                .groupBy("s.productId")
                .select("s.productId", "productId")
                .select("count(distinct ol.customerId)", "customersCnt")
                .orderByAsc("s.productId");
    }

    private void setQueryParameters(
            CriteriaBuilder<Tuple> cb,
            BigDecimal priceLow,
            BigDecimal priceHigh
    ) {
        cb.setParameter("priceLow", priceLow);
        cb.setParameter("priceHigh", priceHigh);
    }


    private Map<Long, Long> mapResults(List<Tuple> rows) {
        Map<Long, Long> result = new LinkedHashMap<>();

        for (Tuple row : rows) {
            Long productId = extractLong(row, "productId");
            Long customerCount = extractLong(row, "customersCnt");
            result.put(productId, customerCount);
        }

        return result;
    }

    private Long extractLong(Tuple row, String alias) {
        return ((Number) row.get(alias)).longValue();
    }
}
