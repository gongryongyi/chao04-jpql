package com.ohgiraffers.section03.projection;

import javax.persistence.*;
import java.util.List;

@Entity(name = "bidirection_category")
@Table(name = "tbl_category")
public class BiDirectionCategory {

    @Id
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;
    @OneToMany(mappedBy = "category")
    private List<BiDirectionMenu> menuList;

    public BiDirectionCategory() {
    }

    public BiDirectionCategory(int categoryCode, String categoryName, Integer refCategoryCode, List<BiDirectionMenu> menuList) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
        this.menuList = menuList;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }

    public List<BiDirectionMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<BiDirectionMenu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "BiDirectionCategory{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                '}';
    }
}
