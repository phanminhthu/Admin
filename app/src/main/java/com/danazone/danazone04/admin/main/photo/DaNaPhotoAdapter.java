package com.danazone.danazone04.admin.main.photo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danazone.danazone04.admin.BaseAdapter;
import com.danazone.danazone04.admin.R;
import com.danazone.danazone04.admin.bean.DaNaPhoto;
import com.danazone.danazone04.admin.bean.Users;

import java.util.ArrayList;
import java.util.List;

public class DaNaPhotoAdapter extends BaseAdapter implements Filterable {

    public interface OnUserClickListener {
        void onClickItem(DaNaPhoto position);
    }
    private List<DaNaPhoto> mList;
    private OnUserClickListener mListener;
    private List<DaNaPhoto> mFilterList;
    private Filter mFilter;

    public DaNaPhotoAdapter(@NonNull Context context, List<DaNaPhoto> list, OnUserClickListener listener) {
        super(context);
        mList = list;
        mFilterList = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_photo, parent, false);
        return new ViewHolderItem(view);
    }

    /**
     * onBindHolder Item
     *
     * @param holder
     * @param position
     */
    private void onBindViewHolderItem(ViewHolderItem holder, final int position) {
      //  Users mRun = mList.get(position);
        holder.mTvName.setText(mFilterList.get(position).getUsername());
        holder.mTvPhone.setText(mFilterList.get(position).getPhone());
        holder.mTvProvince.setText(mFilterList.get(position).getProvince());
        holder.mTvDate.setText(mFilterList.get(position).getDate());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickItem(mFilterList.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolderItem((ViewHolderItem) holder, position);
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new UserFilter();
        }
        return mFilter;
    }

    /**
     * ViewHolderItem
     */
    private class ViewHolderItem extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvPhone;
        private TextView mTvProvince;
        private TextView mTvDate;
        private LinearLayout mView;

        public ViewHolderItem(View view) {
            super(view);
            mTvName = (TextView) view.findViewById(R.id.mTvName);
            mTvPhone = (TextView) view.findViewById(R.id.mTvPhone);
            mTvProvince = (TextView) view.findViewById(R.id.mTvProvince);
            mTvDate = (TextView) view.findViewById(R.id.mTvDate);
            mView = (LinearLayout) view.findViewById(R.id.mView);
        }
    }

    /**
     * Filter list phone codes
     */
    private class UserFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                mFilterList = mList;
            } else {
                ArrayList<DaNaPhoto> filteredList = new ArrayList<>();
                for (DaNaPhoto data : mFilterList) {
                    if ( data.getPhone().toLowerCase().contains(charString)
                            || data.getUsername().toLowerCase().contains(charString)
                            || data.getProvince().toLowerCase().contains(charString)) {
                        filteredList.add(data);
                    }
                }
                mFilterList = filteredList;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = mFilterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mFilterList = (ArrayList<DaNaPhoto>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}

