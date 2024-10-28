package com.kh.demo.domain.product.svc;

public interface ProductSVC {
  // 등록
  Long save(String pname, int price, int quantity);
}
