package me.shumon.ipassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import me.shumon.tabbedapp.R;

/**
 * Created by kutimuti on 2/17/15.
 */
public class HomeFragmentAdapter extends BaseAdapter{

    private Context mContext;
    private FragmentManager mManager;

    private final List<HomeFragmentItemDescription> mBlocks = new ArrayList<HomeFragmentItemDescription>();


    // constructor
    public HomeFragmentAdapter(Context context, FragmentManager manager) {

        mContext = context;
        mManager = manager;
    }


    // Adds a box
    public void add(HomeFragmentItemDescription aBlock) {

        mBlocks.add(aBlock);
        notifyDataSetChanged();
    }

    // removes a box
    public void remove(HomeFragmentItemDescription aBlock) {

        mBlocks.remove(aBlock);
        notifyDataSetChanged();

    }

    //Notify any changes in adapter
    public void listItemChangedNotify(){
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mBlocks.size();
    }

    @Override
    public Object getItem(int position) {
        return mBlocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the current item
        HomeFragmentItemDescription aBlockItem = (HomeFragmentItemDescription) getItem(position);

        // Inflate the View for this item
        RelativeLayout blockLayout = null;

        // Fill in with specific data
        HomeFragmentItemHolder mBlockHolder = null;

        // if layout is null then create one
        if (null == convertView) {
            blockLayout = (RelativeLayout) LayoutInflater.from(mContext)
                    .inflate(R.layout.home_each_item_block, parent,
                            false);

            mBlockHolder = new HomeFragmentItemHolder();

            mBlockHolder.mBlockColorText = (TextView) blockLayout.findViewById(R.id.blockText);

            blockLayout.setTag(mBlockHolder);

        }
        else { // if not null then reuse it
            blockLayout = (RelativeLayout) convertView;
            mBlockHolder = (HomeFragmentItemHolder) convertView.getTag();
        }

        /**
         * Cache all the views..
         */
        mBlockHolder.mBlockColorText.setBackgroundColor(aBlockItem.getBlockColor());
        // Return the View
        return blockLayout;

    }



}
