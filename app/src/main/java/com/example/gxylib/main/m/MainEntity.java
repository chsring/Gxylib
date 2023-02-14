package com.example.gxylib.main.m;

import com.example.gxylib.main.BaseEntity;

import java.io.Serializable;
import java.util.List;


public class MainEntity extends BaseEntity {

    public List<DataBean> data;

    public static class DataBean implements Serializable {
        private int id;
        private int goodsId;
        private String goodsName;
        private String goodsType;
        private int optionsId;
        private String optionsName;
        private String optionsDetail;
        private double discountPrice;
        private double listPrice;
        private int stock;
        private int balancePay;
        private int directPay;
        private boolean virtualGoods;
        private boolean specialCounter;
        private boolean validFlag;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public int getOptionsId() {
            return optionsId;
        }

        public void setOptionsId(int optionsId) {
            this.optionsId = optionsId;
        }

        public String getOptionsName() {
            return optionsName;
        }

        public void setOptionsName(String optionsName) {
            this.optionsName = optionsName;
        }

        public String getOptionsDetail() {
            return optionsDetail;
        }

        public void setOptionsDetail(String optionsDetail) {
            this.optionsDetail = optionsDetail;
        }

        public double getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(double discountPrice) {
            this.discountPrice = discountPrice;
        }

        public double getListPrice() {
            return listPrice;
        }

        public void setListPrice(double listPrice) {
            this.listPrice = listPrice;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getBalancePay() {
            return balancePay;
        }

        public void setBalancePay(int balancePay) {
            this.balancePay = balancePay;
        }

        public int getDirectPay() {
            return directPay;
        }

        public void setDirectPay(int directPay) {
            this.directPay = directPay;
        }

        public boolean isVirtualGoods() {
            return virtualGoods;
        }

        public void setVirtualGoods(boolean virtualGoods) {
            this.virtualGoods = virtualGoods;
        }

        public boolean isSpecialCounter() {
            return specialCounter;
        }

        public void setSpecialCounter(boolean specialCounter) {
            this.specialCounter = specialCounter;
        }

        public boolean isValidFlag() {
            return validFlag;
        }

        public void setValidFlag(boolean validFlag) {
            this.validFlag = validFlag;
        }
    }
}
