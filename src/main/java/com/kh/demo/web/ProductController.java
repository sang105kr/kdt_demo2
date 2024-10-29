package com.kh.demo.web;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.form.product.AllForm;
import com.kh.demo.web.form.product.DetailForm;
import com.kh.demo.web.form.product.SaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
  public String findAll(Model model){
    List<Product> list = productSVC.findAll();
    List<AllForm> all = new ArrayList<>();

    for (Product product : list) {
      AllForm allForm = new AllForm();
      allForm.setProductId(product.getProductId());
      allForm.setPname(product.getPname());
      all.add(allForm);
    }

    model.addAttribute("all",all);

    return "/product/all"; // view이름
  }

  //상품단건조회
  @GetMapping("/{id}/detail")  // Get /products/2/detail
  public String findById(
          @PathVariable("id") Long productId,
          Model model){
    log.info("productId={}", productId);

    Optional<Product> findedProduct = productSVC.findById(productId);
    Product product = findedProduct.orElseThrow();

    DetailForm detailForm = new DetailForm();
    detailForm.setProductId(product.getProductId());
    detailForm.setPname(product.getPname());
    detailForm.setPrice(product.getPrice());
    detailForm.setQuantity(product.getQuantity());

    model.addAttribute("detailForm",detailForm);

    return "/product/detailForm";
  }

  //단건삭제
  @GetMapping("/{id}/del")
  public String deleteById(@PathVariable("id") Long productId){
    log.info("productId={}",productId);

    int rows = productSVC.deleteById(productId);

    return "redirect:/products"; // 302 get rediredUtrl: http://localhost:9080/products
  }
}
