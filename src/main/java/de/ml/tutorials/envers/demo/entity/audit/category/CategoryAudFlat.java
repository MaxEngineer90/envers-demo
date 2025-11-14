package  de.ml.tutorials.envers.demo.entity.audit.category;

import de.ml.tutorials.envers.demo.entity.audit.CategoryAudFlatKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "V_CATEGORY_AUD_FLAT")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoryAudFlat {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false)),
            @AttributeOverride(name = "rev", column = @Column(name = "REV", nullable = false))
    })
    private CategoryAudFlatKey key;

    @Column(name = "CODE", length = 50)
    private String code;

    @Column(name = "NAME", length = 150)
    private String name;

    @Column(name = "REVTYPE")
    private Integer revtype;

    @Column(name = "CODE_MOD")
    private Integer codeMod;

    @Column(name = "NAME_MOD")
    private Integer nameMod;

    // Convenience
    @Transient public Long getId() { return key != null ? key.getId() : null; }
    @Transient public Long getRev() { return key != null ? key.getRev() : null; }
}