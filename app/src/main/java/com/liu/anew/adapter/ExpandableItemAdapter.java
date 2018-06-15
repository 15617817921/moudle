package com.liu.anew.adapter;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.liu.anew.R;
import com.liu.anew.bean.custom.Level0Item;
import com.liu.anew.bean.custom.Level1Item;
import com.liu.anew.bean.custom.Person;
import com.liu.anew.utils.MyLogger;

import java.util.List;

/**
 * Created by luoxw on 2016/8/9.
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
        addItemType(TYPE_PERSON, R.layout.item_expandable_lv2);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                switch (holder.getLayoutPosition() % 3) {
                    case 0:
                        holder.setImageResource(R.id.iv_head, R.mipmap.ic_launcher_round);
                        break;
                    case 1:
                        holder.setImageResource(R.id.iv_head, R.mipmap.ic_launcher);
                        break;
                    case 2:
                        holder.setImageResource(R.id.iv_head, R.mipmap.ic_launcher_round);
                        break;
                }
                final Level0Item lv0 = (Level0Item) item;
                holder.setText(R.id.title, lv0.title)
                        .setText(R.id.sub_title, lv0.subTitle)
                        .setImageResource(R.id.iv, lv0.isExpanded() ? R.mipmap.arrow_down : R.mipmap.arrow_ping);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();

                        MyLogger.kLog().e("Level 0 item pos: " + pos);
                        if (lv0.isExpanded()) {
                            MyLogger.kLog().e("折叠 " + pos);
                            collapse(pos);
                        } else {
// 获取当前父级位置
                            int cp = getParentPosition(item);
// 通过父级位置找到当前list，删除指定下级
                            List<MultiItemEntity> data1 = getData();
//                            List<MultiItemEntity> data = (List<MultiItemEntity>) getData().get(cp);
                            MyLogger.kLog().e("折叠 " + data1.size());
                            // 获取当前父级位置
//                            int cp = getParentPosition(person);
// 通过父级位置找到当前list，删除指定下级
//                            ((Level1Item)getData().get(cp)).removeSubItem(person);

//                            expandAll(pos, false);//展开此条目下所有的二级三级
//                            expandAll();//展开所有条目下所有的二级三级
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                holder.setText(R.id.title, lv1.title)
                        .setText(R.id.sub_title, lv1.subTitle)
                        .setImageResource(R.id.iv, lv1.isExpanded() ? R.mipmap.arrow_down : R.mipmap.arrow_ping);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        MyLogger.kLog().e("Level 1 item pos: " + pos);
                        if (lv1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getAdapterPosition();
                        remove(pos);
                        return true;
                    }
                });
                break;
            case TYPE_PERSON:
                final Person person = (Person) item;
                holder.setText(R.id.tv, person.name + " parent pos: " + getParentPosition(person));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getAdapterPosition();
                        remove(pos);
                    }
                });
                break;
        }
    }
}
