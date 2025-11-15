package de.ml.tutorials.envers.demo.repository.audit;

import de.ml.tutorials.envers.demo.entity.audit.product.ProductAudLastChange;

import java.util.Optional;

public interface ProductAudLastChangeRepository extends ReadOnlyRepository<ProductAudLastChange, Long> {

    Optional<ProductAudLastChange> findById(Long id);
}