package b12app.vyom.com.shopoholic.model;

import java.util.List;

public class Category {


    private List<CategoryBean> category;

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public static class CategoryBean {
        public CategoryBean(String cid, String cname, String cdiscription, String cimagerl) {
            this.cid = cid;
            this.cname = cname;
            this.cdiscription = cdiscription;
            this.cimagerl = cimagerl;
        }

        /**
         * cid : 107
         * cname : Electronics
         * cdiscription : Online directory of electrical goods manufacturers, electronic goods suppliers and electronic product manufacturers. Get details of electronic products.
         * cimagerl : https://rjtmobile.com/ansari/shopingcart/admin/uploads/category_images/images.jpg
         */

        private String cid;
        private String cname;
        private String cdiscription;
        private String cimagerl;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCdiscription() {
            return cdiscription;
        }

        public void setCdiscription(String cdiscription) {
            this.cdiscription = cdiscription;
        }

        public String getCimagerl() {
            return cimagerl;
        }

        public void setCimagerl(String cimagerl) {
            this.cimagerl = cimagerl;
        }
    }
}
