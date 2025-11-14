package de.ml.tutorials.envers.demo.entity.audit.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Table(name = "V_PRODUCT_AUD_INTERVAL")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductAudInterval {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false)),
            @AttributeOverride(name = "revFrom", column = @Column(name = "REV_FROM", nullable = false))
    })
    private ProductAudIntervalKey key;

    @Column(name = "REV_TO")
    private Long revTo;

    @Column(name = "REVTYPE")
    private Integer revtype;

    @Column(name = "SKU", length = 50)
    private String sku;

    @Column(name = "NAME", length = 200)
    private String name;

    @Column(name = "CURRENT_PRICE", precision = 15, scale = 2)
    private BigDecimal currentPrice;

    @Column(name = "STATUS", length = 30)
    private String status;

    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @Column(name = "SUPPLIER_ID")
    private Long supplierId;

    // Convenience
    @Transient public Long getId() { return key != null ? key.getId() : null; }
    @Transient public Long getRevFrom() { return key != null ? key.getRevFrom() : null; }
}