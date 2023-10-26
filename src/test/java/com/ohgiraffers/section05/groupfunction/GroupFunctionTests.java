package com.ohgiraffers.section05.groupfunction;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupFunctionTests {
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
    public void 특정_카테고리의_등록된_메뉴_수_조회(){
        //given
        int categoryCodeParameter = 3;
        //when
        String jpql = "SELECT COUNT(m.menuPrice) FROM menu_section05 m WHERE m.categoryCode = : categoryCode";
        long countOfMenu = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCodeParameter)
                .getSingleResult();

        //then
        assertTrue(countOfMenu >=0);
        System.out.println(countOfMenu);
    }

    @Test
    public void count를_제외한_다른_그훕함수의_조회결과가_없는_경우_테스트(){
         //given
        int categorycodeParameter = 3;
        //when
        String jpql = "SELECT SUM(m.menuPrice) FROM menu_section05 m WHERE m.categoryCode = : categoryCode";
        //then
        assertThrows(NullPointerException.class, ()->{long sumOfPrice = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categorycodeParameter)
                .getSingleResult();
        }); //assertThrows = 이구문을 수행하면 익셉션이 발생할 것이다. NullPointerException.class = 발생 예상하는 타입을 적은거임 {수행하고자 하는 function}

        assertDoesNotThrow( ()->{
            Long sumOfPrice = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categorycodeParameter)
                .getSingleResult();
            System.out.println(sumOfPrice);
        });  //assertDoesNotThrow = 이구문을 수행하면 익셉션을 발생하지 않습니다.


    }

    @Test
    public void groupby절과_having절을_사용한_조회_테스트(){
        //given
        long minPrice = 50000L;   //int 타입이면 안됨
        //when
        String jpql = "SELECT m.categoryCode, SUM(m.menuPrice)" +
                    " FROM menu_section05 m "+
                    " GROUP BY m.categoryCode" +
                    " HAVING SUM(m.menuPrice) >= :minPrice";

        List<Object[]> sumPriceOfCategoryList = entityManager.createQuery(jpql, Object[].class)
                .setParameter("minPrice", minPrice)
                .getResultList();
        //then
        assertNotNull(sumPriceOfCategoryList);
        sumPriceOfCategoryList.forEach(row -> {
            Arrays.stream(row).forEach(System.out::println);  //object배열을 setream으로 람다식에서 만든다음 프린트 아웃
        });
    }
}
