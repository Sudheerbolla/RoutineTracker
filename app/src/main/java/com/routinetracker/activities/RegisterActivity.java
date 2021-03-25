package com.routinetracker.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.routinetracker.R;
import com.routinetracker.databinding.ActivityRegisterBinding;
import com.routinetracker.utils.Constants;
import com.routinetracker.utils.StaticUtils;
import com.routinetracker.utils.dbutils.DBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ActivityRegisterBinding activityRegisterBinding;

    @Override
    void initComponents() {
        activityRegisterBinding.txtDOB.setOnClickListener(this);
        activityRegisterBinding.txtExistingUser.setOnClickListener(this);
        activityRegisterBinding.txtRegister.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        initComponents();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtRegister:
                performLogin();
                break;
            case R.id.txtExistingUser:
                onBackPressed();
                break;
            case R.id.txtDOB:
                openDatePicker();
                break;
            default:
                break;
        }
    }

    private void openDatePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> activityRegisterBinding.txtDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void performLogin() {
        String validationMessage = checkValidations();
        if (TextUtils.isEmpty(validationMessage)) {
            new DBHelper(this).addUser(getUserJSONObject());
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        } else StaticUtils.showToast(this, validationMessage);
    }

    private JSONObject getUserJSONObject() {
        JSONObject jsonObject = new JSONObject();
        String email = activityRegisterBinding.edtEmailAddress.getText().toString().trim();
        String name = activityRegisterBinding.edtName.getText().toString().trim();
        String password = activityRegisterBinding.edtPassword.getText().toString().trim();
        int favNumb = Integer.parseInt(activityRegisterBinding.edtFavNumber.getText().toString().trim());
        String dob = activityRegisterBinding.txtDOB.getText().toString().trim();
        try {
            jsonObject.put(Constants.USER_NAME, name);
            jsonObject.put(Constants.USER_DOB, dob);
            jsonObject.put(Constants.USER_EMAIL, email);
            jsonObject.put(Constants.USER_PASSWORD, password);
            jsonObject.put(Constants.USER_FAV_NUMBER, favNumb);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private String checkValidations() {
        String email = activityRegisterBinding.edtEmailAddress.getText().toString().trim();
        String name = activityRegisterBinding.edtName.getText().toString().trim();
        String password = activityRegisterBinding.edtPassword.getText().toString().trim();
        String cnfpassword = activityRegisterBinding.edtConfirmPassword.getText().toString().trim();
        String favNumb = activityRegisterBinding.edtFavNumber.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            activityRegisterBinding.edtName.requestFocus();
            return "Please enter User Name";
        }
        if (TextUtils.isEmpty(email)) {
            activityRegisterBinding.edtEmailAddress.requestFocus();
            return "Please enter Email Address";
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            activityRegisterBinding.edtEmailAddress.requestFocus();
            return "Please enter a valid Email Address";
        }
        if (TextUtils.isEmpty(password)) {
            activityRegisterBinding.edtPassword.requestFocus();
            return "Please enter Password";
        }
        if (password.length() < 6) {
            activityRegisterBinding.edtPassword.requestFocus();
            return "Password length must be above 6.";
        }
        if (TextUtils.isEmpty(cnfpassword)) {
            activityRegisterBinding.edtConfirmPassword.setText("");
            activityRegisterBinding.edtConfirmPassword.requestFocus();
            return "Please Confirm Password";
        }
        if (cnfpassword.length() < 6) {
            activityRegisterBinding.edtConfirmPassword.setText("");
            activityRegisterBinding.edtConfirmPassword.requestFocus();
            return "Confirm Password length must be above 6.";
        }
        if (!password.equals(cnfpassword)) {
            activityRegisterBinding.edtConfirmPassword.setText("");
            activityRegisterBinding.edtConfirmPassword.requestFocus();
            return "Password should match Confirm Password.";
        }
        if (TextUtils.isEmpty(favNumb)) {
            activityRegisterBinding.edtFavNumber.requestFocus();
            return "Please enter Your favourite Number";
        }
        if (TextUtils.isEmpty(favNumb)) {
            activityRegisterBinding.edtFavNumber.requestFocus();
            return "Please enter Your favourite Number";
        }
        if (TextUtils.isEmpty(activityRegisterBinding.txtDOB.getText().toString().trim())) {
            return "Please select your Date of Birth";
        }
        return "";
    }
}