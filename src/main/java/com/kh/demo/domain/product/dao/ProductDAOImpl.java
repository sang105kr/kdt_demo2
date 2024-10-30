package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    //Map param = Map.of("pname",product.getPname(),"quantity",product.getQuantity(),"price",product.getPrice());
    // spring been객체(product)의 멤버필드와 sql의 매개변수(=파라미터) 이름을 매칭한 정보를 반환
    SqlParameterSource param = new BeanPropertySqlParameterSource(product);

    // template.update()가 수행된 레코드의 특정 컬럼값을 읽어오는 용도
    KeyHolder keyholder = new GeneratedKeyHolder();
    long rows = template.update(sql.toString(),param,keyholder,new String[]{"product_id"});
    log.info("rows={}", rows);
    //case1) 1개의 컬럼값을 읽어올때
//    long pid = keyholder.getKey().longValue();//상품아이디
    //case2) 2개 이상의 컬럼값을 읽어올때
    Number pidNumber = (Number)keyholder.getKeys().get("product_id");
    long pid = pidNumber.longValue();
    return pid;  //상품 아이디 반환
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

  @Override
  public Optional<Product> findById(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id,pname,quantity,price ");
    sql.append("  from product ");
    sql.append(" where product_id = :productId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("productId",productId);

    Product product = null;
    try {
      product = template.queryForObject(
              sql.toString(),
              param,
              BeanPropertyRowMapper.newInstance(Product.class));
    } catch (EmptyResultDataAccessException e) { //조회 레코드가 없으면 예외 발생
      return Optional.empty();
    }

    return Optional.of(product);
  }

  @Override
  public int deleteById(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from product ");
    sql.append("where product_id = :productId ");

    Map<String, Long> param = Map.of("productId",productId);
    int rows = template.update(sql.toString(), param);
    return rows;
  }
}
