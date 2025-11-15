package de.ml.tutorials.envers.demo.repository.audit;


import de.ml.tutorials.envers.demo.entity.audit.product.ProductAudFlat;
import de.ml.tutorials.envers.demo.entity.audit.product.ProductAudFlatKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductAudFlatRepository extends ReadOnlyRepository<ProductAudFlat, ProductAudFlatKey> {

    List<ProductAudFlat> findByKeyIdOrderByKeyRevAsc(Long id);

    Page<ProductAudFlat> findByKeyId(Long id, Pageable pageable);

    Optional<ProductAudFlat> findTopByKeyIdOrderByKeyRevDesc(Long id);
}