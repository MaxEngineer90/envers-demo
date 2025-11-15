package de.ml.tutorials.envers.demo.repository.audit;



import de.ml.tutorials.envers.demo.entity.audit.product.ProductAudInterval;
import de.ml.tutorials.envers.demo.entity.audit.product.ProductAudIntervalKey;

import java.util.List;

public interface ProductAudIntervalRepository extends ReadOnlyRepository<ProductAudInterval, ProductAudIntervalKey> {

    List<ProductAudInterval> findByKeyIdOrderByKeyRevFromAsc(Long id);
}