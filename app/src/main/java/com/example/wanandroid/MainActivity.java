package com.example.wanandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroid.base.SignUpActivity;
import com.example.wanandroid.base.home.BlogActivity;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.sharedPreference.SaveAccount;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.button.MaterialButton;

import org.litepal.tablemanager.Connector;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Voyager
 * @className MainActivity
 * @description 登录界面
 * @createTime
 */

public class MainActivity extends AppCompatActivity {
    MaterialButton login, signup;
    EditText password, account;
    CheckBox auto_login;
    String accountStr, passwordStr;
    private Context context;
    Map<String, String> map = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpUtils.getInstance();
        map = SaveAccount.getAccountInfo(this);
        context = getBaseContext();
        Connector.getDatabase();
        initView();
        initListener();


    }


    private void initListener() {
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SaveAccount.openAutoLogin(MainActivity.this);
                } else {
                    SaveAccount.stopAutoLogin(MainActivity.this);
                }
            }
        });

        login.setOnClickListener(v -> {
            accountStr = account.getText().toString().trim();
            passwordStr = password.getText().toString().trim();
            if (TextUtils.isEmpty(accountStr)) {
                Toast.makeText(context, "请输入账号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(passwordStr)) {
                Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }

            Call<MessageBean> call = HttpUtils.getUserService().login(accountStr, passwordStr);
            call.enqueue(new Callback<MessageBean>() {
                @Override
                public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                    assert response.body() != null;
                    if (response.isSuccessful()) {
                        MessageBean message = response.body();
                        if (0 == message.getErrorCode()) {
                            Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                            SaveAccount.saveAccountInfo(MainActivity.this, accountStr, passwordStr);
                            Intent intent = new Intent(MainActivity.this, BlogActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                    Log.e("MainActivity_login", "回调失败：" + t.getMessage() + "," + t.toString());
                }
            });
        });
        signup.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                passwordStr = editable.toString().trim();
            }
        });
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                accountStr = editable.toString().trim();
            }
        });

    }

    private void initView() {
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        password = findViewById(R.id.password);
        account = findViewById(R.id.account);
        account.setText(map.get("account"));
        password.setText(map.get("password"));
        auto_login = findViewById(R.id.auto_login);
    }

}