package b12app.vyom.com.shopoholic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import b12app.vyom.com.shopoholic.model.OrderHistory;
import b12app.vyom.com.shopoholic.R;

public class OrderHistoryAdapter extends BaseAdapter {

    List<OrderHistory.OrderhistoryBean> orderhistoryBeans;

    Context context;
    LayoutInflater layoutInflater;

    public OrderHistoryAdapter(List<OrderHistory.OrderhistoryBean> orderhistoryBeans , Context context) {
        this.orderhistoryBeans = orderhistoryBeans ;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return orderhistoryBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return orderhistoryBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final OrderHistoryViewHolder orderHistoryViewHolder;

        if(convertView == null){
            orderHistoryViewHolder = new OrderHistoryViewHolder();
            convertView = layoutInflater.inflate(R.layout.order_item_format, parent, false);
            orderHistoryViewHolder.tvOrderNo = convertView.findViewById(R.id.order_no);
            orderHistoryViewHolder.tvItems = convertView.findViewById(R.id.tvOrderItems);
            orderHistoryViewHolder.tvTimePlaced = convertView.findViewById(R.id.time_placed);
            orderHistoryViewHolder.tvDeliveryAddress = convertView.findViewById(R.id.tvDeliveryAddress);
            orderHistoryViewHolder.tvOrderTotal = convertView.findViewById(R.id.order_total);
            convertView.setTag(orderHistoryViewHolder);


            orderHistoryViewHolder.tvOrderNo.setText(orderhistoryBeans.get(position).getOrderid());
            orderHistoryViewHolder.tvOrderTotal.setText("₹ "+orderhistoryBeans.get(position).getTotalprice());
            orderHistoryViewHolder.tvTimePlaced.setText(orderhistoryBeans.get(position).getPlacedon());
            orderHistoryViewHolder.tvDeliveryAddress.setText(orderhistoryBeans.get(position).getDeliveryadd());
            orderHistoryViewHolder.tvItems.append(orderhistoryBeans.get(position).getItemname()+" ");






        }else{
            orderHistoryViewHolder= (OrderHistoryViewHolder) convertView.getTag();
        }


        return convertView;
    }

    public class OrderHistoryViewHolder{


        TextView tvOrderNo, tvItems, tvOrderTotal, tvTimePlaced, tvDeliveryAddress;
    }
}
