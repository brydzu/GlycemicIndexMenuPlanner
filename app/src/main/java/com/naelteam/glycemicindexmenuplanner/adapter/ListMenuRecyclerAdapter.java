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
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.ProductGroup;
import com.naelteam.glycemicindexmenuplanner.model.IGlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.view.GlycemicIndexValueView;

import java.util.ArrayList;
import java.util.List;


public class ListMenuRecyclerAdapter extends RecyclerView.Adapter<ListMenuRecyclerAdapter.MainViewHolder> {

    private final String  TAG = ListMenuRecyclerAdapter.class.getSimpleName();

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<IGlycemicIndex> mDatas=new ArrayList<IGlycemicIndex>();

    private int mChevronColor;

    public ListMenuRecyclerAdapter(Context context){
        mContext = context;
    }

    public ListMenuRecyclerAdapter(Context context, List<IGlycemicIndex> glycemicIndexes, OnItemClickListener clickListener){
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_menu_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder viewHolder, int position) {

        if (viewHolder instanceof GroupViewHolder) {
            final ProductGroup productGroup = (ProductGroup) mDatas.get(position);
            final GroupViewHolder groupViewHolder = (GroupViewHolder) viewHolder;
            groupViewHolder.mTextView.setText(productGroup.getTitle());

            if (productGroup.isExpanded()){
                groupViewHolder.mImageView.setImageResource(R.drawable.ic_expand_more_black_36dp);
            }else {
                groupViewHolder.mImageView.setImageResource(R.drawable.ic_chevron_right_black_36dp);
            }
            groupViewHolder.mImageView.setColorFilter(mChevronColor);
        }else {
            final Product product = (Product) mDatas.get(position);
            final ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            itemViewHolder.mTextView.setText(product.getTitle());
            if (product.isSelected()){
                itemViewHolder.mItemLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue_light));
            }else {
                itemViewHolder.mItemLayout.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
            }
            itemViewHolder.mGlycemicIndexValueView.setValue(product.getValue());

            //Log.d(TAG, "onBindViewHolder, position = " + position + ", view = " + itemViewHolder.mGlycemicIndexValueView + ", product title = " + product.getTitle() + ", value = " + product.getValue());

            GlycemicIndexValueRule.setGlycemicIndexValue(mContext, itemViewHolder.mGlycemicIndexValueView, product.getValue());
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

    public void expandGlycemicGroup(ProductGroup productGroup, List<IGlycemicIndex> glycemicIndexes, int position){
        Log.d(TAG, "expandGlycemicGroup - position = " + position);

        productGroup.setExpanded(true);
        productGroup.setNbItems(glycemicIndexes.size());

        addAll(glycemicIndexes, position + 1, productGroup.getNbItems());
    }

    public void collapseGlycemicGroup(ProductGroup productGroup, int position){
        removeAll(position + 1, productGroup.getNbItems());
        productGroup.setExpanded(false);
        productGroup.setNbItems(0);
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
        ProductGroup dummyGroup = new ProductGroup("");
        for ( IGlycemicIndex glycemicIndex:glycemicIndexes){
            String title = glycemicIndex.getTitle();
            dummyGroup.setTitle(title.charAt(0) + "");
            Log.d(TAG, "selectItems - look for productGroup with title = " + dummyGroup.getTitle());
            int indexGlycemicGroup = mDatas.indexOf(dummyGroup);
            ProductGroup productGroup = (ProductGroup) mDatas.get(indexGlycemicGroup);
            Log.d(TAG, "selectItems - productGroup.isExpanded() = " + productGroup.isExpanded());
            if (!productGroup.isExpanded()){
               mOnItemClickListener.onLoadGIGroup(productGroup, null, indexGlycemicGroup);
            }
            int glycemicIndexPosition = mDatas.indexOf(glycemicIndex);
            if (count==0) firstSelectedItemPosition = glycemicIndexPosition;
            Product productFromList = (Product) mDatas.get(glycemicIndexPosition);
            productFromList.setSelected(true);

            count++;
        }
        notifyDataSetChanged();
        return firstSelectedItemPosition;
    }

    public interface OnItemClickListener {
        void onLoadGIGroup(ProductGroup productGroup, View itemView, int layoutPosition);
        void onClickGIItem(Product product, int layoutPosition);
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

                    final ProductGroup productGroup = (ProductGroup) mDatas.get(getLayoutPosition());
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onLoadGIGroup(productGroup, itemView, getLayoutPosition());
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
                    final Product product = (Product) mDatas.get(getLayoutPosition());

                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onClickGIItem(product, getLayoutPosition());
                    }
                }
            });

        }
    }
}
