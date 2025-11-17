package de.ml.tutorials.envers.demo.repository;

import com.blazebit.persistence.CriteriaBuilderFactory;
import de.ml.tutorials.envers.demo.entity.views.line.OrderLineAudView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderLineAudViewBlazeRepository {
    private final CriteriaBuilderFactory cbf;
    @PersistenceContext
    private EntityManager em;

    public List<OrderLineAudView> findByOrderIdOrderedByRevAsc(Long orderId) {
        return cbf.create(em, OrderLineAudView.class)
                .where("orderId").eq(orderId)
                .orderByAsc("rev")
                .getResultList();
    }

    public List<OrderLineAudView> findByProductIdAndRev(Long productId, Integer rev) {
        return cbf.create(em, OrderLineAudView.class)
                .where("productId").eq(productId)
                .where("rev").eq(rev)
                .getResultList();
    }

    public List<OrderLineAudView> findByCustomerIdOrderedByRevAsc(Long customerId) {
        return cbf.create(em, OrderLineAudView.class)
                .where("customerId").eq(customerId)
                .orderByAsc("rev")
                .getResultList();
    }
}
