package com.example.danazone04.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.danazone04.admin.bean.Users;
import com.example.danazone04.admin.common.Common;
import com.example.danazone04.admin.common.ILoadMore;
import com.example.danazone04.admin.common.MySingleton;
import com.example.danazone04.admin.dialog.StartDialog;
import com.example.danazone04.admin.main.MainAdapter;
import com.example.danazone04.admin.main.info.ViewInfoUserActivity_;
import com.example.danazone04.admin.main.login.admin.ViewAdminActivity_;
import com.example.danazone04.admin.utils.ConnectionUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

@SuppressLint({"Registered", "NewApi"})
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements RecyclerView.OnScrollChangeListener {
    @ViewById
    RecyclerView mRecycleView;
    @ViewById
    EditText mEdtSearch;
    @ViewById
    ImageView mImgAdd;
    @ViewById
    ImageView mImgRemove;

    private MainAdapter mAdapter;
    private List<Users> mUsers;
    private AlertDialog alertDialog;
    @Extra
    String mCode;

    //Volley Request Queue
    private RequestQueue requestQueue;

    //The request counter to send ?page=1, ?page=2  requests
    private int requestCount = 1;


    @Override
    protected void afterView() {

        if (SessionManager.getInstance().getKeySaveId().equals("admin") && mCode.equals("admin")) {
            mImgAdd.setVisibility(View.VISIBLE);
            mImgRemove.setVisibility(View.VISIBLE);
        }
        alertDialog = new SpotsDialog(MainActivity.this);

        requestQueue = Volley.newRequestQueue(this);
        setUpAdapter();
        getData();
        mRecycleView.setOnScrollChangeListener(this);
        getTextSearch();
    }

    private void setUpAdapter() {
        RecyclerViewUtils.Create().setUpVertical(this, mRecycleView);
        mUsers = new ArrayList<>();

        mAdapter = new MainAdapter(MainActivity.this, mUsers, new MainAdapter.OnUserClickListener() {
            @Override
            public void onClickItem(Users position) {
                ViewInfoUserActivity_.intent(MainActivity.this).mUsers(position).start();
            }
        });
        mRecycleView.setAdapter(mAdapter);
    }

    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }

    //This method would return a JsonArrayRequest that will be added to the request queue
    private JsonArrayRequest getDataFromServer(int requestCount) {
        //Initializing ProgressBar
//        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
//
//        //Displaying Progressbar
        alertDialog.show();
        alertDialog.setMessage("Đang tải...");
        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Common.URL_NEW + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                        alertDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        alertDialog.dismiss();
                        //Toast.makeText(MainActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }


    //This method will parse json data
    private void parseData(JSONArray array) {
        alertDialog.dismiss();
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            Users users = new Users();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                users.setUsername(json.getString("username"));
                users.setPhone(json.getString("phone"));
                users.setEmail(json.getString("email"));
                users.setBirthday(json.getString("birthday"));
                users.setSex(json.getString("sex"));
                users.setBike(json.getString("bike"));
                users.setProvince(json.getString("province"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            mUsers.add(users);
        }

        //Notifying the adapter that data has been added or changed
        mAdapter.notifyDataSetChanged();
    }

//    private void loadData() {
//        alertDialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                Common.URL_VIEW_USERS, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                alertDialog.dismiss();
//                try {
//                    JSONArray jsonarray = new JSONArray(response);
//                    for (int i = 0; i < jsonarray.length(); i++) {
//                        Users users = new Users();
//                        JSONObject jsonobject = jsonarray.getJSONObject(i);
//                        String name = jsonobject.getString("username");
//                        String phone = jsonobject.getString("phone");
//                        String avatar = jsonobject.getString("avatar");
//                        String email = jsonobject.getString("email");
//                        String birthday = jsonobject.getString("birthday");
//                        String sex = jsonobject.getString("sex");
//                        String bike = jsonobject.getString("bike");
//                        String iduser = jsonobject.getString("iduser");
//                        String province = jsonobject.getString("province");
//
//                        users.setAvatar(avatar);
//                        users.setBike(bike);
//                        users.setBirthday(birthday);
//                        users.setSex(sex);
//                        users.setUsername(name);
//                        users.setEmail(email);
//                        users.setPhone(phone);
//                        users.setId(Integer.valueOf(iduser));
//                        users.setProvince(province);
//                        mUsers.add(users);
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                alertDialog.dismiss();
//                showAlertDialog("loi");
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> parms = new HashMap<>();
//                parms.put("key", "10");
//                return parms;
//            }
//        };
//        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
//    }

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

    @Click({R.id.mImgAdd, R.id.mImgRemove})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.mImgAdd:
                new StartDialog(this).show();
                break;

            case R.id.mImgRemove:
                ViewAdminActivity_.intent(this).start();
                break;
        }

    }


    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (isLastItemDisplaying(mRecycleView)) {
            //Calling the method getdata again
            getData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!ConnectionUtil.isConnected(this)) {
            showAlertDialog("Vui lòng kiểm tra kết nối internet");
            return;
        }
    }
}
