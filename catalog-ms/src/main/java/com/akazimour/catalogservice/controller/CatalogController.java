package com.akazimour.catalogservice.controller;

import com.akazimour.catalogservice.api.CatalogControllerApi;
import com.akazimour.catalogservice.dto.ProductPriceElement;
import com.akazimour.catalogservice.entity.Categories;
import com.akazimour.catalogservice.model.ProductPriceDto;
import com.akazimour.catalogservice.entity.HistoryData;
import com.akazimour.catalogservice.entity.Product;
import com.akazimour.catalogservice.model.CategoriesDto;
import com.akazimour.catalogservice.mapper.CatalogMapper;
import com.akazimour.catalogservice.model.HistoryDataProductDto;
import com.akazimour.catalogservice.model.ProductDto;

import com.akazimour.catalogservice.repository.HistoryDataMapper;
import com.akazimour.catalogservice.repository.ProductRepository;
import com.akazimour.catalogservice.service.CatalogService;
import com.akazimour.catalogapi.CatalogApi;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;

@RestController
public class CatalogController implements CatalogApi, CatalogControllerApi {


    @Autowired
    CatalogService catalogService;
    @Autowired
    CatalogMapper catalogMapper;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    HistoryDataMapper historyDataMapper;
    @Autowired
    MethodArgumentResolverHelper resolverHelper;
    @Autowired
    NativeWebRequest request;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return CatalogControllerApi.super.getRequest();
    }

    @Override
    public ResponseEntity<CategoriesDto> createCategory(CategoriesDto categoriesDto) {
        Categories newCategory = catalogService.createNewCategory(categoriesDto);
        newCategory.setProducts(null);
        CategoriesDto newCategoriesDto = catalogMapper.categoryToDto(newCategory);
        return ResponseEntity.ok(newCategoriesDto);
    }
    @Override
    public ResponseEntity<ProductDto> addNewProductWithCategory(ProductDto productDto, String categoryName) {
        if (categoryName != null) {
            Product productWithCategory = catalogService.createProductWithCategory(categoryName, productDto);
            productWithCategory.getCategories().forEach(c->c.setProducts(null));
            ProductDto dto = catalogMapper.productToDto(productWithCategory);
            dto.getCategories().forEach(c->c.setProducts(null));
            return ResponseEntity.ok(dto);
        } else {
            Product newProduct = catalogService.createNewProduct(productDto);
            newProduct.getCategories().forEach(p->p.setProducts(null));
            ProductDto body = catalogMapper.productToDto(newProduct);
            body.getCategories().forEach(p->p.setProducts(null));
            return ResponseEntity.ok(body);
        }
    }
    @Override
    public ResponseEntity<ProductDto> modifyProduct(Long productId, ProductDto productDto) {
        Product product = catalogService.modifyProduct(productId, productDto);
        product.getCategories().forEach(categories -> categories.setProducts(null));
        return ResponseEntity.ok(catalogMapper.productToDto(product));
    }
    @Override
    public ResponseEntity<Void> deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("This product does not exist with id: " + productId));
        productRepository.delete(product);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
// Teljes product history product id megadásával
    @Override
    public ResponseEntity<List<HistoryDataProductDto>> getFullProductHistoryById(Long id) {
        List<HistoryData<Product>> productHistory = catalogService.getProductHistory(id);
        List<HistoryData<ProductDto>> productDtosWithHystory = new ArrayList<>();
        productHistory.forEach(productHistoryData ->{
            HistoryDataProductDto historyDataProductDto = historyDataMapper.courseHistoryDtataToDto(productHistoryData);
            productDtosWithHystory.add(historyDataMapper.historyDataProductDtoToHistoryData(historyDataProductDto));
});
        return ResponseEntity.ok(historyDataMapper.historyDataDtoToHistoryProductDto(productDtosWithHystory));
    }

    public void configPageable(@SortDefault("id") Pageable pageable) {}
    public void configurePredicate(@QuerydslPredicate(root = Product.class) Predicate predicate) {}
    @Override
    public ResponseEntity<List<ProductDto>> queryDslSearch( @Valid Integer page,@Valid Integer size,@Valid List<String> sort) {
        Pageable pageable = resolverHelper.createPageable(this.getClass(), "configPageable", request);
        Predicate predicate = resolverHelper.createPredicate(this.getClass(), "configurePredicate", request);
        Iterable<Product> products = productRepository.findAll(predicate, pageable);
        products.forEach(product -> product.getCategories().forEach(categories -> categories.setProducts(null)));
        List<ProductDto> body = catalogMapper.iterableProductListToProductDtoList(products);
        return ResponseEntity.ok(body);

    }
    // Product id megadásával
    @Override
    public ResponseEntity<List<ProductPriceDto>> getProductPriceHistoryById(Long id) {
        List<HistoryData<Product>> productHistory = catalogService.getProductHistory(id);
        List<ProductPriceElement> priceElements = catalogService.getPriceElements(productHistory);
        return ResponseEntity.ok(historyDataMapper.priceElementListToProductPriceDtoList(priceElements));
    }

    @Override
    public com.akazimour.catalogapi.dto.ProductDto findByProductId(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product does not exist with id: " + id));
        product.getCategories().forEach(p->p.setProducts(null));
        com.akazimour.catalogapi.dto.ProductDto productDto = catalogMapper.ProductTofeignProductDto(product);
        productDto.getCategories().forEach(p->p.setProducts(null));
        return productDto;
    }
}
