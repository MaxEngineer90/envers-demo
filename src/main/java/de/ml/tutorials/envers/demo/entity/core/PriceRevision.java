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
@ToString(exclude = {"product"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "PRICE_REVISION")
@Audited
public class PriceRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVISION_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Column(name = "PRICE_GROSS", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceGross;

    @Column(name = "CURRENCY", nullable = false, length = 3)
    private String currency;

    @Column(name = "VALID_FROM", nullable = false)
    private Instant validFrom;

    @Column(name = "VALID_TO")
    private Instant validTo;

    @Column(name = "RECORDED_AT", nullable = false)
    private Instant recordedAt;
}
