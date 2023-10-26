package com.ohgiraffers.section06.join;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JoinTests {
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
    public void 내부조인을_이용한_조회_테스트(){
        //when
        String jpql = "SELECT m FROM menu_section06 m JOIN m.category c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    public void 외부조인을_이용한_조회_테스트(){
        //when
        String jpql = "SELECT m.menuName, c.categoryName FROM menu_section06 m RIGHT JOIN m.category c " +
                  " ORDER BY m.category.categoryCode";
        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 컬렉션조인을_이용한_조회_테스트(){
        //when
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section06 c LEFT JOIN c.menuList m";
        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();
        //then
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 세타조인을_이용한_조회_테스트(){
        //when
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section06 c, menu_section06 m";
        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();
        //then
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 패치조인을_이용한_조회_테스트(){
        //when
        String jpql = "SELECT m FROM menu_section06 m JOIN FETCH m.category c";  //처음에 이 구문을 실행할때 조인된 구문을 다 실행합니다. 없으면 일반적인 이너 조인이 발생함
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
