package com.akazimour.catalogservice.mapper;

import com.akazimour.catalogservice.entity.Categories;
import com.akazimour.catalogservice.model.CategoriesDto;
import com.akazimour.catalogservice.entity.Product;
import com.akazimour.catalogservice.model.ProductDto;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


import java.util.List;

@Mapper(componentModel = "spring")
public interface CatalogMapper {
    @Named("sum")
    @Mapping(target = "categories.products", ignore = true)
    ProductDto productToDto (Product product);
    @Named("sel")
    @Mapping(target = "categories.products", ignore = true)
    Product DtoToProduct(ProductDto productDto);
    @IterableMapping(qualifiedByName = "sum")
    List<ProductDto> productsToDtos (List<Product> products);
    @IterableMapping(qualifiedByName = "sel")
    List<Product> DtosToProducts (List<ProductDto> productDtos);
    @Named("block")
    @Mapping(target = "products", ignore = true)
    Categories dtoToCategory (CategoriesDto categoriesDto);
    @Named("hide")
    @Mapping(target = "products", ignore = true)
    CategoriesDto categoryToDto (Categories categories);
    @IterableMapping(qualifiedByName = "block")
    List<Categories> dtosToCategories (List<CategoriesDto> categoriesDto);
    @IterableMapping(qualifiedByName = "hide")
    List<CategoriesDto> CategoriesToDtos (List<Categories> categories);
    @IterableMapping(qualifiedByName = "sum")
    List<ProductDto> iterableProductListToProductDtoList (Iterable<Product> iterable);
    @Mapping(target = "categories.products", ignore = true)
    com.akazimour.catalogapi.dto.ProductDto ProductTofeignProductDto  (Product product);
   @Mapping(target = "categories.products", ignore = true)
    Product feignPdDtoToProduct (com.akazimour.catalogapi.dto.ProductDto productDto);

}
