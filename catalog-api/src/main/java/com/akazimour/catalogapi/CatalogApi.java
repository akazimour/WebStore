package com.akazimour.catalogapi;

import com.akazimour.catalogapi.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@ComponentScan
public interface CatalogApi {

    @RequestMapping(method = RequestMethod.GET,value = "api/catalog/product/{id}")
    public ProductDto findByProductId(@PathVariable Long id);
}
