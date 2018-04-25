package b12app.vyom.com.shopoholic.model;

import java.io.Serializable;

// FIXME generate failure  field _$OrderConfirmed136
public class OrderConfirm implements Serializable {

    public static class OrderconfirmedBean {
        public OrderconfirmedBean(String orderid, String orderstatus, String name, String deliveryadd, String mobile, String itemname, String itemquantity, String totalprice, String placedon) {
            this.orderid = orderid;
            this.orderstatus = orderstatus;
            this.name = name;
            this.deliveryadd = deliveryadd;
            this.mobile = mobile;
            this.itemname = itemname;
            this.itemquantity = itemquantity;
            this.totalprice = totalprice;
            this.placedon = placedon;
        }

        /**
         * orderid : 2147483795
         * orderstatus : 1
         * name : patel
         * billingadd : Noida
         * deliveryadd : Noida
         * mobile : 7405078484
         * email : vansh3vns@gmail.com
         * itemid : 310
         * itemname : Pant
         * itemquantity : 1
         * totalprice : 2530
         * paidprice : 2530
         * placedon : 2018-04-17 13:19:33
         */


        private String orderid;
        private String orderstatus;
        private String name;
        private String billingadd;
        private String deliveryadd;
        private String mobile;
        private String email;
        private String itemid;
        private String itemname;
        private String itemquantity;
        private String totalprice;
        private String paidprice;
        private String placedon;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(String orderstatus) {
            this.orderstatus = orderstatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBillingadd() {
            return billingadd;
        }

        public void setBillingadd(String billingadd) {
            this.billingadd = billingadd;
        }

        public String getDeliveryadd() {
            return deliveryadd;
        }

        public void setDeliveryadd(String deliveryadd) {
            this.deliveryadd = deliveryadd;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getItemid() {
            return itemid;
        }

        public void setItemid(String itemid) {
            this.itemid = itemid;
        }

        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemname) {
            this.itemname = itemname;
        }

        public String getItemquantity() {
            return itemquantity;
        }

        public void setItemquantity(String itemquantity) {
            this.itemquantity = itemquantity;
        }

        public String getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(String totalprice) {
            this.totalprice = totalprice;
        }

        public String getPaidprice() {
            return paidprice;
        }

        public void setPaidprice(String paidprice) {
            this.paidprice = paidprice;
        }

        public String getPlacedon() {
            return placedon;
        }

        public void setPlacedon(String placedon) {
            this.placedon = placedon;
        }
    }
}
