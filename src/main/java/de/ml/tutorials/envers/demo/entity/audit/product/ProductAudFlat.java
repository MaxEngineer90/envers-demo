package de.ml.tutorials.envers.demo.entity.audit.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Table(name = "V_PRODUCT_AUD_FLAT")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductAudFlat {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false)),
            @AttributeOverride(name = "rev", column = @Column(name = "REV", nullable = false))
    })
    private ProductAudFlatKey key;

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

    @Column(name = "REVTYPE")
    private Integer revtype;

    @Column(name = "SKU_MOD")
    private Integer skuMod;

    @Column(name = "NAME_MOD")
    private Integer nameMod;

    @Column(name = "CURRENT_PRICE_MOD")
    private Integer currentPriceMod;

    @Column(name = "STATUS_MOD")
    private Integer statusMod;

    @Column(name = "CATEGORY_ID_MOD")
    private Integer categoryIdMod;

    @Column(name = "SUPPLIER_ID_MOD")
    private Integer supplierIdMod;


    // Convenience-Getter für MapStruct/REST
    @Transient
    public Long getId() {
        return key != null ? key.getId() : null;
    }

    @Transient
    public Long getRev() {
        return key != null ? key.getRev() : null;
    }
}