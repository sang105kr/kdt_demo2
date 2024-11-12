package com.kh.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TryCatchFinallyTest {
  @Test
  void t1(){
    int[] arr = new int[]{1, 2, 3};
    log.info("{}",1);
    try {
      // 예외가 발생될수 있는 코드
      int val = arr[3];   // ArrayIndexOutOfBoundsException

      // catch절의 매개변수 타입은 하위타입이 위에, 상위타입이 아래오도록 배치
    } catch(ArrayIndexOutOfBoundsException e) {
      log.info("err={}",e.toString());
    } catch (Exception e) {
      // 예외처리
      log.info("err={}",e.toString());
    }  finally {
      // 예외 유무와 상관없이 수행되는 코드
      log.info("{}","예외 유무와 상관없이 수행되는 코드");
    }
    log.info("{}",2);
  }


  void t2(){
    try {
      Class.forName("java.lang.String");
    } catch (ClassNotFoundException e) {
      log.info("e={}",e);
    }
  }

  void t3() throws Exception{
//    try {
      t4();
//    }catch (Exception e){
//
//    }
  }


  void t4() throws Exception{
    Class.forName("java.lang.String");
  }
}
