package de.ml.tutorials.envers.demo.service.audit;

import de.ml.tutorials.envers.demo.dto.ProductRevisionDto;

import java.util.List;
import java.util.Optional;

public interface ProductAuditService {
    List<ProductRevisionDto> listHistory(Long productId);
    Optional<ProductRevisionDto> getLastChange(Long productId);
    Optional<ProductRevisionDto> getAtRevision(Long productId, long revision);
}
