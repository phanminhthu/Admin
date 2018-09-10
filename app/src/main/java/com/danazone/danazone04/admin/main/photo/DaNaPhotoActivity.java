package com.danazone.danazone04.admin.main.photo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.danazone.danazone04.admin.BaseActivity;
import com.danazone.danazone04.admin.R;
import com.danazone.danazone04.admin.RecyclerViewUtils;
import com.danazone.danazone04.admin.bean.DaNaPhoto;
import com.danazone.danazone04.admin.bean.Users;
import com.danazone.danazone04.admin.common.Common;
import com.danazone.danazone04.admin.common.MySingleton;
import com.danazone.danazone04.admin.main.MainActivity;
import com.danazone.danazone04.admin.main.MainAdapter;
import com.danazone.danazone04.admin.main.info.ViewInfoUserActivity_;
import com.danazone.danazone04.admin.main.infoPhoto.ViewInfoPhotoActivity_;
import com.danazone.danazone04.admin.utils.ConnectionUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

@SuppressLint("Registered")
@EActivity(R.layout.activity_dana_photo)
public class DaNaPhotoActivity extends BaseActivity {
    @ViewById
    RecyclerView mRecycleView;
    @ViewById
    EditText mEdtSearch;
    @ViewById
    TextView mTvView;

    private DaNaPhotoAdapter mAdapter;
    private List<DaNaPhoto> mUsers;
    private AlertDialog alertDialog;

    @Override
    protected void afterView() {
        alertDialog = new SpotsDialog(DaNaPhotoActivity.this);
        setUpAdapter();
        loadData();
        //Search editText
        getTextSearch();
    }

    private void setUpAdapter() {
        RecyclerViewUtils.Create().setUpVertical(this, mRecycleView);
        mUsers = new ArrayList<>();

        mAdapter = new DaNaPhotoAdapter(DaNaPhotoActivity.this, mUsers, new DaNaPhotoAdapter.OnUserClickListener() {
            @Override
            public void onClickItem(DaNaPhoto position) {
                ViewInfoPhotoActivity_.intent(DaNaPhotoActivity.this).mUsers(position).start();
            }
        });
        mRecycleView.setAdapter(mAdapter);
    }

    private void loadData() {
        alertDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Common.URL_VIEW_PHOTO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        DaNaPhoto users = new DaNaPhoto();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String name = jsonobject.getString("username");
                        String phone = jsonobject.getString("phone");
                        String email = jsonobject.getString("email");
                        String birthday = jsonobject.getString("birthday");
                        String sex = jsonobject.getString("sex");
                        String bike = jsonobject.getString("bike");
                        String iduser = jsonobject.getString("iduser");
                        String province = jsonobject.getString("province");
                        String date = jsonobject.getString("date");

                        users.setBussiness(bike);
                        users.setBirthday(birthday);
                        users.setSex(sex);
                        users.setUsername(name);
                        users.setEmail(email);
                        users.setPhone(phone);
                        users.setId(Integer.valueOf(iduser));
                        users.setProvince(province);
                        users.setDate(date);
                        mUsers.add(users);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
                mTvView.setText("Thành Viên: " + mUsers.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alertDialog.dismiss();
                showAlertDialog("loi");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    /**
     * Get text search enter editText
     */
    private void getTextSearch() {
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
