package de.ml.tutorials.envers.demo.entity.views.customer;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CUSTOMER_AUD_VIEW")
@IdClass(CustomerAudViewId.class)
@Immutable
public class CustomerAudView {

    @Id
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Id
    @Column(name = "REV")
    private Integer rev;

    @Column(name = "REVTYPE")
    private Integer revType;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CREATED_AT")
    private Instant createdAt;
}