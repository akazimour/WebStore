package com.akazimour.catalogservice.repository;
import com.akazimour.catalogservice.entity.Product;
import com.akazimour.catalogservice.entity.QProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, QuerydslPredicateExecutor<Product>, QuerydslBinderCustomizer<QProduct>{

    @Override
    default void customize(QuerydslBindings bindings, QProduct product) {
        bindings.bind(product.productName).first((path, value) -> path.likeIgnoreCase(value +"%"));
        bindings.bind(product.categories.any().categoryName).first((path, value) -> path.likeIgnoreCase(value +"%"));
        bindings.bind(product.productPrice).all((path, values) ->
                {
                    if (values.size() != 2)
                        return Optional.empty();
                Iterator<? extends Integer> iterator = values.iterator();
                Integer from = iterator.next();
                Integer to = iterator.next();
            return Optional.of(path.between(from,to));
        });
    }

}
