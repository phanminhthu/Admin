package com.example.danazone04.admin.main.info;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.example.danazone04.admin.BaseActivity;
import com.example.danazone04.admin.R;
import com.example.danazone04.admin.bean.Users;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@SuppressLint("Registered")
@EActivity(R.layout.activity_view_info_user)
public class ViewInfoUserActivity extends BaseActivity {
    @ViewById
    TextView mTvName;
    @ViewById
    TextView mTvPhone;
    @ViewById
    TextView mTvEmail;
    @ViewById
    TextView mTvBirthDay;
    @ViewById
    TextView mTvSex;
    @ViewById
    TextView mTvBike;

    @Extra
    Users mUsers;

    @Override
    protected void afterView() {
        if (mUsers != null) {
            mTvName.setText(mUsers.getUsername());
            mTvBike.setText(mUsers.getBike());
            mTvBirthDay.setText(mUsers.getBirthday());
            mTvEmail.setText(mUsers.getEmail());
            mTvSex.setText(mUsers.getSex());
            mTvPhone.setText(mUsers.getPhone());
        }

    }
}
