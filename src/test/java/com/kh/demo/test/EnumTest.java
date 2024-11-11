package com.kh.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

@Slf4j
public class EnumTest {

  @Test
  void t1(){
    Day today = Day.MONDAY;
    //Enum 값 출력
    log.info("Today is:" + today);

    //모든 Enum값 출력
    for (Day day : Day.values()) {
      log.info("day={}",day);
    }
  }

  @Test
  void t2(){

    //모든 enuum 값 출력 및 타입출력
    for(Day2 day: Day2.values()){
      log.info("{} is a {}", day, day.getType());
    }

    Day2 sunday = Day2.valueOf("SUNDAY");
    log.info("sunday={}", sunday);
  }

  @Test
  @DisplayName("enum타입을 사용 안했을때")
  void t3(){
    Random random = new Random();
//    for (int i = 0; i < 6; i++) {
//      int num = random.nextInt(6) + 1; //1~6까지의 랜덤한 정수 발생
//      log.info("num={}", num);
//    }
    int num = random.nextInt(6) + 1;

    switch (num) {
      case Die.ONE:
        log.info("one");
        break;
      case Die.TWO:
        log.info("two");
        break;
      case Die.THREE:
        log.info("three");
        break;
      case Die.FOUR:
        log.info("four");
        break;
      case Die.FIVE:
        log.info("five");
        break;
      case Die.SIX:
        log.info("six");
        break;
      default:
        log.info("유효하지 않은 주사위눈!");
        break;
    }
  }

  @Test
  @DisplayName("enum타입을 사용했을때")
  void t4(){
    Random random = new Random();
//    for (int i = 0; i < 6; i++) {
//      int num = random.nextInt(6) + 1; //1~6까지의 랜덤한 정수 발생
//      log.info("num={}", num);
//    }
    int num = random.nextInt(6) + 1;

    Die3 rolledDie = null;
    for (Die3 die : Die3.values()) {
      if(die.getNum() == num){
        rolledDie = die;
        break;
      }
    }

    if (rolledDie != null) {
      log.info(rolledDie.getMeaning());
    }else{
      log.info("유효하지 않은 주사위눈!");
    }
  }



  @Test
  void t5(){
//    Die die = Die.ONE;
//    Die2 die2 = Die2.ONE;
    Die3 die3 = Die3.ONE;
  }
  static enum Day{
    SUNDAY,
    MONDAY,
    TUESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;
  }

  static enum Day2{
    //상수 객체
    SUNDAY("weekend"),
    MONDAY("weekday"),
    TUESDAY("weekday"),
    THURSDAY("weekday"),
    FRIDAY("weekday"),
    SATURDAY("weekend");

    //속성
    private String type;
    //생성자
    Day2(String type){
      this.type = type;
    }

    //메소드
    public String getType(){
      return type;
    }
  }

  static class Die {
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
  }

  static class Die2 {
    public static final String ONE = "one";
    public static final String TWO = "two";
    public static final String THREE = "three";
    public static final String FOUR = "four";
    public static final String FIVE = "five";
    public static final String SIX = "six";
  }

  static enum Die3 {
    ONE(1,"one"),
    TWO(2,"two"),
    THREE(3,"three"),
    FORUR(4,"four"),
    FIVE(5,"five"),
    SIX(6,"six");

    private int num;
    private String meaning;

    Die3(int num, String meaning) {
      this.num = num;
      this.meaning = meaning;
    }

    public int getNum() {
      return num;
    }

    public String getMeaning() {
      return meaning;
    }
  }
}


