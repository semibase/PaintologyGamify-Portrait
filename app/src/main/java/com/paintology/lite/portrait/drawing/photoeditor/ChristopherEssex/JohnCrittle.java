package com.paintology.lite.portrait.drawing.photoeditor.ChristopherEssex;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.paintology.lite.portrait.drawing.R;
import com.paintology.lite.portrait.drawing.photoeditor.ImageFilterUtils;

import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class JohnCrittle extends BaseAdapter {

    List<LizDavenport> filterUris;
    Context mContext;
    private Bitmap background;

    private int selectFilter = 0;

    public void setSelectFilter(int selectFilter) {
        this.selectFilter = selectFilter;
    }

    public int getSelectFilter() {
        return selectFilter;
    }

    public JohnCrittle(Context context, List<LizDavenport> effects, Bitmap backgroud) {
        filterUris = effects;
        mContext = context;
        this.background = backgroud;
    }

    @Override
    public int getCount() {
        return filterUris.size();
    }

    @Override
    public Object getItem(int position) {
        return filterUris.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EffectHolder holder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.xpro_bottom_filter, null);
            holder = new EffectHolder();
            holder.filteredImg = (GPUImageView) convertView.findViewById(R.id.small_filter);
            convertView.setTag(holder);
        } else {
            holder = (EffectHolder) convertView.getTag();
        }

        final LizDavenport effect = (LizDavenport) getItem(position);

        holder.filteredImg.setImage(background);
        GPUImageFilter filter = ImageFilterUtils.createFilterForType(mContext, effect.getType());
        holder.filteredImg.setFilter(filter);

        return convertView;
    }

    class EffectHolder {
        GPUImageView filteredImg;
    }

}
