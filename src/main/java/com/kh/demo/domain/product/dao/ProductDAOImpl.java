package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor // final 필드를 매개값으로 갖는 생성자를 자동 생성해준다.
public class ProductDAOImpl implements ProductDAO{

  private final NamedParameterJdbcTemplate template;

//  public ProductDAOImpl(NamedParameterJdbcTemplate template){
//    this.template = template;
//  }

  @Override
  public Long save(Product product) {
    //sql
    StringBuffer sql = new StringBuffer();
    sql.append("insert into product(product_id,pname,quantity,price) ");
    sql.append("values(product_product_id_seq.nextval, :pname, :quantity, :price) ");

    //sql수행
    Map param = Map.of("pname",product.getPname(),"quantity",product.getQuantity(),"price",product.getPrice());
    long rows = template.update(sql.toString(),param);

    log.info("rows={}", rows);

    return rows;
  }

  @Override
  public List<Product> findAll() {
    //sql
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id,pname,quantity,price ");
    sql.append("  from product ");
    sql.append("order by product_id desc ");

    //db요청
    // BeanPropertyRowMapper : 자바 entity클래스와 db레코드를 자동 매핑
    List<Product> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Product.class));

    return list;
  }
}
