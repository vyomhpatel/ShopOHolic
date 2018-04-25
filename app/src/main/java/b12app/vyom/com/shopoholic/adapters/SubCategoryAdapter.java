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
import b12app.vyom.com.shopoholic.model.SubCategory;

public class SubCategoryAdapter extends BaseAdapter {

    List<SubCategory.SubcategoryBean> subcategoryBeanList;
    Context context;
    LayoutInflater layoutInflater;

    public SubCategoryAdapter(List<SubCategory.SubcategoryBean> subcategoryBeanList, Context context) {
        this.subcategoryBeanList = subcategoryBeanList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return subcategoryBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return subcategoryBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SubCategoryHolder subCategoryHolder;

        if(convertView == null){
            subCategoryHolder = new SubCategoryHolder();
            convertView = layoutInflater.inflate(R.layout.content_item_format, parent, false);
            subCategoryHolder.sub_category_image = convertView.findViewById(R.id.item_content_image);
            subCategoryHolder.sub_category_title = convertView.findViewById(R.id.item_content_title);
            convertView.setTag(subCategoryHolder);

            Picasso.with(context).load(subcategoryBeanList.get(position).getScimageurl()).into(subCategoryHolder.sub_category_image);
            subCategoryHolder.sub_category_title.setText(subcategoryBeanList.get(position).getScname());

        }else{
            subCategoryHolder= (SubCategoryHolder)convertView.getTag();
        }


        return convertView;
    }

    public class SubCategoryHolder{

        ImageView sub_category_image;
        TextView sub_category_title;

    }
}
