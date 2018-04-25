package b12app.vyom.com.shopoholic.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import b12app.vyom.com.shopoholic.utility.CartDbHelper;
import b12app.vyom.com.shopoholic.model.Product;
import b12app.vyom.com.shopoholic.R;

public class ProductDetailsFragment extends Fragment {

    private static final String TAG = "product details tag";
    private TextView tvProductName, tvProductPrice, tvProductDescription, tvShare;
    private ImageView imageProduct;
    private Button btnAddtoCart;
    static CartDbHelper cartDbHelper;
    SQLiteDatabase sqLiteDb;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_details_format,container,false);
        tvProductName = view.findViewById(R.id.tvProductName);
        tvProductPrice = view.findViewById(R.id.tvproductPrice);
        tvProductDescription = view.findViewById(R.id.product_description);
        tvShare = view.findViewById(R.id.tvShare);
        imageProduct = view.findViewById(R.id.product_image);
        btnAddtoCart = view.findViewById(R.id.btnAddtoCart);
        Bundle receiveProduct = getArguments();


        SharedPreferences getuser = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        final String user_idDb  = getuser.getString("user_id","");

        cartDbHelper = new CartDbHelper(getActivity());
        sqLiteDb = cartDbHelper.getWritableDatabase();

        final Product.ProductsBean productsBean = (Product.ProductsBean) receiveProduct.getSerializable("product");


        Log.i(TAG, "Product Details"+productsBean.getPname());

        Picasso.with(getActivity()).load(productsBean.getImage()).into(imageProduct);
        tvProductName.setText(productsBean.getPname());
        tvProductDescription.setText(productsBean.getDiscription());
        tvProductPrice.setText("Rs "+productsBean.getPrize());
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, productsBean.getPname());
                i.putExtra(Intent.EXTRA_TEXT   , "Check "+productsBean.getPname() +" out at Shop-O-Holic at really low cost of Rs "+ productsBean.getPrize());
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundleToCart = new Bundle();
//                bundleToCart.putSerializable("cartItem",productsBean);

                Toast.makeText(getActivity(),"Product"+ productsBean.getPname(), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "product: "+productsBean.getPname());


               String query = "SELECT * FROM "+CartDbHelper.TABLE_NAME+" WHERE "+CartDbHelper.USER_ID+" = "+user_idDb+" AND "+CartDbHelper.PRODUCT_ID+" = "+productsBean.getId();

               Cursor c = sqLiteDb.rawQuery(query,null);
                c.moveToFirst();
                if(c.getCount()>0) {
                    String cursor_product_quantity = c.getString(c.getColumnIndex(CartDbHelper.PRODUCT_QUANTITY));

                    int increased_quantity = Integer.valueOf(cursor_product_quantity)+1;

                    Log.i(TAG, "cursor_product_quantity: " + increased_quantity);
                    ContentValues quantityupdateValues = new ContentValues();
                    quantityupdateValues.put(CartDbHelper.PRODUCT_QUANTITY,String.valueOf(increased_quantity));
                    sqLiteDb.update(CartDbHelper.TABLE_NAME,quantityupdateValues,CartDbHelper.USER_ID+"=?",new String[]{user_idDb});
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put(CartDbHelper.USER_ID, user_idDb);
                    values.put(CartDbHelper.PRODUCT_ID, productsBean.getId());
                    values.put(CartDbHelper.PRODUCT_NAME, productsBean.getPname());
                    values.put(CartDbHelper.PRODUCT_IMAGE, productsBean.getImage());
                    values.put(CartDbHelper.PRODUCT_PRICE, productsBean.getPrize());
                    values.put(CartDbHelper.PRODUCT_QUANTITY, productsBean.getQuantity());

                    sqLiteDb.insert(CartDbHelper.TABLE_NAME, null, values);
                }

                ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
              //  shoppingCartFragment.setArguments(bundleToCart);
                getFragmentManager().beginTransaction().replace(R.id.frame_home,shoppingCartFragment).commit();

//                Log.i(TAG, "Product Detail parcel to cart"+bundleToCart);



            }
        });

        return view;
    }
}
