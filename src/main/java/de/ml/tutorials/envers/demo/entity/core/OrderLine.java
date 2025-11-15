package de.ml.tutorials.envers.demo.entity.core;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString(exclude = {"order", "product", "customer", "priceRevisionAtPricing"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ORDER_LINE")
@Audited
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINE_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PRICE_REVISION_ID_AT_PRICING", nullable = false)
    private PriceRevision priceRevisionAtPricing;

    @Column(name = "QTY", nullable = false, precision = 10, scale = 3)
    private BigDecimal quantity;

    @Column(name = "PRICE_PAID_GROSS", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePaidGross;

    @Column(name = "CURRENCY", nullable = false, length = 3)
    private String currency;

    @Column(name = "PRICED_AT", nullable = false)
    private Instant pricedAt;
}