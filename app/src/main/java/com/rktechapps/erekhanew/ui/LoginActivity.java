package com.rktechapps.erekhanew.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rktechapps.erekhanew.R;
import com.rktechapps.erekhanew.apputil.AppConfig;
import com.rktechapps.erekhanew.models.HelpDeskRequestBody;
import com.rktechapps.erekhanew.models.HelpDeskResponse;
import com.rktechapps.erekhanew.models.LoginPoliceBody;
import com.rktechapps.erekhanew.models.LoginPoliceResponse;
import com.rktechapps.erekhanew.retrofit.ApiClient;
import com.rktechapps.erekhanew.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView txtRegister, txtTitle, txtUsername, txtPassword, txtHelpNum;
    private ImageView imgBack;
    private Button btnLogin;
    private AppConfig appConfig;
    private RelativeLayout helDeskLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        setListeners();
        if (appConfig.isNetworkAvailable()) {
            loadHelpNumber();
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.no_network), Toast.LENGTH_LONG).show();
        }
    }

    private void loadHelpNumber() {
        String md5info = getString(R.string.md5_info);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait a moment...");
        progressDialog.show();
        HelpDeskRequestBody requestBody = new HelpDeskRequestBody(md5info);

        Call<HelpDeskResponse> call = ApiClient.
                getRetrofitInstance().
                create(ApiInterface.class).
                fetchHelpNumber(requestBody);
        call.enqueue(new Callback<HelpDeskResponse>() {
            @Override
            public void onResponse(Call<HelpDeskResponse> call, Response<HelpDeskResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200 && response.body() != null) {
                    helDeskLay.setVisibility(View.VISIBLE);
                    HelpDeskResponse helpDeskResponse = response.body();
                    if (helpDeskResponse.getStatus() == 1) {
                        for (HelpDeskResponse.HelpDeskData data : helpDeskResponse.getData()) {
                            txtHelpNum.setText(data.getMobile());
                        }
                    } else
                        Toast.makeText(LoginActivity.this, getString(R.string.default_error), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.default_error), Toast.LENGTH_LONG).show();
                    helDeskLay.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<HelpDeskResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                helDeskLay.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initViews() {
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.textRegister);
        txtTitle = findViewById(R.id.tvTitle);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        imgBack = findViewById(R.id.imageBackArrow);
        imgBack.setVisibility(View.INVISIBLE);
        txtTitle.setText("Login");
        helDeskLay = findViewById(R.id.layHelpDeskDetails);
        txtHelpNum = findViewById(R.id.txtHelpNumber);
        appConfig = new AppConfig(this);
        fillMobileNumber();
    }

    private void setListeners() {
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterPoliceActivity.class));
                finish();
            }
        });
        txtHelpNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();


                if (mobileNumber.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_LONG).show();
                } else if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
                } else {
                    performUserLogin(mobileNumber, password);
                }

            }


        });
    }

    private void makePhoneCall() {
        String phone = txtHelpNum.getText().toString();
        String d = "tel:" + phone;
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse(d));
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(phoneIntent);
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Call faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
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
                        LoginPoliceResponse loginPoliceResponse = response.body();
                        if(loginPoliceResponse.getStatus()==1)
                        {
                            for(LoginPoliceResponse.LoginData data:loginPoliceResponse.getLoginData()){
                                appConfig.updateLoginStatus(true);
                                appConfig.updateLoginInfo(data.getUserid(),data.getName(),data.getMobile(),data.getPolicestation(),
                                        data.getDistrict());
                                Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(i);
                                finish();
                            }



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


    public static class EditSponsorActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_sponsor);
        }
    }
}
