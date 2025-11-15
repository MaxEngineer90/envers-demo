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
@ToString(exclude = {"orders", "orderLines"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "CUSTOMER")
@Audited
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false, length = 120)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 120)
    private String lastName;

    @Column(name = "EMAIL", length = 254)
    private String email;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "customer")
    @NotAudited
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    @NotAudited
    private List<OrderLine> orderLines;
}