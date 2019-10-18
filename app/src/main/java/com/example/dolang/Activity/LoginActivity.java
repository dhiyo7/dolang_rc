package com.example.dolang.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolang.Converter.BaseResponse;
import com.example.dolang.Model.ModelUser;
import com.example.dolang.Network.ApiClient;
import com.example.dolang.Network.UserInterface;
import com.example.dolang.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private UserInterface userInterface = ApiClient.getUserInterface();
    private SharedPreferences setting;
    private ModelUser mUser = new ModelUser();
    private static  final  String TAG = "LoginActivity";
    private ImageButton btRegis;
    private TextView btnLogin;
    private TextInputEditText etEmail, etPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initView();
        doLogin();
    }

    private void initView() {
        btRegis  = findViewById(R.id.btRegis);
        btnLogin     = findViewById(R.id.btnLogin);
        btRegis.setOnClickListener(this);
        setting = getSharedPreferences("USER", MODE_PRIVATE);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

    private void doLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (!email.isEmpty() && !password.isEmpty()) {
                    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        etEmail.setError("Masukkan email dengan benar");
                        etEmail.requestFocus();
                        return;
                    }
                    if (password.length() < 6){
                        etPassword.setError("Kata sandi minimal enam karakter");
                        etPassword.requestFocus();
                        return;
                    }

//                    loading.setVisibility(View.VISIBLE);
                    btnLogin.setEnabled(false);
                    Call<BaseResponse<ModelUser>> user = userInterface.login(email, password);
                    user.enqueue(new Callback<BaseResponse<ModelUser>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<ModelUser>> call, Response<BaseResponse<ModelUser>> response) {
                            if (response.isSuccessful()) {
                                BaseResponse<ModelUser> body = response.body();
                                if (body.getStatus()) {
                                    mUser = body.getData();
                                    if (mUser != null) {
                                        System.out.println(mUser.getApi_token());
                                        setLogedIn(mUser.getApi_token(), String.valueOf(mUser.getId()));
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Respon sukses tanpa error", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    //LoginActivity gagal
                                    Toast.makeText(LoginActivity.this, "LoginActivity gagal", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Gagal Masuk", Toast.LENGTH_SHORT).show();
                            }
//                            loading.setVisibility(View.INVISIBLE);
                            btnLogin.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<ModelUser>> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Gagal terkoneksi ke server, silahkan cek koneksi", Toast.LENGTH_SHORT).show();
                            System.err.println(t.getMessage());
//                            loading.setVisibility(View.INVISIBLE);
                            btnLogin.setEnabled(true);
                        }
                    });
                }


            }
        });

    }

    private void setLogedIn(String api_token, String id) {

        SharedPreferences.Editor editor = setting.edit();
        editor.putString("TOKEN", api_token);
        editor.putString("USER_ID", id);
        editor.commit();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNotLoggedIn()){
            finish();
        }
    }

    private boolean isNotLoggedIn() {
        String token = setting.getString("TOKEN", "UNDIFINED");
        return token == null || token.equals("UNDIFINED");
    }

    @Override
    public void onClick(View v) {
        if (v == btRegis) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//            Pair[] pairs = new Pair[1];
//            pairs[0] = new Pair<View, String>(tvLogin, "tvLogin");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this);
            startActivity(intent, activityOptions.toBundle());
        }
    }

//    public void login(View view) {
//        Intent in = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(in);
//    }
}
