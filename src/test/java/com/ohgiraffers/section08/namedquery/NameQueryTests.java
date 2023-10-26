package com.ohgiraffers.section08.namedquery;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NameQueryTests {
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
    public void 동적쿼리를_이용한_조회_테스트() {
        //given
        String searchName = "한우";
        int searchCategoryCode = 4;
        //when
        StringBuilder jpql = new StringBuilder("SELECT m FROM menu_section08 m  ");
        if (searchName != null && !searchName.isEmpty() && searchCategoryCode > 0) {
            jpql.append("WHERE ");
            jpql.append("m.menuName LIkE '%' || :menuName || '%' ");
            jpql.append("AND ");
            jpql.append("m.categoryCode = :categoryCode ");

        } else {
            if (searchName != null && !searchName.isEmpty()) {
                jpql.append("WHERE ");
                jpql.append("m.menuName LIkE '%' || :menuName || '%' ");
            } else if (searchCategoryCode > 0) {
                jpql.append("WHERE ");
                jpql.append("m.categoryCode = :categoryCode ");
            }

        }
        TypedQuery<Menu> query = entityManager.createQuery(jpql.toString(), Menu.class);
        if (searchName != null && !searchName.isEmpty() && searchCategoryCode > 0) {
            query.setParameter("menuName", searchName);
            query.setParameter("categoryCode", searchCategoryCode);
        } else {
            if (searchName != null && !searchName.isEmpty()) {
                query.setParameter("menuName", searchName);
            } else if (searchCategoryCode > 0) {
                query.setParameter("categoryCode", searchCategoryCode);
            }
        }
        List<Menu> menuList = query.getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    public void 네임드쿼리를_이용한_조회_테스트(){
        //when
        List<Menu> menuList = entityManager.createNamedQuery("menu_section08.selectMenuList", Menu.class)
                .getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }
}