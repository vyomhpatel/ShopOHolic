package b12app.vyom.com.shopoholic.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import b12app.vyom.com.shopoholic.R;

public class AccountItemAdapter extends BaseAdapter {

    Integer[] icons;
    String[] actions;
    LayoutInflater layoutInflater;
    Context context;

    public AccountItemAdapter(Integer[] icons, String[] actions, Context context) {
        this.icons = icons;
        this.actions = actions;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }





    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return icons[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AccountItemHolder accountItemHolder;

        if(convertView == null){
            accountItemHolder = new AccountItemHolder();
            convertView = layoutInflater.inflate(R.layout.account_item_format, parent, false);
            accountItemHolder.icon = convertView.findViewById(R.id.tvIcon);
            accountItemHolder.tvName = convertView.findViewById(R.id.tvName);
            convertView.setTag(accountItemHolder);

            accountItemHolder.icon.setImageResource(icons[position]);
            accountItemHolder.tvName.setText(actions[position]);

        }else{
            accountItemHolder= (AccountItemHolder) convertView.getTag();
        }


        return convertView;
    }

    public class AccountItemHolder{
        ImageView icon;
        TextView  tvName;

    }
}
