package com.rktechapps.erekhanew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rktechapps.erekhanew.apputil.AppConfig;
import com.rktechapps.erekhanew.models.ResendOtpRequestBody;
import com.rktechapps.erekhanew.models.ResendOtpResponse;
import com.rktechapps.erekhanew.models.ValidateOtpRequestBody;
import com.rktechapps.erekhanew.models.ValidateOtpResponse;
import com.rktechapps.erekhanew.retrofit.ApiClient;
import com.rktechapps.erekhanew.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateOTPActivity extends AppCompatActivity implements View.OnClickListener {

    private String otp = "0";
    private TextView txtOtpDesc, txtTitle, txtOtp;
    private Button btnValidate, btnResendOtp;
    AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        appConfig = new AppConfig(this);
        initViews();
        setListeners();
    }
    public void initViews(){
        txtOtpDesc = findViewById(R.id.txtOtpDesc);
        txtOtpDesc.setText
                ("We have sent a 6 digit verification code to your mobile number +91 "+appConfig.getUserMobileNumber()+". Enter it below.");
        otp = appConfig.getUserOtp();
        txtTitle = findViewById(R.id.tvTitle);
        txtTitle.setText("Verify OTP");
        txtOtp = findViewById(R.id.txtOtp);
        btnValidate = findViewById(R.id.btnValidateOtp);
        btnResendOtp = findViewById(R.id.btnResendOtp);
    }
    public void setListeners(){
        btnValidate.setOnClickListener(this);
        btnResendOtp.setOnClickListener(this);
        txtOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>3) {
                    btnValidate.setEnabled(true);
                    btnValidate.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else {
                    btnValidate.setEnabled(false);
                    btnValidate.setTextColor(getResources().getColor(R.color.colorDarkAsh));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnValidateOtp:
                String userOtp = txtOtp.getText().toString().trim();
                System.out.println("otp saved in device === "+otp);
                if(userOtp.equals(otp))
                {
                    sendValidateOtpRequest();
                }
                else
                {
                    Toast.makeText(ValidateOTPActivity.this,"OTP not matching.",Toast.LENGTH_LONG).show();
                    txtOtp.setText("");
                }
               break;
            case R.id.btnResendOtp:
                requestResendOtp();
                break;
        }
    }


    private void sendValidateOtpRequest()
    {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.show();



        String mobile = appConfig.getUserMobileNumber();
        String pUid = appConfig.getUserId();
        String md5Info = getString(R.string.md5_info);

        ValidateOtpRequestBody otpRequestBody = new ValidateOtpRequestBody(pUid,mobile,md5Info);

        Call<ValidateOtpResponse> call = ApiClient.getRetrofitInstance().
                create(ApiInterface.class).
                validateOtp(otpRequestBody);

        //request body
        String URL = call.request().url().toString();
        System.out.println("Retrofit URL : " + URL);
        Log.i("Retrofit URL", "Retrofit URL" + URL);

        call.enqueue(new Callback<ValidateOtpResponse>() {
            @Override
            public void onResponse(Call<ValidateOtpResponse> call, Response<ValidateOtpResponse> response)
            {
                progressDialog.dismiss();
                if(response.code()==200&&response.body()!=null)
                {
                    System.out.println("Response from Verify Otp");
                    System.out.println(response.body().getUserid());
                    System.out.println(response.body().getUsername());
                    System.out.println(response.body().getMobile());
                    System.out.println(response.body().getPolicestation());
                    System.out.println(response.body().getPassword());

                    ValidateOtpResponse requestResponse = response.body();

                    appConfig.updateUserOtpSuccessInfo(requestResponse.getUserid(),requestResponse.getMobile(),
                            requestResponse.getUsername(),requestResponse.getPassword(),requestResponse.getName(),
                            requestResponse.getPolicestation());
                    appConfig.updateValidateOtpStatus();
                    Toast.makeText(ValidateOTPActivity.this,
                            "Verification is complete..Now you can login with the new credentials",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ValidateOTPActivity.this,LoginActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(ValidateOTPActivity.this,
                            "Something went wrong. Please try again later",
                            Toast.LENGTH_LONG).show();
                    txtOtp.setText("");
                }
            }

            @Override
            public void onFailure(Call<ValidateOtpResponse> call, Throwable t)
            {
                Toast.makeText(ValidateOTPActivity.this,
                        "Unable to reach server. Please try again later !!",
                        Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                txtOtp.setText("");
            }
        });

    }
    private void requestResendOtp()
    {
        txtOtp.setText("");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.show();

        String md5 = getString(R.string.md5_info);
        String mobile = appConfig.getUserMobileNumber();
        String pUid = appConfig.getUserId();

         ResendOtpRequestBody resendRequestBody = new ResendOtpRequestBody(pUid,mobile,md5);

        Call<ResendOtpResponse> call = ApiClient.
                getRetrofitInstance().create(ApiInterface.class).
                resendOTP(resendRequestBody);

        call.enqueue(new Callback<ResendOtpResponse>() {
            @Override
            public void onResponse(Call<ResendOtpResponse> call, Response<ResendOtpResponse> response)
            {
                progressDialog.dismiss();
                if(response.code()==200 && response.body()!=null)
                {
                    otp = response.body().getOTP();
                    Toast.makeText(ValidateOTPActivity.this,
                            "OTP successfully resent",
                            Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(ValidateOTPActivity.this,
                            R.string.default_error,
                            Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<ResendOtpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ValidateOTPActivity.this,
                        R.string.server_error,
                        Toast.LENGTH_LONG).show();
            }
        });


    }


}
