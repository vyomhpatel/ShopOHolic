package b12app.vyom.com.shopoholic;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import b12app.vyom.com.shopoholic.adapters.CategoryPagerAdapter;
import b12app.vyom.com.shopoholic.model.Category;
import b12app.vyom.com.shopoholic.utility.Events;
import b12app.vyom.com.shopoholic.utility.GlobalBus;

public class HomeRecycleViewAdapter extends RecyclerView.Adapter {

    Context context;
    List<Category.CategoryBean> categories_list;
    int FLAG_HEADER = 0;
    int FLAG_NORMAL = 1;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    public HomeRecycleViewAdapter(Context context, List<Category.CategoryBean> categories_list) {
        this.context = context;
        this.categories_list = categories_list;
    }


    @Override
    public int getItemViewType(int position) {

        if(position==0) {


            return FLAG_HEADER;
        }
         return  FLAG_NORMAL;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==FLAG_HEADER){

            View view = LayoutInflater.from(context).inflate(R.layout.fragment_home,parent,false);
            return new ViewHolderBanner(view);

        }

        if(viewType==FLAG_NORMAL){
            View view = LayoutInflater.from(context).inflate(R.layout.content_item_format,parent,false);
            return new ViewHolderNormal(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {



        if(holder instanceof ViewHolderBanner){

            CategoryPagerAdapter categoryPagerAdapter = new CategoryPagerAdapter(context,categories_list);

            ((ViewHolderBanner) holder).viewPagerBanner.setAdapter(categoryPagerAdapter);

            Log.i("test", categories_list.get(position).getCimagerl());
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == categories_list.size()-1) {
                        currentPage = 0;
                    }
                    ((ViewHolderBanner) holder).viewPagerBanner.setCurrentItem(currentPage++, true);
                }
            };

            timer = new Timer(); // This will create a new Thread
            timer .schedule(new TimerTask() { // task to be scheduled

                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);



        }else if (holder instanceof ViewHolderNormal){



            ((ViewHolderNormal) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String c_id = categories_list.get(position).getCid();

                    Events.AdapterToHomeFragmentMessage message = new Events.AdapterToHomeFragmentMessage(c_id);
                    GlobalBus.getBus().post(message);

                }
            });
            ((ViewHolderNormal) holder).category_title_home.setText(categories_list.get(position).getCname());
            Picasso.with(context).load(categories_list.get(position).getCimagerl()).into(((ViewHolderNormal) holder).category_image);
        }
    }



    @Override
    public int getItemCount() {
        return categories_list.size();
    }


    public class ViewHolderBanner extends RecyclerView.ViewHolder{

        ViewPager viewPagerBanner;


        public ViewHolderBanner(View itemView) {
            super(itemView);

            viewPagerBanner = itemView.findViewById(R.id.pager);
        }
    }

    public class ViewHolderNormal extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        ImageView category_image;
        TextView category_title_home;



        public ViewHolderNormal(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            category_image = itemView.findViewById(R.id.item_content_image);
            category_title_home = itemView.findViewById(R.id.item_content_title);


        }


        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();

        if((layoutParams != null) && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
            StaggeredGridLayoutManager.LayoutParams staggeredLayoutParams = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            staggeredLayoutParams.setFullSpan(holder.getLayoutPosition()==0|| holder.getLayoutPosition()%3==1);
        }
    }




}
