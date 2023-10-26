package com.ohgiraffers.section07.subquery;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SubqueryTests {
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
    public void 서브쿼리를_이용한_메뉴_조회_테스트(){
        //given
        String categoryNameParameter = "한식";
        //when
        String jpql = "SELECT m FROM menu_section07 m WHERE m.categoryCode" +
                " = (SELECT c.categoryCode FROM category_section07 c WHERE c.categoryName = :categoryName)";  //join 대신 서브쿼리 사용

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter("categoryName", categoryNameParameter)
                .getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
