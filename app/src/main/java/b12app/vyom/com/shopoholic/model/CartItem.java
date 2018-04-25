package b12app.vyom.com.shopoholic.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CartItem {


    private List<ProductsBean> products;

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean implements Parcelable{
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
        private String image;

        public ProductsBean(String id, String pname, String quantity, String prize, String image) {
            this.id = id;
            this.pname = pname;
            this.quantity = quantity;
            this.prize = prize;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeString(id);
            dest.writeString(pname);
            dest.writeString(quantity);
            dest.writeString(prize);
            dest.writeString(image);
        }

        public ProductsBean(Parcel in) {
            id = in.readString();
            pname = in.readString();
            quantity = in.readString();
            prize = in.readString();
            image = in.readString();
        }
        public static final Parcelable.Creator<ProductsBean> CREATOR = new Parcelable.Creator<ProductsBean>()
        {
            public ProductsBean createFromParcel(Parcel in)
            {
                return new ProductsBean(in);
            }
            public ProductsBean[] newArray(int size)
            {
                return new ProductsBean[size];
            }
        };
    }
}
