package com.colinbradley.fwc.RecyclerViewForGear;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colinbradley.fwc.DatabaseAndData.FWCGear;
import com.colinbradley.fwc.R;

import java.util.List;

/**
 * Created by colinbradley on 11/5/16.
 */

public class Adapter extends RecyclerView.Adapter<Holder>{

    private List<FWCGear> mGearList;
    private OnItemSelectedListener mOnItemSelectedListener;

    public interface OnItemSelectedListener{
        void onItemSelected(int id);
    }

    public Adapter(List<FWCGear> gearList, OnItemSelectedListener onItemSelectedListener){
        mGearList = gearList;
        mOnItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final FWCGear currentItem = mGearList.get(position);

        holder.mName.setText(mGearList.get(position).getName());
        holder.mType.setText(mGearList.get(position).getType());
        holder.mImage.setBackgroundResource(mGearList.get(position).getImage());

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemSelectedListener.onItemSelected(currentItem.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGearList.size();
    }

    public void replaceData(List<FWCGear> newList){
        mGearList = newList;
        notifyDataSetChanged();
    }
}
