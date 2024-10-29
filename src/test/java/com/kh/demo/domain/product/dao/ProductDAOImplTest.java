package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ProductDAOImplTest {

  @Autowired
  ProductDAO productDAO;

  @Test
  void save(){
    log.info("save()호출됨!");
    Product product = new Product();
    product.setPname("의자");
    product.setPrice(1000000L);
    product.setQuantity(10L);

    Long insertedRows = productDAO.save(product);
    log.info("insertedRows={}",insertedRows);
  }


}
