package com.example.danazone04.admin.main.info;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.danazone04.admin.BaseActivity;
import com.example.danazone04.admin.R;
import com.example.danazone04.admin.bean.Users;

import org.androidannotations.annotations.Click;
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
    @ViewById
    RelativeLayout mRlCall;
    @ViewById
    RelativeLayout mRlEmail;

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

    @Click({R.id.mRlCall, R.id.mRlEmail})
    void onClich(View v) {
        switch (v.getId()) {
            case R.id.mRlCall:
                cellPhoneRider();
              break;

            case R.id.mRlEmail:
                Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
                email.putExtra(Intent.EXTRA_SUBJECT, "DaNa Bike");
                startActivity(Intent.createChooser(email, "DaNa Bike"));
                break;
        }
    }

    /**
     * call user
     * start trips
     */
    private void cellPhoneRider() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + mUsers.getPhone())));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    cellPhoneRider();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }

}
