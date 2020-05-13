package com.rktechapps.erekhanew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rktechapps.erekhanew.apputil.AppConfig;
import com.rktechapps.erekhanew.models.LoginPoliceBody;
import com.rktechapps.erekhanew.models.LoginPoliceResponse;
import com.rktechapps.erekhanew.retrofit.ApiClient;
import com.rktechapps.erekhanew.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView txtRegister, txtTitle, txtUsername, txtPassword;
    private ImageView imgBack;
    private Button btnLogin;
    private AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        setListeners();
    }
    private void initViews(){
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.textRegister);
        txtTitle = findViewById(R.id.tvTitle);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        imgBack = findViewById(R.id.imageBackArrow);
        imgBack.setVisibility(View.INVISIBLE);
        txtTitle.setText("Login");
        appConfig = new AppConfig(this);
        fillMobileNumber();
    }
    private void setListeners(){
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterPoliceActivity.class));
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if(appConfig.isNetworkAvailable()) {

                    if (mobileNumber.equals("")) {
                        Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_LONG).show();
                    }
                    else if(password.equals("")) {
                        Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    }
                    else {
                        performUserLogin(mobileNumber, password);
                    }

                }
                else
                {
                    Toast.makeText(LoginActivity.this,
                            R.string.no_network,Toast.LENGTH_LONG).show();
                    txtPassword.setText("");
                }
            }
        });
    }

    private void performUserLogin(String mobile,String password)
    {
        String md5Info  = getString(R.string.md5_info);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait a moment...");
        progressDialog.show();

        if(appConfig.isNetworkAvailable())
        {
            LoginPoliceBody loginPoliceBody = new LoginPoliceBody(mobile,password,md5Info);

            Call<LoginPoliceResponse> call = ApiClient.
                    getRetrofitInstance().
                    create(ApiInterface.class).
                    loginPoliceMan(loginPoliceBody);

            call.enqueue(new Callback<LoginPoliceResponse>() {
                @Override
                public void onResponse(Call<LoginPoliceResponse> call, Response<LoginPoliceResponse> response)
                {
                    progressDialog.dismiss();

                    if(response.code()==200 && response.body()!=null)
                    {
                        System.out.println("Login Response ==== "+response.body().getStatus());
                        if(response.body().getStatus()==1)
                        {
                            appConfig.updateLoginStatus(true);
                            startActivity(new Intent(LoginActivity.this, SponsorHomeActivity.class));
                            finish();
                        }
                        else if(response.body().getStatus() == 0){
                            Toast.makeText(LoginActivity.this,
                                    "Invalid username or password",
                                    Toast.LENGTH_LONG).show();
                            txtPassword.setText("");

                        }

                        else
                        {
                            Toast.makeText(LoginActivity.this,
                                    getString(R.string.default_error),
                                    Toast.LENGTH_LONG).show();
                            txtPassword.setText("");
                        }
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,
                                getString(R.string.default_error),
                                Toast.LENGTH_LONG).show();
                        txtPassword.setText("");
                    }

                }

                @Override
                public void onFailure(Call<LoginPoliceResponse> call, Throwable t)
                {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,
                            getString(R.string.server_error),
                            Toast.LENGTH_LONG).show();
                    txtPassword.setText("");
                }
            });


        }
        else
        {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this,
                    getString(R.string.no_network),
                    Toast.LENGTH_LONG).show();
            txtPassword.setText("");
        }

    }

    private void fillMobileNumber()
    {
        String mobile_number = appConfig.getUserMobileNumber();
        if(!mobile_number.equals("0"))
            txtUsername.setText(mobile_number.trim());
    }


}
