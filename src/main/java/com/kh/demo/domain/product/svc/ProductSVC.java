package com.kh.demo.domain.product.svc;

import com.kh.demo.domain.entity.Product;

public interface ProductSVC {
  // 등록
  Long save(Product product);
}
