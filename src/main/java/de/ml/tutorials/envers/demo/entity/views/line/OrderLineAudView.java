package de.ml.tutorials.envers.demo.entity.views.line;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ORDER_LINE_AUD_VIEW")
@IdClass(OrderLineAudViewId.class)
@Immutable
public class OrderLineAudView {

    @Id
    @Column(name = "LINE_ID")
    private Long lineId;

    @Id
    @Column(name = "REV")
    private Integer rev;

    @Column(name = "REVTYPE")
    private Integer revType;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "QTY")
    private Integer quantity;

    @Column(name = "PRICE_PAID_GROSS")
    private BigDecimal pricePaidGross;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "PRICED_AT")
    private Instant pricedAt;

    @Column(name = "PRICE_REVISION_ID_AT_PRICING")
    private Long priceRevisionIdAtPricing;
}