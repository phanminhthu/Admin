package com.danazone.danazone04.admin.main.login;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.danazone.danazone04.admin.BaseActivity;
import com.danazone.danazone04.admin.MainActivity_;
import com.danazone.danazone04.admin.R;
import com.danazone.danazone04.admin.SessionManager;
import com.danazone.danazone04.admin.utils.ConnectionUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

@SuppressLint("Registered")
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewById
    EditText mEdtLogin;
    @ViewById
    TextView mTvSubmit;

    @Override
    protected void afterView() {
        if (!ConnectionUtil.isConnected(this)) {
            showAlertDialog(getResources().getString(R.string.internet));
            return;
        }
    }

    @Click(R.id.mTvSubmit)
    void onClick(View v) {
        if (ConnectionUtil.isConnected(this)) {
            if (mEdtLogin.getText().toString().trim().equals("")) {
                showAlertDialog(getResources().getString(R.string.id));
                return;
            }
            if (mEdtLogin.getText().toString().trim().equals("admin")) {
                SessionManager.getInstance().setKeySaveId(mEdtLogin.getText().toString().trim());
                MainActivity_.intent(this).mCode("admin").start();
                finish();
            } else {
                MainActivity_.intent(this).mCode(mEdtLogin.getText().toString().trim()).start();
                finish();
            }
        } else {
            showAlertDialog(getResources().getString(R.string.internet));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!ConnectionUtil.isConnected(this)) {
            showAlertDialog(getResources().getString(R.string.internet));
            return;
        }
    }
}
