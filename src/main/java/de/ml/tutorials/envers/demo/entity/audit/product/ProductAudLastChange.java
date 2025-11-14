package de.ml.tutorials.envers.demo.entity.audit.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Table(name = "V_PRODUCT_AUD_LAST_CHANGE")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductAudLastChange {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "REV", nullable = false)
    private Long rev;

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
}