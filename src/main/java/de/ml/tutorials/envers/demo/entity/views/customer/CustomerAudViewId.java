package de.ml.tutorials.envers.demo.entity.views.customer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerAudViewId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long customerId;
    private Integer rev;
}