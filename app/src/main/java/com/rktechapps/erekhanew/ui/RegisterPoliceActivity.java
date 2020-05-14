package com.rktechapps.erekhanew.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rktechapps.erekhanew.commonutils.DelayAutoCompleteTextView;
import com.rktechapps.erekhanew.R;
import com.rktechapps.erekhanew.apputil.AppConfig;
import com.rktechapps.erekhanew.models.FetchPoliceStationRequestBody;
import com.rktechapps.erekhanew.models.FetchPoliceStationResponse;
import com.rktechapps.erekhanew.models.RegisterPoliceRequestBody;
import com.rktechapps.erekhanew.models.RegisterPoliceResponse;
import com.rktechapps.erekhanew.retrofit.ApiClient;
import com.rktechapps.erekhanew.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPoliceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegisterPolice;
    private ImageView imgBack;
    private TextView txtName,txtPenNo, txtPhone, txtAddress, txtEmail, txtUsername, txtPassword,txtTitle;
    //private AutoCompleteTextView atvPoliceStation;
    private DelayAutoCompleteTextView atvPoliceStation;
    private CheckBox chkDeclare;
    private boolean isDeclared = false;
    ProgressBar mBar;
    private AppConfig appConfig;
    private List<FetchPoliceStationResponse.PoliceStation> policeStations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_police);
        appConfig = new AppConfig(this);
        initViews();
        setListeners();
        if(!appConfig.isNetworkAvailable())
            Toast.makeText(RegisterPoliceActivity.this,"No network connection. Please try again later",Toast.LENGTH_LONG).show();
    }
    public void initViews(){
        txtTitle = findViewById(R.id.tvTitle);
        imgBack = findViewById(R.id.imageBackArrow);
        txtTitle.setText("Register");
        btnRegisterPolice = findViewById(R.id.btnRegisterPolice);
        txtName = findViewById(R.id.txtPoliceName);
        txtPenNo = findViewById(R.id.txtPolicePenNo);
        txtPhone = findViewById(R.id.txtPolicePhone);
        txtAddress = findViewById(R.id.txtPoliceAddress);
        txtEmail = findViewById(R.id.txtPoliceEmail);
        atvPoliceStation = findViewById(R.id.atvPoliceStation);
        txtUsername = findViewById(R.id.txtPoliceUserName);
        txtPassword = findViewById(R.id.txtPolicePassword);
        chkDeclare = findViewById(R.id.chkDeclare);
        mBar = new ProgressBar(this);
    }
    public void setListeners(){
        btnRegisterPolice.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        chkDeclare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    isDeclared = true;
                else
                    isDeclared = false;
            }
        });
       atvPoliceStation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String query = charSequence.toString();
                if(query.length()>2)
                    if(appConfig.isNetworkAvailable())
                        fetchPoliceStations(query);
                    else
                        Toast.makeText(RegisterPoliceActivity.this,"No network connection",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequiredFields();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtPenNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequiredFields();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequiredFields();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                txtUsername.setText(s.toString());
            }
        });
        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequiredFields();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequiredFields();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        atvPoliceStation.setOnItemClickListener(onItemClickListener);
    }
    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    atvPoliceStation.setText(adapterView.getItemAtPosition(i).toString());
                }
            };
    public void checkRequiredFields(){
        if(!(txtName.getText().toString().trim().isEmpty()) &&
                !(txtPenNo.getText().toString().trim().isEmpty()) &&
                !(txtPhone.getText().toString().trim().isEmpty()) &&
                !(txtAddress.getText().toString().trim().isEmpty()) &&
                !(txtPassword.getText().toString().trim().isEmpty())) {
            btnRegisterPolice.setEnabled(true);
            btnRegisterPolice.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        else {
            btnRegisterPolice.setEnabled(false);
            btnRegisterPolice.setTextColor(getResources().getColor(R.color.colorDarkAsh));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegisterPolice:
                register();
                break;
            case R.id.imageBackArrow:
                startActivity(new Intent(RegisterPoliceActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void register(){
        String name = txtName.getText().toString().trim();
        String penNo = txtPenNo.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String address = txtAddress.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        Integer policeStationCode = Integer.parseInt(getPoliceStationCode(atvPoliceStation.getText().toString().trim()));
        if(phone.length() != 10){
            Toast.makeText(this,"Please enter a 10 digit phone number",Toast.LENGTH_LONG).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Please enter a valid email id",Toast.LENGTH_LONG).show();
            txtEmail.setText("");
            txtEmail.requestFocus();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(this,"Please enter a password with at least 6 characters",Toast.LENGTH_LONG).show();
            txtPassword.setText("");
            txtPassword.requestFocus();
            return;
        }
        if(policeStationCode == 0){
            Toast.makeText(this,"Please enter select a valid Police station",Toast.LENGTH_LONG).show();
            atvPoliceStation.setText("");
            atvPoliceStation.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait a moment...");
        progressDialog.show();
        if(appConfig.isNetworkAvailable()) {
            RegisterPoliceRequestBody.PoliceMen policeMen = new RegisterPoliceRequestBody.PoliceMen(name,phone, penNo, address, email, password,
                    policeStationCode,policeStationCode);
            List<RegisterPoliceRequestBody.PoliceMen> policeMenList = new ArrayList<>();
            policeMenList.add(policeMen);
            RegisterPoliceRequestBody registerPoliceMenRequestBody = new RegisterPoliceRequestBody(policeMenList);
            for(RegisterPoliceRequestBody.PoliceMen men:policeMenList)
                System.out.println(men.getName());

            Call<RegisterPoliceResponse> call = ApiClient.
                    getRetrofitInstance().
                    create(ApiInterface.class).
                    registerPoliceMen(registerPoliceMenRequestBody);


            call.enqueue(new Callback<RegisterPoliceResponse>() {

                @Override
                public void onResponse(Call<RegisterPoliceResponse> call, Response<RegisterPoliceResponse> response) {
                    progressDialog.dismiss();
                    String URL = call.request().url().toString();
                    System.out.println("Retrofit URL : " + URL);
                    Log.i("Retrofit URL", "Retrofit URL" + URL);
                    if (response.code() == 200)
                    {
                        System.out.println("inside policemen register success");
                        if(response.body()!=null)
                        {
                            RegisterPoliceResponse signUpResponse = response.body();
                            System.out.println("Sign up response status == "+response.body().getStatus());
                            if(signUpResponse.getStatus()== 1)
                            {
                                appConfig.updateUserInfo(txtPhone.getText().toString().trim(),
                                        Integer.toString(signUpResponse.getUser_id()),
                                        signUpResponse.getOtp(),txtName.getText().toString().trim());
                                Intent intent = new Intent(RegisterPoliceActivity.this, ValidateOTPActivity.class);
                                //intent.putExtra("otp_for_validation",signUpResponse.getOtp());
                                startActivity(intent);
                                finish();
                            }
                            else if(signUpResponse.getStatus()==2)
                            {
                                Toast.makeText(RegisterPoliceActivity.this,
                                        "This mobile number is already registered",Toast.LENGTH_LONG).show();
                                txtPassword.setText("");

                            }
                            else
                                Toast.makeText(RegisterPoliceActivity.this,
                                        "Something went wrong..Please try again later",Toast.LENGTH_LONG).show();
                                txtPassword.setText("");
                        }

                    }
                    else {
                        System.out.println("inside policemen register response ==="+response.code());
                        Toast.makeText(RegisterPoliceActivity.this,
                                "Something went wrong..Please try again later..",Toast.LENGTH_LONG).show();
                        txtPassword.setText("");
                    }

                }

                @Override
                public void onFailure(Call<RegisterPoliceResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterPoliceActivity.this,
                            "Unable to connect to server. Please try again later",Toast.LENGTH_LONG).show();
                    txtPassword.setText("");
                    mBar.setVisibility(View.INVISIBLE);
                }
            });
        }
        else
        {
            progressDialog.dismiss();
            Toast.makeText(RegisterPoliceActivity.this,"No network connection",Toast.LENGTH_LONG);
            mBar.setVisibility(View.INVISIBLE);
        }
    }

    private String getPoliceStationCode(String name)
    {
        String code  = "00";
        for(FetchPoliceStationResponse.PoliceStation policeStation : policeStations)
        {
            if(name.equals(policeStation.getPsName()))
            {
                code = policeStation.getPsId();
                break;
            }
        }

        return code;
    }
    private void fetchPoliceStations(String query)
    {
        atvPoliceStation.setLoadingIndicator((ProgressBar) findViewById(R.id.pb_loading_indicator));
        System.out.println("Inside fetch police station");
        String md5Info = getString(R.string.md5_info);
        FetchPoliceStationRequestBody policeStationRequestBody = new FetchPoliceStationRequestBody(md5Info,query);

        Call<FetchPoliceStationResponse> call = ApiClient.
                getRetrofitInstance().create(ApiInterface.class).
                fetchPoliceStationNames(policeStationRequestBody);

        call.enqueue(new Callback<FetchPoliceStationResponse>() {
            @Override
            public void onResponse(Call<FetchPoliceStationResponse> call, Response<FetchPoliceStationResponse> response)
            {
                System.out.println("inside get police station onResponse");
                if(response.code()==200&&response.body()!=null)
                {
                    System.out.println("response code === 200");
                    policeStations = new ArrayList<>();
                    /*if(policeStations.size()>0)
                        policeStations.clear();*/
                    policeStations= response.body().getPoliceStations();
                    List<String> pos = new ArrayList<>();
                    if(policeStations != null) {
                        for (FetchPoliceStationResponse.PoliceStation policeStation : policeStations) {
                            System.out.println(policeStation.getPsName());
                            pos.add(policeStation.getPsName());

                        }
                        atvPoliceStation = findViewById(R.id.atvPoliceStation);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RegisterPoliceActivity.this,
                                android.R.layout.simple_dropdown_item_1line, pos.toArray(new String[0]));
                        atvPoliceStation.setAdapter(arrayAdapter);
                        atvPoliceStation.setThreshold(1);
                    }
                }
                else{
                    Toast.makeText(RegisterPoliceActivity.this,"Something went wrong..Please try again later !!",Toast.LENGTH_LONG).show();
                    atvPoliceStation.setText("");
                }


            }

            @Override
            public void onFailure(Call<FetchPoliceStationResponse> call, Throwable t) {
                System.out.println("inside fetch police station on failure "+t.getMessage());
                Toast.makeText(RegisterPoliceActivity.this,"Unable to connect to the server..Please try again later !!",Toast.LENGTH_LONG).show();
                atvPoliceStation.setText("");
            }
        });
    }

}
