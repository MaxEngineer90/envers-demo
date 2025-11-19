package de.ml.tutorials.envers.demo.service;

import de.ml.tutorials.envers.demo.repository.PricingAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PricingAnalyticsService {
    private final PricingAnalyticsRepository repository;

    public Map<Long, Long> countCustomersPerProductAt(Instant asOf, BigDecimal price, String currency) {
        return repository.countCustomersPerProductAt(asOf, price, currency);
    }
}
