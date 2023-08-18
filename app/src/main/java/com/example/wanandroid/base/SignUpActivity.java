package com.example.wanandroid.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.wanandroid.MainActivity;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText rePassword, account, password;
    MaterialButton signup;
    AppCompatImageButton back;
    String strPassword, strRePassword, strAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        rePassword = findViewById(R.id.rePassword);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        back = findViewById(R.id.back);
        initListener();

    }

    private void initListener() {
        rePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strRePassword = s.toString().trim();

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strPassword = s.toString().trim();
            }
        });

        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strAccount = s.toString().trim();
            }
        });

        signup.setOnClickListener(v -> {
            signup.setText("注册中...");
            if (strAccount.isEmpty()) {
                Toast.makeText(this, "账号不能为空！", Toast.LENGTH_SHORT).show();
            } else if (strPassword.isEmpty()) {
                Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            } else if (strRePassword.isEmpty()) {
                Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            } else if (!strPassword.equals(strRePassword)) {
                Toast.makeText(this, "两次输入的密码不相同！", Toast.LENGTH_SHORT).show();
            } else {
                Call<MessageBean> call = HttpUtils.getUserService().register(strAccount, strPassword, strRePassword);
                call.enqueue(new Callback<MessageBean>() {
                    @Override
                    public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if (response.body().getErrorCode() == 0) {
                                Toast.makeText(SignUpActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                            Toast.makeText(SignUpActivity.this, response.body().getErrorMsg(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                        Toast.makeText(SignUpActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            signup.setText("注册");
        });

        back.setOnClickListener(v -> {
            finish();
        });

    }


}