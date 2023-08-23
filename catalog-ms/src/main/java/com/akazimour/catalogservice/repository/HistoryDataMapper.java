package com.akazimour.catalogservice.repository;
import com.akazimour.catalogservice.dto.ProductPriceElement;
import com.akazimour.catalogservice.entity.Categories;
import com.akazimour.catalogservice.model.CategoriesDto;
import com.akazimour.catalogservice.model.ProductDto;
import com.akazimour.catalogservice.entity.HistoryData;
import com.akazimour.catalogservice.model.HistoryDataProductDto;
import com.akazimour.catalogservice.entity.Product;
import com.akazimour.catalogservice.model.ProductPriceDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryDataMapper {
    HistoryDataProductDto courseHistoryDtataToDto(HistoryData<Product> productHistoryData);

    HistoryData historyDataProductDtoToHistoryData (HistoryDataProductDto historyDataProductDto);

   List<HistoryDataProductDto> historyDataDtoToHistoryProductDto (List<HistoryData<ProductDto>> historyDataList);

    @Mapping(target = "products", ignore = true)
    Categories dtoToCategory (CategoriesDto categoriesDto);
    @Mapping(target = "products", ignore = true)
    CategoriesDto categoryToDto (Categories categories);

    List<ProductPriceDto> priceElementListToProductPriceDtoList(List<ProductPriceElement> priceElements);

}
