package com.kh.demo.domain.product.dao;

public interface ProductDAO {
  // 등록
  Long save(String pname, int price, int quantity);
}
