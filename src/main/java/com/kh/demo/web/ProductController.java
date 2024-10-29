package com.kh.demo.web;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.form.product.SaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductSVC productSVC;

//  public ProductController(ProductSVC productSVC){
//    this. productSVC = productSVC;
//  }

  //등록양식
  @GetMapping("/add")
  public String addForm(){

    return "/product/add";//상품등록화면
  }
  //등록처리
  @PostMapping("/add")  // "post /products/add"
  public String add(SaveForm saveForm){
    //사용자가 입력한정보
    log.info("panme={}, price={}, quantity={}", saveForm.getPname(),saveForm.getPrice(), saveForm.getQuantity());

    //상품테이블에 저장
    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setPrice(saveForm.getPrice());
    product.setQuantity(saveForm.getQuantity());

    productSVC.save(product);

    return "/product/detailForm"; //상품상세화면
  }

  //목록양식
  @GetMapping  // get /products
  public String findAll(){
    List<Product> list = productSVC.findAll();

    return "/product/all"; // view이름
  }
}
