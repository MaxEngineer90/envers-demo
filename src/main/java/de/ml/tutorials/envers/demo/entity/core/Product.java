package de.ml.tutorials.envers.demo.entity.core;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString(exclude = {"priceRevisions", "orderLines"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "PRODUCT")
@Audited
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "SKU", nullable = false, length = 50)
    private String sku;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotAudited
    private List<PriceRevision> priceRevisions;

    @OneToMany(mappedBy = "product")
    @NotAudited
    private List<OrderLine> orderLines;
}