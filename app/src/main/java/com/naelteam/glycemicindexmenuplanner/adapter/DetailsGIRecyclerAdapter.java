package com.naelteam.glycemicindexmenuplanner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndexGroup;
import com.naelteam.glycemicindexmenuplanner.model.WikSection;

import java.util.ArrayList;
import java.util.List;


public class DetailsGIRecyclerAdapter extends RecyclerView.Adapter<DetailsGIRecyclerAdapter.MainViewHolder> {

    public final static int SECTION_VIEW_TYPE = 0;
    public final static int SECTION_CONTENT_VIEW_TYPE = 1;

    private final String  TAG = DetailsGIRecyclerAdapter.class.getSimpleName();

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<WikSection> mDatas=new ArrayList<WikSection>();

    public DetailsGIRecyclerAdapter(Context context){
        mContext = context;
    }

    public DetailsGIRecyclerAdapter(Context context, OnItemClickListener clickListener){
        mContext = context;
        mOnItemClickListener = clickListener;
    }

    public void add(WikSection wikSection){
        mDatas.add(wikSection);
        notifyItemInserted(mDatas.size());
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == SECTION_VIEW_TYPE){
            View view = LayoutInflater.from(mContext).inflate(R.layout.details_gi_section, viewGroup, false);
            return new SectionTitleViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.details_gi_section_content, viewGroup, false);
            return new SectionContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(MainViewHolder viewHolder, int position) {

        final WikSection section = mDatas.get(position);
        if (viewHolder instanceof SectionTitleViewHolder) {
            TextView textView = ((SectionTitleViewHolder) viewHolder).mTitleTextView;
            textView.setText(section.getTitle());
            if (section.isSubTitle()){
                textView.setTextSize(18);
                ((SectionTitleViewHolder) viewHolder).mDivider.setVisibility(View.GONE);
            }
        }else {
            ((SectionContentViewHolder) viewHolder).mDescriptionTextView.setText(section.getDescription());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).getDescription() == null){
            return SECTION_VIEW_TYPE;
        }else {
            return SECTION_CONTENT_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addAll(List<WikSection> wikSections, int position, int nbItems) {
        Log.d(TAG, "addAll - position = " + position + ", nbItems = " + nbItems);
        if (nbItems > 0) {
            int i = position;
            for (WikSection wikSection : wikSections) {
                mDatas.add(i, wikSection);
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

    public interface OnItemClickListener {
        void onLoadGIGroup(GlycemicIndexGroup glycemicIndexGroup, View itemView, int layoutPosition);
        void onClickGIItem(GlycemicIndex glycemicIndex, int layoutPosition);
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }

    class SectionTitleViewHolder extends MainViewHolder{

        public TextView mTitleTextView;
        public View mDivider;

        public SectionTitleViewHolder(final View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.details_gi_section_text);
            mDivider = itemView.findViewById(R.id.details_gi_section_divider);


            /*itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onLoadGIGroup - view CLICKED");

                    final GlycemicIndexGroup glycemicIndexGroup = (GlycemicIndexGroup) mDatas.get(getLayoutPosition());
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onLoadGIGroup(glycemicIndexGroup, itemView, getLayoutPosition());
                    }
                }
            });*/
        }
    }

    class SectionContentViewHolder extends MainViewHolder{

        public TextView mDescriptionTextView;

        public SectionContentViewHolder(View itemView) {
            super(itemView);

            mDescriptionTextView = (TextView) itemView.findViewById(R.id.details_gi_section_content_description);

            /*itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClickGIItem - view CLICKED");
                    final WikSection section = (WikSection) mDatas.get(getLayoutPosition());

                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onClickGIItem(section, getLayoutPosition());
                    }
                }
            });*/

        }
    }
}
