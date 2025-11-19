package de.ml.tutorials.envers.demo.controller;

import de.ml.tutorials.envers.demo.service.PricingAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class PricingAnalyticsController {

    private final PricingAnalyticsService service;

    @GetMapping(value = "/customers-per-product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Long, Long>> customersPerProduct(
            @RequestParam("asOf") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant asOf,
            @RequestParam("price") BigDecimal price,
            @RequestParam("currency") String currency
    ) {
        Map<Long, Long> result = service.countCustomersPerProductAt(asOf, price, currency);
        return ResponseEntity.ok(result);
    }
}
