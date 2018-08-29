package com.example.danazone04.admin.main;

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

import com.example.danazone04.admin.BaseAdapter;
import com.example.danazone04.admin.R;
import com.example.danazone04.admin.bean.Users;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends BaseAdapter implements Filterable {

    public interface OnUserClickListener {
        void onClickItem(Users position);
    }
    private List<Users> mList;
    private OnUserClickListener mListener;
    private List<Users> mFilterList;
    private Filter mFilter;

    public MainAdapter(@NonNull Context context, List<Users> list, OnUserClickListener listener) {
        super(context);
        mList = list;
        mFilterList = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_users, parent, false);
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
        holder.mTvBike.setText(mFilterList.get(position).getBike());
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
        private TextView mTvBike;
        private LinearLayout mView;

        public ViewHolderItem(View view) {
            super(view);
            mTvName = (TextView) view.findViewById(R.id.mTvName);
            mTvPhone = (TextView) view.findViewById(R.id.mTvPhone);
            mTvBike = (TextView) view.findViewById(R.id.mTvBike);
            mView = (LinearLayout) view.findViewById(R.id.mView);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null) {
//                        mListener.onClickItem(MainAdapter.this.getItemViewType(getAdapterPosition()));
//                    }
//                }
//            });
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
                ArrayList<Users> filteredList = new ArrayList<>();
                for (Users data : mFilterList) {
                    if ( data.getPhone().toLowerCase().contains(charString)) {
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
            mFilterList = (ArrayList<Users>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}

