package de.ml.tutorials.envers.demo.entity.audit.product;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductAudIntervalKey implements Serializable {
    private Long id;
    private Long revFrom;
}