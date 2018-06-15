package com.liu.anew.base;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

public class SmartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private  AdapterView.OnItemClickListener mListener;
    private AdapterView.OnItemLongClickListener mLongListener;
    private int mPosition = -1;

    public SmartViewHolder(View itemView, AdapterView.OnItemClickListener mListener, AdapterView.OnItemLongClickListener mLongListener) {
        super(itemView);
        this.mListener = mListener;
        this.mLongListener = mLongListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        /*
         * 设置水波纹背景
         */
        setBackground(itemView);
    }


    public SmartViewHolder(View itemView, AdapterView.OnItemClickListener mListener) {
        super(itemView);
        this.mListener = mListener;
        itemView.setOnClickListener(this);
        /*
         * 设置水波纹背景
         */
        setBackground(itemView);
    }
    /*
     * 设置水波纹背景
     */
    private void setBackground(View itemView) {
        if (itemView.getBackground() == null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = itemView.getContext().getTheme();
            int top = itemView.getPaddingTop();
            int bottom = itemView.getPaddingBottom();
            int left = itemView.getPaddingLeft();
            int right = itemView.getPaddingRight();
            if (theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
                itemView.setBackgroundResource(typedValue.resourceId);
            }
            itemView.setPadding(left, top, right, bottom);
        }
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            int position = getAdapterPosition();
            if (position >= 0) {
                mListener.onItemClick(null, v, position, getItemId());
            } else if (mPosition > -1) {
                mListener.onItemClick(null, v, mPosition, getItemId());
            }
        }

    }
    @Override
    public boolean onLongClick(View v) {
        if (mLongListener != null) {
            int position = getAdapterPosition();
            if (position >= 0) {
                mLongListener.onItemLongClick(null, v, position, getItemId());
            } else if (mPosition > -1) {
                mLongListener.onItemLongClick(null, v, mPosition, getItemId());
            }
        }
        //返回true 表示消耗了事件 事件不会继续传递
        return true;
    }
    public View findViewById(int id) {
        return id == 0 ? itemView : itemView.findViewById(id);
    }

    public SmartViewHolder text(int id, CharSequence sequence) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(sequence);
        }
        return this;
    }

    public SmartViewHolder text(int id, @StringRes int stringRes) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(stringRes);
        }
        return this;
    }

    /**
     *
     * @param id
     * @param colorId
     * @return
     */

    public SmartViewHolder textColorId(int id, int colorId) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(), colorId));
        }
        return this;
    }

    public SmartViewHolder image(int id, int imageId) {
        View view = findViewById(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(imageId);
        }
        return this;
    }
    /**
     * 关于内部view事件的点击
     */
    public SmartViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener)
    {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public SmartViewHolder setOnTouchListener(int viewId,
                                         View.OnTouchListener listener)
    {
        View view = findViewById(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public SmartViewHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener)
    {
        View view = findViewById(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

}