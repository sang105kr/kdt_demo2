package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;

public interface ProductDAO {
  // 등록
  Long save(Product product);
}
