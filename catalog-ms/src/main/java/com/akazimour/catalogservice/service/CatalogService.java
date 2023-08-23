package com.akazimour.catalogservice.service;

import com.akazimour.catalogservice.dto.ProductPriceElement;
import com.akazimour.catalogservice.entity.HistoryData;
import com.akazimour.catalogservice.model.CategoriesDto;
import com.akazimour.catalogservice.model.ProductDto;
import com.akazimour.catalogservice.entity.Categories;
import com.akazimour.catalogservice.entity.Product;
import com.akazimour.catalogservice.mapper.CatalogMapper;
import com.akazimour.catalogservice.repository.CategoryRepository;
import com.akazimour.catalogservice.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CatalogMapper catalogMapper;

    @PersistenceContext
    EntityManager em;

    public Categories createNewCategory(CategoriesDto categoriesDto) {

        Optional<Categories> categories = categoryRepository.findByCategoryName(categoriesDto.getCategoryName());
        if (categories.isPresent()) {
            throw new IllegalArgumentException("The category already exist!");
        } else
            return categoryRepository.save(catalogMapper.dtoToCategory(categoriesDto));

    }

    public Product createNewProduct(ProductDto productDto) {
        return productRepository.save(catalogMapper.DtoToProduct(productDto));
    }

    @Transactional
    public Product createProductWithCategory(String categoryName, ProductDto productDto) {
        Categories category = categoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new IllegalArgumentException("This category does not exist!"));
        Product product = catalogMapper.DtoToProduct(productDto);
        category.addProductToCategory(product);
        product.addCategoryToProduct(category);
        productRepository.save(product);
        categoryRepository.save(category);

        return product;
    }

    public Product modifyProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("This product does not exist with id: " + productId));
        if (productId != productDto.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unmatched product id in the request and path !");
        } else
            product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        if (productDto.getCategories() != null) {
            List<CategoriesDto> categories = productDto.getCategories();
            product.setCategories(null);
            product.setCategories(catalogMapper.dtosToCategories(categories));
            return productRepository.save(product);
        } else
            return productRepository.save(product);
    }

    @Transactional
    public List<HistoryData<Product>> getProductHistory(Long id) {
        List resultList = AuditReaderFactory.get(em)
                .createQuery()
                .forRevisionsOfEntity(Product.class, false, true)
                .add(AuditEntity.property("id").eq(id))
                .getResultList().stream().map(o -> {
                    Object[] objArray = (Object[]) o;
                    DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) objArray[1];
                    Product product = (Product) objArray[0];
                    product.getProductName().length();
                    product.getProductPrice().intValue();
                    List<Categories> categories = product.getCategories();
                    if (categories != null)
                        product.getCategories().size();
                    return new HistoryData<Product>(
                            product,
                            (RevisionType) objArray[2],
                            defaultRevisionEntity.getId(),
                            defaultRevisionEntity.getRevisionDate()
                    );
                }).toList();
        return resultList;
    }

    @Transactional
    public List<HistoryData<Product>> getProductRicesWithHistory(Long id) {
        List resultList = AuditReaderFactory.get(em)
                .createQuery()
                .forRevisionsOfEntity(Product.class, false, true)
                .add(AuditEntity.property("id").eq(id))
                .getResultList().stream().map(o -> {
                    Object[] objArray = (Object[]) o;
                    DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) objArray[1];
                    Product product = (Product) objArray[0];
                    product.getProductName().length();
                    product.getProductPrice().intValue();
                    List<Categories> categories = product.getCategories();
                    if (categories != null)
                        product.getCategories().size();
                    return new HistoryData<Product>(
                            product,
                            (RevisionType) objArray[2],
                            defaultRevisionEntity.getId(),
                            defaultRevisionEntity.getRevisionDate()
                    );
                }).toList();
        return resultList;
    }
    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    @Cacheable("priceHistoryCache")
   public List<ProductPriceElement> getPriceElements (List<HistoryData<Product>> productHistory){
        List<ProductPriceElement> priceElements = new ArrayList<>();
        for (int i = 0; i < productHistory.size(); i++) {
            Integer productPrice = productHistory.get(i).getData().getProductPrice();
            Date date = productHistory.get(i).getDate();
            priceElements.add(i,new ProductPriceElement(productPrice,date));
        };
       List<ProductPriceElement> elements = priceElements.stream().filter(distinctByKey(p -> p.getPrice())).collect(Collectors.toList());
       return elements;
    }
}