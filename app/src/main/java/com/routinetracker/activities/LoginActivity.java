package com.routinetracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.routinetracker.R;
import com.routinetracker.databinding.ActivityLoginBinding;
import com.routinetracker.utils.StaticUtils;
import com.routinetracker.utils.dbutils.DBHelper;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ActivityLoginBinding activityLoginBinding;

    @Override
    void initComponents() {
        activityLoginBinding.txtForgotPassword.setOnClickListener(this);
        activityLoginBinding.txtLogin.setOnClickListener(this);
        activityLoginBinding.txtNewUser.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initComponents();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtLogin:
                performLogin();
                break;
            case R.id.txtNewUser:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.txtForgotPassword:
                StaticUtils.showToast(this, R.string.module_under_development);
                break;
            default:
                break;
        }
    }

    private void performLogin() {
        String validationMessage = checkValidations();
        if (TextUtils.isEmpty(validationMessage)) {
            String email = activityLoginBinding.edtEmailAddress.getText().toString().trim();
            String password = activityLoginBinding.edtPassword.getText().toString().trim();
            if (new DBHelper(this).performLogin( email, password)) {
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
            } else {
                startActivity(new Intent(this, RegisterActivity.class));
//                startActivity(new Intent(this, MainActivity.class));
//                finishAffinity();
            }
        } else StaticUtils.showToast(this, validationMessage);
    }

    private String checkValidations() {
        String email = activityLoginBinding.edtEmailAddress.getText().toString().trim();
        String password = activityLoginBinding.edtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            activityLoginBinding.edtEmailAddress.requestFocus();
            return "Please enter Email Address";
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            activityLoginBinding.edtEmailAddress.requestFocus();
            return "Please enter a valid Email Address";
        }
        if (TextUtils.isEmpty(password)) {
            activityLoginBinding.edtPassword.requestFocus();
            return "Please enter Password";
        }
        if (password.length() < 6) {
            activityLoginBinding.edtPassword.requestFocus();
            return "Password length must be above 6.";
        }
        return "";
    }
}