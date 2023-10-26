package com.ohgiraffers.section04.paging;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PagingTests {
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
    public void 페이징_API를_이용한_조회_테스트(){
        //given
        int offset = 10;  //조회를 건너띄고자하는 하는 행의 갯수
        int limit = 5;    //조회하고자 하는 행의 갯수
        //when
        String jpql = "SELECT m FROM menu_section04 m ORDER BY m.menuCode DESC";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setFirstResult(offset)  //11번쨰 값부터가 1번쨰
                .setMaxResults(limit)
                .getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);


    }
}
