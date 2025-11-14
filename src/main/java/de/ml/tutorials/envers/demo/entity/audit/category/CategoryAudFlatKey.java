package de.ml.tutorials.envers.demo.entity.audit.category;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CategoryAudFlatKey implements Serializable {
    private Long id;
    private Long rev;
}