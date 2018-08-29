package com.example.danazone04.admin.main.login;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.danazone04.admin.BaseActivity;
import com.example.danazone04.admin.MainActivity_;
import com.example.danazone04.admin.R;
import com.example.danazone04.admin.SessionManager;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@SuppressLint("Registered")
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewById
    EditText mEdtLogin;
    @ViewById
    TextView mTvSubmit;
    private String code;

    @Override
    protected void afterView() {
    }

    @Click(R.id.mTvSubmit)
    void onClick(View v) {
        if (mEdtLogin.getText().toString().trim().equals("")) {
            showAlertDialog("Vui lòng nhập mã nhân viên");
            return;
        }
        if (mEdtLogin.getText().toString().trim().equals("admin")) {
            SessionManager.getInstance().setKeySaveId(mEdtLogin.getText().toString().trim());
            MainActivity_.intent(this).mCode("admin").start();
        }else{
            MainActivity_.intent(this).mCode(mEdtLogin.getText().toString().trim()).start();
        }
    }
}