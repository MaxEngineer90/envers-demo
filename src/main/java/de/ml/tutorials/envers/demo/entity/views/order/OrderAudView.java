package de.ml.tutorials.envers.demo.entity.views.order;

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
@Table(name = "ORDERS_AUD_VIEW")
@IdClass(OrderAudViewId.class)
@Immutable
public class OrderAudView {

    @Id
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Id
    @Column(name = "REV")
    private Integer rev;

    @Column(name = "REVTYPE")
    private Integer revType;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "ORDERED_AT")
    private Instant orderedAt;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "TOTAL_GROSS")
    private BigDecimal totalGross;

    @Column(name = "CURRENCY")
    private String currency;
}
