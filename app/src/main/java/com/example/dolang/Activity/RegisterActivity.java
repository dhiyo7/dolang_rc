package com.example.dolang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.dolang.Converter.BaseResponse;
import com.example.dolang.Model.ModelUser;
import com.example.dolang.Network.ApiClient;
import com.example.dolang.Network.UserInterface;
import com.example.dolang.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private RelativeLayout rlayout;
    private Animation animation;
    private TextInputEditText etUsername, etEmail, etPassword;
    private Button btnSignUp;
    private UserInterface userInterface = ApiClient.getUserInterface();
    private SharedPreferences setting;
    private ModelUser mUser = new ModelUser();
    private static final String TAG = "registerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initView();
        doregister();

        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);
    }



    private void initView() {

        btnSignUp = findViewById(R.id.btnSignUp);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        setting = getSharedPreferences("USER", MODE_PRIVATE);
    }

    private void doregister() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                if (!name.isEmpty() && !email.isEmpty() && !pass.isEmpty() && pass.length() > 6 && name.length() > 5 ){
//                    loading.setVisibility(View.VISIBLE);
                    btnSignUp.setEnabled(false);
                    Call<BaseResponse<ModelUser>> user_ = userInterface.register(name, email, pass);
                    user_.enqueue(new Callback<BaseResponse<ModelUser>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<ModelUser>> call, Response<BaseResponse<ModelUser>> response) {
                            if (response.isSuccessful()){
                                BaseResponse<ModelUser> body = response.body();
                                if (body.getStatus()){
                                    mUser = body.getData();
                                    if (mUser != null){
                                        Log.d(TAG, mUser.getApi_token());
                                        setLoggedIn(mUser.getApi_token(), String.valueOf(mUser.getId()));
                                        Intent a = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(a);
                                        finish();
                                        Toast.makeText(RegisterActivity.this, "Selamat datang "+mUser.getName(), Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Respons sukses tanpa error hehe", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else {
                                Toast.makeText(RegisterActivity.this, "daftar gagal", Toast.LENGTH_SHORT).show();
                            }
//                            loading.setVisibility(View.INVISIBLE);
                            btnSignUp.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<ModelUser>> call, Throwable t) {
                            Log.d(TAG, "LokerIT "+t.getMessage());
//                            loading.setVisibility(View.INVISIBLE);
                            btnSignUp.setEnabled(true);
                            Toast.makeText(RegisterActivity.this, "Ada yang tidak beres : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this, "Isikan form Nama minimal 5 karakter dan password 6", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setLoggedIn(String api_token, String id) {

        SharedPreferences.Editor editor = setting.edit();
        editor.putString("TOKEN", api_token);
        editor.putString("USER_ID", id);
        editor.commit();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
