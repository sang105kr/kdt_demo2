package com.kh.demo.domain.product.svc;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC{

  private final ProductDAO productDAO;

  @Override
  public Long save(Product product) {
    long rows = productDAO.save(product);
    return rows;
  }

}
