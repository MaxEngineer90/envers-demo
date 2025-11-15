package de.ml.tutorials.envers.demo.entity.views.product;

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
public class ProductAudViewId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long productId;
    private Integer rev;
}