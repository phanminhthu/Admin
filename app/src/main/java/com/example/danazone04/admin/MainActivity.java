package com.example.danazone04.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.danazone04.admin.bean.Users;
import com.example.danazone04.admin.common.Common;
import com.example.danazone04.admin.common.MySingleton;
import com.example.danazone04.admin.main.MainAdapter;
import com.example.danazone04.admin.main.info.ViewInfoUserActivity_;

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
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById
    RecyclerView mRecycleView;
    @ViewById
    EditText mEdtSearch;

    private MainAdapter mAdapter;
    private List<Users> mUsers;
    private AlertDialog alertDialog;

    @Override
    protected void afterView() {
        alertDialog = new SpotsDialog(MainActivity.this);
        setUpAdapter();
        loadData();
        //Search editText
        getTextSearch();
    }

    private void setUpAdapter() {
        RecyclerViewUtils.Create().setUpVertical(this, mRecycleView);
        mUsers = new ArrayList<>();

        mAdapter = new MainAdapter(MainActivity.this, mUsers, new MainAdapter.OnUserClickListener() {
            @Override
            public void onClickItem(Users position) {
                Toast.makeText(MainActivity.this, "" + position.getBirthday(), Toast.LENGTH_SHORT).show();
                ViewInfoUserActivity_.intent(MainActivity.this).mUsers(position).start();
            }
        });
        mRecycleView.setAdapter(mAdapter);
    }

    private void loadData() {
        alertDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Common.URL_VIEW_USERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        Users users = new Users();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String name = jsonobject.getString("username");
                        String phone = jsonobject.getString("phone");
                        String avatar = jsonobject.getString("avatar");
                        String email = jsonobject.getString("email");
                        String birthday = jsonobject.getString("birthday");
                        String sex = jsonobject.getString("sex");
                        String bike = jsonobject.getString("bike");
                        String iduser = jsonobject.getString("iduser");

                        users.setAvatar(avatar);
                        users.setBike(bike);
                        users.setBirthday(birthday);
                        users.setSex(sex);
                        users.setUsername(name);
                        users.setEmail(email);
                        users.setPhone(phone);
                        users.setId(Integer.valueOf(iduser));
                        mUsers.add(users);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alertDialog.dismiss();
                showAlertDialog("loi");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("key", "15");
                return parms;
            }
        };
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
