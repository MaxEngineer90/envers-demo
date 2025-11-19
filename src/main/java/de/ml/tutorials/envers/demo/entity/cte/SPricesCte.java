package de.ml.tutorials.envers.demo.entity.cte;

import com.blazebit.persistence.CTE;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@CTE
@Entity
@Getter
@NoArgsConstructor
@Immutable
public class SPricesCte {

    @Id
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "REVISION_ID")
    private Long revisionId;

    @Column(name = "PRICE_GROSS")
    private BigDecimal priceGross;
}
