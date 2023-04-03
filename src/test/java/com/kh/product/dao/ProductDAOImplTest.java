package com.kh.product.dao;

import com.kh.product.domain.entity.Product;
import com.kh.product.domain.product.dao.ProductDAO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@SpringBootTest
public class ProductDAOImplTest {

  @Autowired
  ProductDAO productDAO;

  //등록
  @Test
  @DisplayName("상품등록")
  void save(){
    Product product = new Product();
    product.setPname("복사기");
    product.setQuantity(10L);
    product.setPrice(1000000L);

    Long pId = productDAO.save(product);
    log.info("pId={}",pId);
    Assertions.assertThat(pId).isGreaterThan(0L);
  }

  //조회
  @Test
  @DisplayName("상품조회")
  void findById(){
    Long pId = 163L;
    Optional<Product> product = productDAO.findById(pId);
//    if(product.isPresent()) {
//      log.info("product={}", product.get());
//    }else{
//      log.info("조회한 결과 없음");
//    }
//    Assertions.assertThat(product.stream().count())
//        .isEqualTo(1);
    Product findedProduct = product.orElseThrow();// 없으면 java.util.NoSuchElementException
    Assertions.assertThat(findedProduct.getPname()).isEqualTo("복사기");
    Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(10L);
    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(1000000L);
  }

  //수정
  @Test
  @DisplayName("상품수정")
  void update() {
    Long pId = 163L;
    Product product = new Product();
    product.setPname("복사기_수정");
    product.setQuantity(20L);
    product.setPrice(2000000L);
    int updatedRowCount = productDAO.update(pId, product);
    Optional<Product> findedProduct = productDAO.findById(pId);

    Assertions.assertThat(updatedRowCount).isEqualTo(1);
    Assertions.assertThat(findedProduct.get().getPname()).isEqualTo(product.getPname());
    Assertions.assertThat(findedProduct.get().getQuantity()).isEqualTo(product.getQuantity());
    Assertions.assertThat(findedProduct.get().getPrice()).isEqualTo(product.getPrice());
  }

  //삭제
  @Test
  @DisplayName("상품삭제")
  void delete() {
    Long pId = 165L;
    int deletedRowCount = productDAO.delete(pId);
    Optional<Product> findedProduct = productDAO.findById(pId);

    Assertions.assertThatThrownBy(()->findedProduct.orElseThrow())
        .isInstanceOf(NoSuchElementException.class);
  }

  //목록
  @Test
  @DisplayName("상품목록")
  void findAll() {
    List<Product> list = productDAO.findAll();

    Assertions.assertThat(list.size()).isGreaterThan(0);
  }

  @Test
  @DisplayName("상품존재")
  void isExist(){
    Long pId = 244L;
    boolean exist = productDAO.isExist(pId);
    Assertions.assertThat(exist).isTrue();
  }
  @Test
  @DisplayName("상품무")
  void isExist2(){
    Long pId = 0L;
    boolean exist = productDAO.isExist(pId);
    Assertions.assertThat(exist).isFalse();
  }

  @Test
  @DisplayName("전체 삭제")
  void deleteAll(){
    int deletedRows = productDAO.deleteAll();
    int countOfRecord = productDAO.countOfRecord();
    Assertions.assertThat(countOfRecord).isEqualTo(0);
  }

  @Test
  @DisplayName("레코드 건수")
  void countOfRecord(){
    int countOfRecord = productDAO.countOfRecord();
    log.info("countOfRecord={}",countOfRecord);
  }


  @Test
  @DisplayName("부분삭제")
  void deleteParts(){
    List<Long> pIds = Arrays.asList(326L,327L,325L,341L);
    int rows = productDAO.deleteParts(pIds);
    Assertions.assertThat(rows).isEqualTo(pIds.size());
  }
}
