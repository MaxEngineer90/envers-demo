package de.ml.tutorials.envers.demo.dto;

import org.hibernate.envers.RevisionType;

import java.time.Instant;

public record RevisionMetadataDto(
        long revision,
        Instant revisionTimestamp,
        RevisionType revisionType
) {}