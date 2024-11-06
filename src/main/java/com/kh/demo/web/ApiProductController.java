package com.kh.demo.web;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.req.product.ReqSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
//@Controller
@RestController // @Controller + @ResponseBody
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ApiProductController {

  private final ProductSVC productSVC;

  //단건조회
  @GetMapping("/{pid}")
//  @ResponseBody
  public ApiResponse<Product> findById(@PathVariable("pid") Long pid){
    ApiResponse<Product> res = null;
    Optional<Product> optionalProduct = productSVC.findById(pid);

    if(optionalProduct.isPresent()){
      Product product = optionalProduct.get();
      res = ApiResponse.createApiResponse("00","success",product);
    }else{
      res = ApiResponse.createApiResponse("01","not found",null);
    }

    return res;
  }

  //목록
  @GetMapping
//  @ResponseBody
  public ApiResponse<List<Product>> all() {
    ApiResponse<List<Product>> res = null;
    List<Product> products = productSVC.findAll();
    if (products.size() != 0) {
      res = ApiResponse.createApiResponse("00", "success", products);
    }else{
      res = ApiResponse.createApiResponse("01", "not found", null);
    }
    return res;
  }

  //상품등록
  @PostMapping
  public ApiResponse<Product> add(@RequestBody ReqSave reqSave){
    log.info("reqSave={}",reqSave);
    ApiResponse<Product> res = null;

    Product product = new Product();
    BeanUtils.copyProperties(reqSave, product);
    Long pid = productSVC.save(product);

    Optional<Product> optionalProduct = productSVC.findById(pid);
    if(optionalProduct.isPresent()) {
      Product savedProduct = optionalProduct.get();
      res = ApiResponse.createApiResponse("00", "success", savedProduct);
    }else{
      res = ApiResponse.createApiResponse("99", "fail", null);
    }
    return res;
  }
}
