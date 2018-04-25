package b12app.vyom.com.shopoholic.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import b12app.vyom.com.shopoholic.R;

public class OrderSummeryFragment extends Fragment {

    private TextView confirmOrderNo,tvFinalPayment,tvPersonName,tvFinalAddress,tvMobileFinal,tvProductName,tvProductQty,tvTerms;
    private Button btnHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_confirm,container,false);

        confirmOrderNo = view.findViewById(R.id.confirmOrderNo);
        tvFinalAddress = view.findViewById(R.id.tvFinalDelivery);
        tvFinalPayment = view.findViewById(R.id.tvFinalPayment);
        tvMobileFinal = view.findViewById(R.id.tvMobileFinal);
        tvProductName = view.findViewById(R.id.tvProductName);
        tvProductQty = view.findViewById(R.id.tvProductQty);
        tvPersonName = view.findViewById(R.id.tvPersonName);
        tvTerms = view.findViewById(R.id.tvTerms);
        btnHistory = view.findViewById(R.id.btnHistory);

       Bundle bundle = getArguments();

       String orderid=  bundle.getString("orderid");
       String  orderstatus=  bundle.getString("orderstatus");
       String   name =  bundle.getString("name");
       String  deliveryadd =    bundle.getString("deliveryadd");
       String    mobile =    bundle.getString("mobile");
       String  itemname =    bundle.getString("itemname");
       String   itemquantity =   bundle.getString("itemquantity");
       String   totalprice =   bundle.getString("totalprice");

        confirmOrderNo.setText(orderid);
        tvFinalAddress.setText(deliveryadd);
        tvFinalPayment.setText("Rs "+totalprice);
        tvMobileFinal.setText(mobile);
        tvProductName.setText(itemname);
        tvProductQty.setText(itemquantity);
        tvPersonName.setText(name);

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOrdersFragment myOrdersFragment = new MyOrdersFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame_home,myOrdersFragment).addToBackStack(null).commit();
            }
        });

        tvTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame_home,aboutUsFragment).commit();
            }
        });

        return view;
    }
}
