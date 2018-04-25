package b12app.vyom.com.shopoholic.model;

import java.io.Serializable;
import java.util.List;

public class Product {


    private List<ProductsBean> products;

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean implements Serializable {
        /**
         * id : 308
         * pname : i5-Laptop
         * quantity : 1
         * prize : 60000
         * discription : Online directory of electrical goods manufacturers, electronic goods suppliers and electronic product manufacturers. Get details of electronic products
         * image : https://rjtmobile.com/ansari/shopingcart/admin/uploads/product_images/images.jpg
         */

        private String id;
        private String pname;
        private String quantity;
        private String prize;
        private String discription;
        private String image;

        public ProductsBean(String id, String pname, String quantity, String prize, String discription, String image) {
            this.id = id;
            this.pname = pname;
            this.quantity = quantity;
            this.prize = prize;
            this.discription = discription;
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPrize() {
            return prize;
        }

        public void setPrize(String prize) {
            this.prize = prize;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
