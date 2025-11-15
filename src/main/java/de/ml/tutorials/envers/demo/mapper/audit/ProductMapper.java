package de.ml.tutorials.envers.demo.mapper.audit;

import de.ml.tutorials.envers.demo.dto.ProductDto;
import de.ml.tutorials.envers.demo.entity.audit.product.ProductAudFlat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "sku", source = "sku"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "currentPrice", source = "currentPrice"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "categoryId", source = "category.id"),
            @Mapping(target = "supplierId", source = "supplier.id")
    })
    ProductDto toDto(ProductAudFlat entity);
}
