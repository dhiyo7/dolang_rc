package com.example.dolang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dolang.Adapter.AdapterKomentar;
import com.example.dolang.Converter.BaseResponse;
import com.example.dolang.Model.ModelKomentar;
import com.example.dolang.Network.ApiClient;
import com.example.dolang.Network.TourInterface;
import com.example.dolang.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAcitivity extends AppCompatActivity {

    private TourInterface api;
    private RecyclerView rv_comment;
    private EditText et_message;
    private RelativeLayout send_comment;
    private SharedPreferences setting;
    private ImageView send;
    private TourInterface tourInterface = ApiClient.getApiInterfaceService();
    private ConstraintLayout btm_logged, btm_not_logged;
    private ModelKomentar mKomen = new ModelKomentar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_acitivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Komentar");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        init();
//        sendMessage();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isNotLoggedIn()){
            btm_not_logged.setVisibility(View.VISIBLE);
            btm_logged.setVisibility(View.INVISIBLE);
        }else{
            btm_not_logged.setVisibility(View.INVISIBLE);
            btm_logged.setVisibility(View.VISIBLE);
        }

        getCOmments();
        sendMessage();

//        setMessage();
    }


    private void setMessage(String api_token, String tour_id, String messages) {
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("TOKEN", api_token);
        editor.putString("TOUR_ID", tour_id);
        editor.putString("MESSAGE" , messages);
    }

    private String getTourId(){ return String.valueOf(getIntent().getStringExtra("TOUR_ID"));}

    private List<ModelKomentar> getCOmments() { return getIntent().getParcelableArrayListExtra("COMMENTS");
    }

    private String getToken(){
        SharedPreferences settings = getSharedPreferences("USER", MODE_PRIVATE);
        return settings.getString("TOKEN", "UNDIFINED");
    }

    private boolean isNotLoggedIn() {
        String token = setting.getString("TOKEN", "UNDIFINED");
        return token == null || token.equals("UNDIFINED");
    }


    private void init(){
        btm_logged = findViewById(R.id.bottom_bar_logged_in);
        btm_not_logged = findViewById(R.id.bottom_bar_not_logged_in);
        btm_not_logged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CommentAcitivity.this, LoginActivity.class);
                startActivity(i);

            }
        });
        setting = getSharedPreferences("USER", MODE_PRIVATE);
        rv_comment = findViewById(R.id.rv_comment);
        et_message = findViewById(R.id.et_comment);
        send_comment = findViewById(R.id.send_comment);
        send =  findViewById(R.id.send);
        rv_comment.setLayoutManager(new LinearLayoutManager(this));
        rv_comment.setAdapter(new AdapterKomentar(getCOmments(), CommentAcitivity.this));
        rv_comment.setHasFixedSize(true);
    }

    private void sendMessage() {
        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String messages = et_message.getText().toString();
                et_message.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                if (!messages.isEmpty()){
                    send_comment.setEnabled(false);
                    Call<BaseResponse<ModelKomentar>> _comment = tourInterface.comment("Bearer " + getToken(), getTourId(), messages);
                    _comment.enqueue(new Callback<BaseResponse<ModelKomentar>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<ModelKomentar>> call, Response<BaseResponse<ModelKomentar>> response) {
                            if (response.isSuccessful()){
                                BaseResponse<ModelKomentar> body = response.body();
                                if (body != null && body.getStatus()){
                                    mKomen = body.getData();
                                    setMessage(mKomen.getApi_token(), String.valueOf(mKomen.getTour_id()), String.valueOf(mKomen.getMessages()));
                                    onResume();
                                    System.err.println("mbuh "+ response.body().getMessage().toString());
                                    Toast.makeText(CommentAcitivity.this, "Sukses", Toast.LENGTH_SHORT).show();
//                                    finish();
                                }else {
                                    Toast.makeText(CommentAcitivity.this, "failed", Toast.LENGTH_SHORT).show();
                                }
                            }else {
//                                System.err.println("mbuh2 " + response.body().getMessage().toString());
                                Toast.makeText(CommentAcitivity.this, "Sukses tapi salah", Toast.LENGTH_SHORT).show();
                            }

                            send_comment.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<ModelKomentar>> call, Throwable t) {
                            Toast.makeText(CommentAcitivity.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

}
