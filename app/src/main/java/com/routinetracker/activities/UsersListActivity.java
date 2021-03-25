package com.routinetracker.activities;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.routinetracker.R;
import com.routinetracker.adapters.UsersAdapter;
import com.routinetracker.databinding.ActivityUsersListBinding;
import com.routinetracker.interfaces.IClickListener;
import com.routinetracker.models.UsersModel;
import com.routinetracker.utils.StaticUtils;

import java.util.ArrayList;

public class UsersListActivity extends BaseActivity implements IClickListener, View.OnClickListener {

    private ActivityUsersListBinding activityUsersListBinding;
    private ArrayList<UsersModel> usersModelArrayList;

    @Override
    void initComponents() {
        activityUsersListBinding.imgBack.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUsersListBinding = DataBindingUtil.setContentView(this, R.layout.activity_users_list);
        initComponents();
        usersModelArrayList = new ArrayList<>();
        setAdapter();
    }

    private void setAdapter() {
        setDummyData();
        UsersAdapter usersAdapter = new UsersAdapter(usersModelArrayList, this);
        activityUsersListBinding.recyclerViewUsers.setAdapter(usersAdapter);
    }

    private void setDummyData() {
        for (int i = 0; i < 20; i++) {
            usersModelArrayList.add(new UsersModel());
        }
    }

    @Override
    public void onClick(View view, int position) {
        StaticUtils.showToast(this, getString(R.string.module_under_development));
    }

    @Override
    public void onLongClick(View view, int position) {
        StaticUtils.showToast(this, getString(R.string.module_under_development));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}