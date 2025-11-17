package de.ml.tutorials.envers.demo.repository;

import com.blazebit.persistence.CriteriaBuilderFactory;
import de.ml.tutorials.envers.demo.entity.views.customer.CustomerAudView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerAudViewBlazeRepository {

    private final CriteriaBuilderFactory cbf;
    @PersistenceContext
    private EntityManager em;

    public List<CustomerAudView> findByEmail(String email) {
        return cbf.create(em, CustomerAudView.class)
                .where("email").eq(email)
                .orderByAsc("rev")
                .getResultList();
    }

    public List<CustomerAudView> findByCustomerIdOrderedByRevAsc(Long customerId) {
        return cbf.create(em, CustomerAudView.class)
                .where("customerId").eq(customerId)
                .orderByAsc("rev")
                .getResultList();
    }

    public List<CustomerAudView> findByLastNameOrderedByRevDesc(String lastName) {
        return cbf.create(em, CustomerAudView.class)
                .where("lastName").eq(lastName)
                .orderByDesc("rev")
                .getResultList();
    }
}
