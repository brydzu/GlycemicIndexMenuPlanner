package com.naelteam.glycemicindexmenuplanner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.business.GlycemicIndexValueRule;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndexGroup;
import com.naelteam.glycemicindexmenuplanner.model.IGlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.view.GlycemicIndexValueView;

import java.util.ArrayList;
import java.util.List;


public class ListGIRecyclerAdapter extends RecyclerView.Adapter<ListGIRecyclerAdapter.MainViewHolder> {

    public final static int GROUP_VIEW_TYPE= 0;
    public final static int ITEM_VIEW_TYPE= 1;

    private final String  TAG = ListGIRecyclerAdapter.class.getSimpleName();

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<IGlycemicIndex> mDatas=new ArrayList<IGlycemicIndex>();

    private int mChevronColor;

    public ListGIRecyclerAdapter(Context context){
        mContext = context;
    }

    public ListGIRecyclerAdapter(Context context, List<IGlycemicIndex> glycemicIndexes, OnItemClickListener clickListener){
        mContext = context;
        mOnItemClickListener = clickListener;
        mDatas.addAll(glycemicIndexes);

        mChevronColor = context.getResources().getColor(android.R.color.holo_blue_light);
    }

    public void add(IGlycemicIndex glycemicIndex){
        mDatas.add(glycemicIndex);
        notifyItemInserted(mDatas.size());
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == GROUP_VIEW_TYPE){
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_gi_group, viewGroup, false);
            return new GroupViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_gi_item, viewGroup, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(MainViewHolder viewHolder, int position) {

        if (viewHolder instanceof GroupViewHolder) {
            final GlycemicIndexGroup glycemicIndexGroup = (GlycemicIndexGroup) mDatas.get(position);
            final GroupViewHolder groupViewHolder = (GroupViewHolder) viewHolder;
            groupViewHolder.mTextView.setText(glycemicIndexGroup.getTitle());

            if (glycemicIndexGroup.isExpanded()){
                groupViewHolder.mImageView.setImageResource(R.drawable.ic_expand_more_black_36dp);
            }else {
                groupViewHolder.mImageView.setImageResource(R.drawable.ic_chevron_right_black_36dp);
            }
            groupViewHolder.mImageView.setColorFilter(mChevronColor);
        }else {
            final GlycemicIndex glycemicIndex = (GlycemicIndex) mDatas.get(position);
            final ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            itemViewHolder.mTextView.setText(glycemicIndex.getTitle());
            if (glycemicIndex.isSelected()){
                itemViewHolder.mItemLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue_light));
            }else {
                itemViewHolder.mItemLayout.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
            }
            itemViewHolder.mGlycemicIndexValueView.setValue(glycemicIndex.getValue());
            GlycemicIndexValueRule.setGlycemicIndexValue(mContext, itemViewHolder.mGlycemicIndexValueView, glycemicIndex.getValue());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position) instanceof GlycemicIndexGroup){
            return GROUP_VIEW_TYPE;
        }else {
            return ITEM_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addAll(List<IGlycemicIndex> glycemicIndexes, int position, int nbItems) {
        Log.d(TAG, "addAll - position = " + position + ", nbItems = " + nbItems);
        if (nbItems > 0) {
            int i = position;
            for (IGlycemicIndex glycemicIndex : glycemicIndexes) {
                mDatas.add(i, glycemicIndex);
                i++;
            }
            notifyItemRangeInserted(position, nbItems);
        }
    }

    public void removeAll(int position, int nbItems) {
        Log.d(TAG, "removeAll - position = " + position + ", nbItems = " + nbItems);
        if (nbItems > 0) {
            for (int i = position + (nbItems-1); i >= position; i--) {
                mDatas.remove(i);
            }
            notifyItemRangeRemoved(position, (nbItems-1));
            notifyDataSetChanged();
        }
    }

    public void expandGlycemicGroup(GlycemicIndexGroup glycemicIndexGroup, List<IGlycemicIndex> glycemicIndexes, int position){
        Log.d(TAG, "expandGlycemicGroup - position = " + position);

        glycemicIndexGroup.setExpanded(true);
        glycemicIndexGroup.setNbItems(glycemicIndexes.size());

        addAll(glycemicIndexes, position + 1, glycemicIndexGroup.getNbItems());
    }

    public void collapseGlycemicGroup(GlycemicIndexGroup glycemicIndexGroup, int position){
        removeAll(position + 1, glycemicIndexGroup.getNbItems());
        glycemicIndexGroup.setExpanded(false);
        glycemicIndexGroup.setNbItems(0);
    }

    /**
     * Select glycemic indexes in the list
     *
     * @return int first position of selected item
     */
    public int selectItems(List<IGlycemicIndex> glycemicIndexes) {
        int count = 0;
        int firstSelectedItemPosition = 0;
        Log.d(TAG, "selectItems - glycemicIndexes size = " + glycemicIndexes.size());
        GlycemicIndexGroup dummyGroup = new GlycemicIndexGroup("");
        for ( IGlycemicIndex glycemicIndex:glycemicIndexes){
            String title = glycemicIndex.getTitle();
            dummyGroup.setTitle(title.charAt(0) + "");
            Log.d(TAG, "selectItems - look for glycemicIndexGroup with title = " + dummyGroup.getTitle());
            int indexGlycemicGroup = mDatas.indexOf(dummyGroup);
            GlycemicIndexGroup glycemicIndexGroup = (GlycemicIndexGroup) mDatas.get(indexGlycemicGroup);
            Log.d(TAG, "selectItems - glycemicIndexGroup.isExpanded() = " + glycemicIndexGroup.isExpanded());
            if (!glycemicIndexGroup.isExpanded()){
               mOnItemClickListener.onLoadGIGroup(glycemicIndexGroup, null, indexGlycemicGroup);
            }
            int glycemicIndexPosition = mDatas.indexOf(glycemicIndex);
            if (count==0) firstSelectedItemPosition = glycemicIndexPosition;
            GlycemicIndex glycemicIndexFromList = (GlycemicIndex) mDatas.get(glycemicIndexPosition);
            glycemicIndexFromList.setSelected(true);

            count++;
        }
        notifyDataSetChanged();
        return firstSelectedItemPosition;
    }

    public interface OnItemClickListener {
        void onLoadGIGroup(GlycemicIndexGroup glycemicIndexGroup, View itemView, int layoutPosition);
        void onClickGIItem(View view, int layoutPosition);
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }

    class GroupViewHolder extends MainViewHolder{

        public TextView mTextView;
        public ImageView mImageView;

        public GroupViewHolder(final View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.gi_group_text);
            mImageView = (ImageView) itemView.findViewById(R.id.gi_group_image);
            mImageView.setColorFilter(mChevronColor);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onLoadGIGroup - view CLICKED");

                    final GlycemicIndexGroup glycemicIndexGroup = (GlycemicIndexGroup) mDatas.get(getLayoutPosition());
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onLoadGIGroup(glycemicIndexGroup, itemView, getLayoutPosition());
                    }
                }
            });
        }
    }

    class ItemViewHolder extends MainViewHolder{

        public TextView mTextView;
        private GlycemicIndexValueView mGlycemicIndexValueView;
        private LinearLayout mItemLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.gi_item_text);
            mGlycemicIndexValueView = (GlycemicIndexValueView) itemView.findViewById(R.id.gi_value);
            mItemLayout = (LinearLayout) itemView.findViewById(R.id.gi_item_layout);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClickGIItem - view CLICKED");
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onClickGIItem(view, getLayoutPosition());
                    }
                }
            });

        }
    }
}
