package com.danazone.danazone04.admin.main.login.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.danazone.danazone04.admin.BaseActivity;
import com.danazone.danazone04.admin.R;
import com.danazone.danazone04.admin.RecyclerViewUtils;
import com.danazone.danazone04.admin.bean.Admin;
import com.danazone.danazone04.admin.common.Common;
import com.danazone.danazone04.admin.common.MySingleton;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

@SuppressLint("Registered")
@EActivity(R.layout.activity_view_admin)
public class ViewAdminActivity extends BaseActivity {
    @ViewById
    RecyclerView mRecycleView;
    @ViewById
    EditText mEdtSearch;

    private ViewAdminAdapter mAdapter;
    private List<Admin> mUsers;
    private AlertDialog alertDialog;

    @Override
    protected void afterView() {
        alertDialog = new SpotsDialog(this);
        setUpAdapter();
        loadData();
        //Search editText
        getTextSearch();
    }

    private void setUpAdapter() {
        RecyclerViewUtils.Create().setUpVertical(this, mRecycleView);
        mUsers = new ArrayList<>();

        mAdapter = new ViewAdminAdapter(ViewAdminActivity.this, mUsers, new ViewAdminAdapter.OnUserClickListener() {
            @Override
            public void onClickItem(final Admin position) {
                alertDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Common.URL_DELETE_ADMIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("thanhcong")) {
                            alertDialog.dismiss();
                            mUsers.remove(position);
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        alertDialog.dismiss();
                        showAlertDialog("loi");
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parms = new HashMap<>();
                        parms.put("code", position.getCode());
                        return parms;
                    }
                };
                MySingleton.getInstance(ViewAdminActivity.this).addToRequestQueue(stringRequest);
            }
        });
        mRecycleView.setAdapter(mAdapter);
    }

    private void loadData() {
        alertDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Common.URL_VIEW_ADMIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        Admin users = new Admin();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String name = jsonobject.getString("username");
                        String phone = jsonobject.getString("phone");
                        String code = jsonobject.getString("code");
                        String iduser = jsonobject.getString("iduser");

                        users.setName(name);
                        users.setCode(code);
                        users.setPhone(phone);
                        users.setId(Integer.valueOf(iduser));
                        mUsers.add(users);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
              //  mAdapter.notifyDataSetChanged();
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
}
