package de.ml.tutorials.envers.demo.dto;

import de.ml.tutorials.envers.demo.entity.product.ProductStatus;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String sku,
        String name,
        BigDecimal currentPrice,
        ProductStatus status,
        Long categoryId,
        Long supplierId
) {}
