package de.ml.tutorials.envers.demo.repository;

import com.blazebit.persistence.CriteriaBuilderFactory;
import de.ml.tutorials.envers.demo.entity.views.price.PriceRevisionAudView;
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
public class PriceRevisionAudViewBlazeRepository {
    private final CriteriaBuilderFactory cbf;
    @PersistenceContext
    private EntityManager em;

    public List<PriceRevisionAudView> findByProductIdOrderedByRevAsc(Long productId) {
        return cbf.create(em, PriceRevisionAudView.class)
                .where("productId").eq(productId)
                .orderByAsc("rev")
                .getResultList();
    }

    public Optional<PriceRevisionAudView> findLatestByProductId(Long productId) {
        List<PriceRevisionAudView> list = cbf.create(em, PriceRevisionAudView.class)
                .where("productId").eq(productId)
                .orderByDesc("rev")
                .setMaxResults(1)
                .getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
    }

    public List<PriceRevisionAudView> findByProductIdAndRevType(Long productId, Integer revType) {
        return cbf.create(em, PriceRevisionAudView.class)
                .where("productId").eq(productId)
                .where("revType").eq(revType)
                .orderByAsc("rev")
                .getResultList();
    }
}
