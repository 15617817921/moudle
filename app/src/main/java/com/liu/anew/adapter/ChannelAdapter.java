package com.liu.anew.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liu.anew.R;
import com.liu.anew.bean.data.ResultBean;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/28.
 */
public class ChannelAdapter extends BaseAdapter {
    private Context mContext;
    private List<ResultBean.ChannelInfoBean> channel_info;

    public ChannelAdapter(Context mContext, List<ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = mContext;
        this.channel_info = channel_info;
    }


    @Override
    public int getCount() {
        return channel_info == null ? 0 : channel_info.size();
    }

    @Override
    public Object getItem(int position) {
        return channel_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holer;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_channel, null);
            holer = new ViewHolder(convertView);
            convertView.setTag(holer);
        } else {
            holer = (ViewHolder) convertView.getTag();
        }

        ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
        holer.tvChannel.setText(channelInfoBean.getChannel_name());
        Glide.with(mContext)
                .load(channelInfoBean.getImage())
                .into(holer.ivChannel);
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.iv_channel)
        ImageView ivChannel;
        @BindView(R.id.tv_channel)
        TextView tvChannel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
