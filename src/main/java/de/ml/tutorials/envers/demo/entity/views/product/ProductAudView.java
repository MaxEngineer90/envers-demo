package de.ml.tutorials.envers.demo.entity.views.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PRODUCT_AUD_VIEW")
@IdClass(ProductAudViewId.class)
@Immutable
public class ProductAudView {

    @Id
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Id
    @Column(name = "REV")
    private Integer rev;

    @Column(name = "REVTYPE")
    private Integer revType;

    @Column(name = "SKU")
    private String sku;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATED_AT")
    private Instant createdAt;
}