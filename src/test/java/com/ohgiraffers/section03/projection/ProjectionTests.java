package com.ohgiraffers.section03.projection;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectionTests {
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
    public void 단일_엔터티_프로젝션_테스트(){
        //when
        String jpql = "SELECT m FROM menu_section03 m";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        //then
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        menuList.get(0).setMenuName("test1");
        entityTransaction.commit();
    }

    @Test
    public void 양방향_연관관계_엔터티_프로젝션_테스트(){
        //given
        int menuCodeParameter = 3;
        //when
        String jpql = "SELECT m.category FROM bidirection_menu m WHERE m.menuCode = :menuCode";
        BiDirectionCategory categoryOfMenu = entityManager.createQuery(jpql, BiDirectionCategory.class)
                .setParameter("menuCode", menuCodeParameter)
                .getSingleResult();

        //then
        System.out.println(categoryOfMenu);
        System.out.println(categoryOfMenu.getMenuList());

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        categoryOfMenu.setCategoryName("test2");
        categoryOfMenu.getMenuList().get(1).setMenuName("test3");
        entityTransaction.commit();
    }


    @Test
    public void 임베디드_타입_프로젝션_테스트(){
   //when
        String jpql = "SELECT m.menuInfo FROM embedded_menu m";
        List<MenuInfo> menuInfoList = entityManager.createQuery(jpql, MenuInfo.class).getResultList();
        //then
        assertNotNull(menuInfoList);
        menuInfoList.forEach(System.out::println);
    }

    @Test
    public void TypedQuery를_이용한_스칼라_타입_프로젝션_테스트() {
        //when
        String jpql = "SELECT c.categoryName FROM category_section03 c";
        List<String> categoryNameList = entityManager.createQuery(jpql, String.class).getResultList();
        //then
        assertNotNull(categoryNameList);
        categoryNameList.forEach(System.out::println);
    }

    @Test
    public void Query를_이용한_스칼라_타입_프로젝션_테스트(){
        //when
        String jpql = "SELECT c.categoryCode, c.categoryName FROM category_section03 c ";
        List<Object[]> categoryList = entityManager.createQuery(jpql).getResultList();
        //then
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            Arrays.stream(row).forEach(System.out::println);
        });
    }
    @Test
    public void new_명령어를_확용한_프로젝션_테스트(){
        //when
        String jpql = "SELECT new com.ohgiraffers.section03.projection.CategoryInfo(c.categoryCode, c.categoryName) FROM category_section03 c ";
        List<CategoryInfo> categoryList = entityManager.createQuery(jpql, CategoryInfo.class).getResultList();
        //then
        assertNotNull(categoryList);
        categoryList.forEach(System.out::println);

    }
}
