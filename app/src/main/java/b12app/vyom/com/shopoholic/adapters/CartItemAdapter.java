package b12app.vyom.com.shopoholic.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import b12app.vyom.com.shopoholic.utility.CartDbHelper;
import b12app.vyom.com.shopoholic.model.CartItem;
import b12app.vyom.com.shopoholic.R;
import b12app.vyom.com.shopoholic.utility.EventTotal;
import b12app.vyom.com.shopoholic.utility.Events;
import b12app.vyom.com.shopoholic.utility.GlobalBus;

public class CartItemAdapter extends BaseAdapter {

    List<CartItem.ProductsBean> productsBeanList;
    Context context;
    LayoutInflater layoutInflater;
    int product_price;
    public String TAG ="cart item adapter";
    private String user_id;

    SQLiteDatabase sqLiteDb;
    static CartDbHelper cartDbHelper;

    public CartItemAdapter(List<CartItem.ProductsBean> productsBeanList , Context context) {
        this.productsBeanList = productsBeanList ;

        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cartDbHelper = new CartDbHelper(context);
        sqLiteDb = cartDbHelper.getWritableDatabase();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_details",Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id","");
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final CartItemHolder cartItemHolder;

        if(convertView == null){
            cartItemHolder = new CartItemHolder();
            convertView = layoutInflater.inflate(R.layout.cart_item_format, parent, false);
            cartItemHolder.item_image = convertView.findViewById(R.id.cart_item_image);
            cartItemHolder.item_title = convertView.findViewById(R.id.cart_item_name);
            cartItemHolder.item_price = convertView.findViewById(R.id.cart_item_price);
            cartItemHolder.spnItemCount = convertView.findViewById(R.id.spnQuantity);
            cartItemHolder.btnRemove = convertView.findViewById(R.id.btnRemove);
            convertView.setTag(cartItemHolder);



        }else{
            cartItemHolder= (CartItemHolder) convertView.getTag();
        }

        Picasso.with(context).load(productsBeanList.get(position).getImage()).into(cartItemHolder.item_image);
        cartItemHolder.item_title.setText(productsBeanList.get(position).getPname());
        cartItemHolder.item_price.setText("₹ "+productsBeanList.get(position).getPrize());
        cartItemHolder.spnItemCount.setSelection(Integer.parseInt(productsBeanList.get(position).getQuantity()));
        cartItemHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String delete = "DELETE FROM "+CartDbHelper.TABLE_NAME+" WHERE "+CartDbHelper.USER_ID+" = "+user_id+" AND "+CartDbHelper.PRODUCT_ID+" = "+productsBeanList.get(position).getId();
                productsBeanList.remove(position);
                sqLiteDb.execSQL(delete);
                notifyDataSetChanged();
            }
        });

        cartItemHolder.spnItemCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position_spn, long id) {

                String spinner_item = cartItemHolder.spnItemCount.getSelectedItem().toString();
                //update datalist
                productsBeanList.get(position).setQuantity(spinner_item);

                //notify list refresh
                GlobalBus.getBus().post(new EventTotal(productsBeanList.get(position).getId(), spinner_item));
                if(Integer.valueOf(spinner_item)==0){
                    String delete = "DELETE FROM "+CartDbHelper.TABLE_NAME+" WHERE "+CartDbHelper.USER_ID+" = "+user_id+" AND "+CartDbHelper.PRODUCT_ID+" = "+productsBeanList.get(position).getId();
                    sqLiteDb.execSQL(delete);
                    productsBeanList.remove(position);



                }
                notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Events.AdapterToCart adapterToCart =  new Events.AdapterToCart(product_price);


        return convertView;
    }

    public class CartItemHolder{

        ImageView item_image;
        TextView item_title, item_price;
        Spinner spnItemCount;
        Button btnRemove;
    }
}
