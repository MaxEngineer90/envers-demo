package de.ml.tutorials.envers.demo.entity.audit.supplier;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SupplierAudFlatKey implements Serializable {
    private Long id;
    private Long rev;
}