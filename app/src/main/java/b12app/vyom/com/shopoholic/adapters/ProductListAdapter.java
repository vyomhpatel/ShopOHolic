package b12app.vyom.com.shopoholic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import b12app.vyom.com.shopoholic.R;
import b12app.vyom.com.shopoholic.model.Product;

public class ProductListAdapter extends BaseAdapter {

    List<Product.ProductsBean> productsBeanList;
    Context context;
    LayoutInflater layoutInflater;

    public ProductListAdapter(List<Product.ProductsBean> productsBeanList , Context context) {
        this.productsBeanList = productsBeanList ;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return productsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProductListHolder productListHolder;

        if(convertView == null){
            productListHolder = new ProductListHolder();
            convertView = layoutInflater.inflate(R.layout.product_list_item_format, parent, false);
            productListHolder.product_image = convertView.findViewById(R.id.product_image);
            productListHolder.product_title = convertView.findViewById(R.id.product_title);
            productListHolder.product_price = convertView.findViewById(R.id.product_price);
            convertView.setTag(productListHolder);

            Picasso.with(context).load(productsBeanList.get(position).getImage()).into(productListHolder.product_image);
            productListHolder.product_title.setText(productsBeanList.get(position).getPname());
            productListHolder.product_price.setText("₹ "+productsBeanList.get(position).getPrize());

        }else{
            productListHolder= (ProductListHolder) convertView.getTag();
        }


        return convertView;
    }

    public class ProductListHolder{

        ImageView product_image;
        TextView product_title, product_price;

    }
}
