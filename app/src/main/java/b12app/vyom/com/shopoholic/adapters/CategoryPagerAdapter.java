package b12app.vyom.com.shopoholic.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import b12app.vyom.com.shopoholic.model.Category;
import b12app.vyom.com.shopoholic.R;

public class CategoryPagerAdapter extends PagerAdapter {

    Context context;
    private List<Category.CategoryBean> mcategoryBeanList;
    private LayoutInflater inflater;
    private MyViewHolder myViewHolder;

    public CategoryPagerAdapter(Context context, List<Category.CategoryBean> mcategoryBeanList) {

        this.context = context;
        this.mcategoryBeanList = mcategoryBeanList;
    }

    @Override
    public int getCount() {
        return this.mcategoryBeanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        myViewHolder = new MyViewHolder();

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.slider_item, container,
                false);

        myViewHolder.imgDisplay = (ImageView) viewLayout.findViewById(R.id.imageViewSlider);

        Picasso.with(context).load(mcategoryBeanList.get(position).getCimagerl()).into(myViewHolder.imgDisplay);
        (container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((CardView) object);

    }

    public class MyViewHolder {
        ImageView imgDisplay;

    }

}
