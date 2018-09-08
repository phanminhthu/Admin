package com.danazone.danazone04.admin.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.danazone.danazone04.admin.BaseDialog;
import com.danazone.danazone04.admin.R;
import com.danazone.danazone04.admin.common.Common;
import com.danazone.danazone04.admin.common.MySingleton;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class StartDialog extends BaseDialog implements View.OnClickListener {

    AlertDialog waitingDialog = new SpotsDialog(getContext());

    public StartDialog(Context context) {
        super(context);

    }

    private EditText mEdtName;
    private EditText mEdtCode;
    private EditText mEdtPhone;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        ImageView mImgCancelDialog = (ImageView) findViewById(R.id.mImgCancelDialog);
        TextView mTvCall = (TextView) findViewById(R.id.mTvSubmit);
        mEdtName = (EditText) findViewById(R.id.mEdtName);
        mEdtCode = (EditText) findViewById(R.id.mEdtCode);
        mEdtPhone = (EditText) findViewById(R.id.mEdtPhone);

        mImgCancelDialog.setOnClickListener(this);
        mTvCall.setOnClickListener(this);

    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_start;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mImgCancelDialog:
                dismiss();
                break;

            case R.id.mTvSubmit:
                waitingDialog.show();
                loadData();
                dismiss();
                break;
        }
    }

    private void loadData() {
        if (mEdtName.getText().toString().trim().equals("")) {
            showAlertDialog("Tên không được để trống");
            return;
        }
        if (mEdtCode.getText().toString().trim().equals("")) {
            showAlertDialog("Mã không được để trống");
            return;
        }
        if (mEdtPhone.getText().toString().trim().equals("")) {
            showAlertDialog("Số điện thoại không được để trống");
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Common.URL_ADD_ADMIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("thanhcong")) {
                    waitingDialog.dismiss();
                    showAlertDialog("Mã nhân viên: " + mEdtCode.getText().toString().trim());


                } else {
                    waitingDialog.dismiss();
                    showAlertDialog("Đăng ký thất bại!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                waitingDialog.dismiss();
                Toast.makeText(getContext(), "Có lỗi", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("username", mEdtName.getText().toString().trim());
                parms.put("phone", mEdtPhone.getText().toString().trim());
                parms.put("code", mEdtCode.getText().toString().trim());

                return parms;
            }
        };//ket thuc stringresquet
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }
}

