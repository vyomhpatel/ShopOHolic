package b12app.vyom.com.shopoholic.model;

import java.util.List;

public class SubCategory {
    private List<SubcategoryBean> subcategory;

    public List<SubcategoryBean> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(List<SubcategoryBean> subcategory) {
        this.subcategory = subcategory;
    }

    public static class SubcategoryBean {
        /**
         * scid : 205
         * scname : Laptops
         * scdiscription : Laptop Prices in India - Buy Laptops online at best prices on ecom.com. Choose wide range of Branded Laptops .Free Shipping, Cash on delivery at India's .
         * scimageurl : https://rjtmobile.com/ansari/shopingcart/admin/uploads/sub_category_images/download.jpg
         */

        private String scid;
        private String scname;
        private String scdiscription;
        private String scimageurl;


        public SubcategoryBean(String scid, String scname, String scdiscription, String scimageurl) {
            this.scid = scid;
            this.scname = scname;
            this.scdiscription = scdiscription;
            this.scimageurl = scimageurl;
        }

        public String getScid() {
            return scid;
        }

        public void setScid(String scid) {
            this.scid = scid;
        }

        public String getScname() {
            return scname;
        }

        public void setScname(String scname) {
            this.scname = scname;
        }

        public String getScdiscription() {
            return scdiscription;
        }

        public void setScdiscription(String scdiscription) {
            this.scdiscription = scdiscription;
        }

        public String getScimageurl() {
            return scimageurl;
        }

        public void setScimageurl(String scimageurl) {
            this.scimageurl = scimageurl;
        }
    }
}
