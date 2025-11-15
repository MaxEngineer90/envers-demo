package de.ml.tutorials.envers.demo.dto;

public record ProductRevisionDto(
        ProductDto product,
        RevisionMetadataDto metadata
) {}