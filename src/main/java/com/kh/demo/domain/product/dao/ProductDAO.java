package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
  // 등록
  Long save(Product product);

  // 목록
  List<Product> findAll();

  //조회
  Optional<Product> findById(Long productId);
}
