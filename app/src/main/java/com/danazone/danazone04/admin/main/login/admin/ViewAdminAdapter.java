package com.danazone.danazone04.admin.main.login.admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danazone.danazone04.admin.BaseAdapter;
import com.danazone.danazone04.admin.R;
import com.danazone.danazone04.admin.bean.Admin;
import com.danazone.danazone04.admin.bean.Users;

import java.util.ArrayList;
import java.util.List;

public class ViewAdminAdapter extends BaseAdapter implements Filterable {

    public interface OnUserClickListener {
        void onClickItem(Admin position);
    }
    private List<Admin> mList;
    private OnUserClickListener mListener;
    private List<Admin> mFilterList;
    private Filter mFilter;

    public ViewAdminAdapter(@NonNull Context context, List<Admin> list, OnUserClickListener listener) {
        super(context);
        mList = list;
        mFilterList = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_admin, parent, false);
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
        holder.mTvName.setText(mFilterList.get(position).getName());
        holder.mTvPhone.setText(mFilterList.get(position).getPhone());
        holder.mImgDelete.setOnClickListener(new View.OnClickListener() {
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
        private ImageView mImgDelete;


        public ViewHolderItem(View view) {
            super(view);
            mTvName = (TextView) view.findViewById(R.id.mTvName);
            mTvPhone = (TextView) view.findViewById(R.id.mTvPhone);
            mImgDelete = (ImageView) view.findViewById(R.id.mImgDelete);

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
                ArrayList<Admin> filteredList = new ArrayList<>();
                for (Admin data : mFilterList) {
                    if ( data.getPhone().toLowerCase().contains(charString) || data.getName().toLowerCase().contains(charString)) {
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
            mFilterList = (ArrayList<Admin>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}

