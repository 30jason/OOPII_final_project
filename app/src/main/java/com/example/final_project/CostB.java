package com.example.final_project;


import java.io.Serializable;

public class CostB implements Serializable {
    public String costTitle;
    public String costDate;
    public String costMoney;
    public String costType;
    public static int budget = 0;

    @Override
    public String toString() {
        return "CostBean{" +
                "costTitle='" + costTitle + '\'' +
                ", costDate='" + costDate + '\'' +
                ", costMoney='" + costMoney + '\'' +
                ", costtype='" + costType + '\'' +
                '}';
    }

    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }

    public String getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(String costMoney) {
        this.costMoney = costMoney;
    }

    public String getCostTitle() {
        return costTitle;
    }

    public void setCostTitle(String costTitle) {
        this.costTitle = costTitle;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costTitle = costType;
    }

    public static Integer getBudget(){ return budget;}

    public static void setBudget(int a){budget = a;}

}
