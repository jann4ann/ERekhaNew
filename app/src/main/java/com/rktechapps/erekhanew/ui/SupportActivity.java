package com.rktechapps.erekhanew.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rktechapps.erekhanew.R;
import com.rktechapps.erekhanew.adapters.SupportAdapter;
import com.rktechapps.erekhanew.apputil.AppConfig;
import com.rktechapps.erekhanew.models.HelpDeskRequestBody;
import com.rktechapps.erekhanew.models.HelpDeskResponse;
import com.rktechapps.erekhanew.retrofit.ApiClient;
import com.rktechapps.erekhanew.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends AppCompatActivity implements SupportAdapter.onContactClickedListener {

    private LinearLayoutManager layoutManager;
    private RecyclerView supportRecyclerView;
    private SupportAdapter mAdapter;
    private TextView txtTitle;
    private ImageView imgBack;
    private List<HelpDeskResponse.HelpDeskData> contactList = new ArrayList<>();
    private AppConfig appConfig;
    private String makeCallNumber = "";
    final int CALL_PHONE_CODE = 1000, OPEN_SETTINGS = 2000;
    private TextView lblSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        initViews();
        setListeners();
    }
    private void initViews(){
        lblSupport = findViewById(R.id.lblSupport);
        supportRecyclerView = findViewById(R.id.supportRecyclerView);
        txtTitle = findViewById(R.id.tvTitle);
        txtTitle.setText("Support");
        imgBack = findViewById(R.id.imageBackArrow);
        appConfig = new AppConfig(this);
    }
    private void setListeners(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initRecyclerView();
        if(appConfig.isNetworkAvailable()) {
            loadHelpNumber();
            lblSupport.setText(getString(R.string.support_desc));
        }
        else {
            lblSupport.setText(getString(R.string.no_network));
            Toast.makeText(SupportActivity.this, getString(R.string.no_network), Toast.LENGTH_LONG).show();
        }
    }
    private void initRecyclerView() {

        layoutManager = new LinearLayoutManager(this);
        mAdapter = new SupportAdapter(this,contactList);
        mAdapter.setOnContactClickedLister(this);
        supportRecyclerView.setAdapter(mAdapter);
        supportRecyclerView.setLayoutManager(layoutManager);

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
                    HelpDeskResponse helpDeskResponse = response.body();
                    if (helpDeskResponse.getStatus() == 1) {
                        contactList = helpDeskResponse.getData();
                        initRecyclerView();
                    } else
                        Toast.makeText(SupportActivity.this, getString(R.string.default_error), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SupportActivity.this, getString(R.string.default_error), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<HelpDeskResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SupportActivity.this, getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onHelpNumberClicked(String number) {
        makeCallNumber = number;
        mCheckPermission();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void mCheckPermission(){

        if(appConfig.shouldAskPermission(this, Manifest.permission.CALL_PHONE)){
            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                if(appConfig.isFirstTimeAskingPermission("call_phone")){
                    appConfig.firstTimeAskingPermission("call_phone",false);
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},CALL_PHONE_CODE);
                }else
                    showSettingsDialog();
            }else
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},CALL_PHONE_CODE);
        }else
            makePhoneCall();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CALL_PHONE_CODE){
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
        }
    }
    private void showSettingsDialog(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(R.string.allow_call_permission)
                .setCancelable(false)
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.fromParts("package", SupportActivity.this.getPackageName(), null);
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,uri);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(intent,OPEN_SETTINGS);
                        dialog.cancel();
                    }
                }).setPositiveButton("Not Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 dialog.cancel();
            }
        });
        AlertDialog alert = dialogBuilder.create();
        alert.setTitle("Manually allow permissions");
        alert.show();

    }

    @SuppressLint("MissingPermission")
    public void makePhoneCall(){
        String d = "tel:" + makeCallNumber ;
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse(d));
        try {
            startActivity(phoneIntent);
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Call failed. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

}
