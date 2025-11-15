package de.ml.tutorials.envers.demo.entity.views.price;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PRICE_REVISION_AUD_VIEW")
@Immutable
public class PriceRevisionAudView {

    @Id
    @Column(name = "REVISION_ID")
    private Long revisionId;

    @Column(name = "REV")
    private Integer rev;

    @Column(name = "REVTYPE")
    private Integer revType;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRICE_GROSS")
    private BigDecimal priceGross;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "VALID_FROM")
    private Instant validFrom;

    @Column(name = "VALID_TO")
    private Instant validTo;

    @Column(name = "RECORDED_AT")
    private Instant recordedAt;
}