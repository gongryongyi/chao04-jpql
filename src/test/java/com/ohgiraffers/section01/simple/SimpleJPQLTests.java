package com.ohgiraffers.section01.simple;

import org.junit.jupiter.api.*;

import javax.persistence.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimpleJPQLTests {
    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    @Test
    public void TypedQuery를_이용한_단일메뉴_조회_테스트(){
        //when
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode = 7";  //엔터티 명은 항상 별칭이 필요하다.
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class); //실행할 구문 ,그때 반환받을 데이터 타입
        String resultMenuName = query.getSingleResult();
        //then
        assertEquals("민트미역국", resultMenuName);
    }

    @Test
    public void Query를_이용한_단일메뉴_조회_테스트(){
        //when
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode = 7";
         Query query = entityManager.createQuery(jpql);  //반환값의 타입이 없으면 Query
         Object resultMenuName = query.getSingleResult();
         //then
        assertEquals("민트미역국", resultMenuName);


    }

    @Test
    public void TypedQuery를_이용한_단일행_조회_테스트(){
        //when
        String jpql = "SELECT m FROM menu_section01 as m WHERE m.menuCode = 7"; //전체 컬럼 조회하기 위해서는 그냥 별칭만 써도됨 ex) m
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        Menu foundMenu = query.getSingleResult();
        //then
        assertEquals(7, foundMenu.getMenuCode());
        System.out.println(foundMenu);
    }

    @Test
    public void TypedQuery를_이용한_다중행_조회_테스트(){
        //when
        String jpql = "SELECT m FROM menu_section01 as m ";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> foundMenuList = query.getResultList();
        //then
        assertNotNull(foundMenuList);
        foundMenuList.forEach(System.out::println);

    }

    @Test
    public void Query를_이용한_다중행_조회_테스트(){
        //when
        String jpql = "SELECT m FROM menu_section01 as m ";
        Query query = entityManager.createQuery(jpql);
        List foundMenuList = query.getResultList();  //<Menu> 붙어도 되고 안붙어도됨
        //then
        assertNotNull(foundMenuList);
        foundMenuList.forEach(System.out::println);

    }

    @Test
    public void distinct를_활용한_중복제거_여러_행_조회_테스트(){
        //when
        String jpql = "SELECT DISTINCT m.categoryCode FROM menu_section01 m";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        List<Integer> categoryCodeList = query.getResultList();
        //then
        assertNotNull(categoryCodeList);
        categoryCodeList.forEach(System.out::println);
    }

    @Test
    public void in_연산자를_활용한_조회_테스트(){
        //카테고리 코드가 6이거나 10인 menu
        String jpql = "SELECT m FROM menu_section01 m WHERE m.categoryCode IN (6, 10)";
        Query query = entityManager.createQuery(jpql);
        List categoryCodeList = query.getResultList();
        //then
        assertNotNull(categoryCodeList);
        categoryCodeList.forEach(System.out::println);
    }

    @Test
    public void like_연산자를_활용한_조회_테스트(){
        // "마늘"이 메뉴 이름으로 들어간 menu 조회
        String jpql = "SELECT m FROM menu_section01 m WHERE menuName LIKE '%마늘%'";
        Query query = entityManager.createQuery(jpql);
        List categoryCodeList = query.getResultList();
        //then
        assertNotNull(categoryCodeList);
        categoryCodeList.forEach(System.out::println);

    }
}
