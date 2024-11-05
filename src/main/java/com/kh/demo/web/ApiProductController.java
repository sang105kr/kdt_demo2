package com.kh.demo.web;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ApiProductController {

  private final ProductSVC productSVC;

  //단건조회
  @GetMapping("/{pid}")
  @ResponseBody
  public Product findById(@PathVariable("pid") Long pid){

    Optional<Product> optionalProduct = productSVC.findById(pid);
    Product product = optionalProduct.orElseThrow();

    return product;
  }

  //목록
  @GetMapping("/all")
  @ResponseBody
  public List<Product> all(){

    List<Product> list = productSVC.findAll();

    return list;
  }

}
