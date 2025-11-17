package de.ml.tutorials.envers.demo.repository;

import com.blazebit.persistence.CriteriaBuilderFactory;
import de.ml.tutorials.envers.demo.entity.views.product.ProductAudView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductAudViewBlazeRepository {

    private final CriteriaBuilderFactory cbf;
    @PersistenceContext
    private EntityManager em;

    public List<ProductAudView> findByProductIdOrderedByRevAsc(Long productId) {
        return cbf.create(em, ProductAudView.class)
                .where("productId").eq(productId)
                .orderByAsc("rev")
                .getResultList();
    }

    public Optional<ProductAudView> findByProductIdAndRev(Long productId, Integer rev) {
        List<ProductAudView> list = cbf.create(em, ProductAudView.class)
                .where("productId").eq(productId)
                .where("rev").eq(rev)
                .setMaxResults(1)
                .getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
    }

    public Optional<ProductAudView> findLatestByProductId(Long productId) {
        List<ProductAudView> list = cbf.create(em, ProductAudView.class)
                .where("productId").eq(productId)
                .orderByDesc("rev")
                .setMaxResults(1)
                .getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
    }
}
