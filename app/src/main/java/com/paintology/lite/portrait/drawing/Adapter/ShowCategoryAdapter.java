package com.paintology.lite.portrait.drawing.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.paintology.lite.portrait.drawing.Model.CategoryModel;
import com.paintology.lite.portrait.drawing.R;
import com.paintology.lite.portrait.drawing.gallery.Interface_select_item;

import java.util.ArrayList;

public class ShowCategoryAdapter extends RecyclerView.Adapter<ShowCategoryAdapter.MyViewHolder> {

    ArrayList<CategoryModel.categoryData> _list;
    Context context;
    Interface_select_item _obj_interface;

    DisplayImageOptions mDisplayImageOptions;
    ImageLoaderConfiguration conf;

    ImageLoader mImageLoader;

    public ShowCategoryAdapter(ArrayList<CategoryModel.categoryData> _list, Context context, Interface_select_item _obj_interface) {
        this._list = _list;
        this.context = context;
        this._obj_interface = _obj_interface;

        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.abc_ab_share_pack_mtrl_alpha)
                /*.showImageOnLoading(R.drawable.loading_bg)
                .showImageOnLoading(R.drawable.loading_bg)*/
                .cacheInMemory(false)
                .cacheOnDisc(false)
                .build();

        conf = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(1)
                .defaultDisplayImageOptions(mDisplayImageOptions)
                .writeDebugLogs()
                .build();

        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(conf);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_tutorial_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        CategoryModel.categoryData _obj =  _list.get(i);
        if (_obj != null) {

//            myViewHolder.tv_tutorial_name(_obj.getObj_data() != null ? Html.fromHtml(_obj.getObj_data().getCategoryName()) : "");

            String title = _obj.getObj_data() != null ? String.valueOf(Html.fromHtml(_obj.getObj_data().getCategoryName())) : "";
            myViewHolder.tv_tutorial_name.setText(context.getResources().getString(R.string.category_name_count, title, _obj.getObj_data().getTotalTutorials()));
            myViewHolder.tv_id.setText(_obj.getObj_data().cate_id);
//            myViewHolder.title.setText(title + ">");

//            displayTutorialCount(myViewHolder.tutorial_count, _obj.getObj_data().getTotalTutorials());

            try {
                if (_obj.getResize() != null && !_obj.getResize().equalsIgnoreCase("false")) {
//                    mImageLoader.displayImage(_obj.getResize(), myViewHolder.thumbnail);
                    Glide.with(context)
                            .load(_obj.getResize())
                            .apply(new RequestOptions().placeholder(R.drawable.feed_thumb_default).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .into(myViewHolder.thumbnail);
                }
            } catch (Exception e) {

            }
//            if (_obj.isTypeCategory()) {
//                myViewHolder.ll_back.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
//            } else {
//                myViewHolder.ll_back.setBackgroundColor(context.getResources().getColor(R.color.cardview_dark_background));
//            }
        }
    }

    private void displayTutorialCount(TextView view, int count) {
//        int count = 0;
//        for (CategoryModel.categoryData child :
//                childs) {
//            int size = child.getChilds().size();
//
//            count += size;
//        }

        view.setText(String.valueOf(count));
    }

    @Override
    public int getItemCount() {
        return _list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnail;
        public TextView tv_tutorial_name, tutorial_count;
        public FrameLayout frm_default;
        //        public LinearLayout ll_back;
        public TextView tv_id;


        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.iv_tutorial_category);
            tv_tutorial_name = (TextView) view.findViewById(R.id.tv_category_name);
//            tutorial_count = (TextView) view.findViewById(R.id.tutorial_count);
            tv_id = (TextView) view.findViewById(R.id.tv_id);
            frm_default = (FrameLayout) view.findViewById(R.id.frm_default);
//            ll_back = (LinearLayout) view.findViewById(R.id.ll_back);

            frm_default.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _obj_interface.selectItem(getAdapterPosition(), false);
                }
            });
        }
    }
}
