package com.ohgiraffers.section02.parameter;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParameterBindingTests {
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
    public void 이름_기준_파라미터_바인딩_메뉴_목록_조회_테스트(){

        //given
        String menuNameParameter = "한우딸기국밥";
        //when
        String jpql = "SELECT m FROM menu_section02 m WHERE m.menuName = :menuName";  //menuName 아런이름의 파라미터를 넘기겠다.
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter("menuName", menuNameParameter)
                .getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    public void 위치_기준_파라미터_바인딩_메뉴_목록_조회_테스트(){
        //given
        String menuNameParameter = "한우딸기국밥";
        //when
        String jpql = "SELECT m FROM menu_section02 m WHERE m.menuName = ?1";  //1부터 시작한다.
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter(1, menuNameParameter)
                .getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
