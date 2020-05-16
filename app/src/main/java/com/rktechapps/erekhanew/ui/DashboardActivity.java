package com.rktechapps.erekhanew.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rktechapps.erekhanew.R;
import com.rktechapps.erekhanew.adapters.DashboardAdapter;
import com.rktechapps.erekhanew.apputil.AppConfig;
import com.rktechapps.erekhanew.models.DashboardModel;
import com.rktechapps.erekhanew.models.DashboardRequestBody;
import com.rktechapps.erekhanew.models.DashboardResponse;
import com.rktechapps.erekhanew.retrofit.ApiClient;
import com.rktechapps.erekhanew.retrofit.ApiInterface;
import com.rktechapps.erekhanew.ui.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private GridLayoutManager layoutManager;
    private List<DashboardModel> mainDashboardList = new ArrayList();
    private RecyclerView dashboardRecycler;
    private AppConfig appConfig;
    private String userid="";
    private TextView txtWelcome, txtTitle, txtPoliceStation;
    private DashboardAdapter mAdapter;
    private ImageView imgBackArrow;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initViews();
        setListeners();
    }
    private void initViews(){
        appConfig = new AppConfig(this);
        imgBackArrow = findViewById(R.id.imageBackArrow);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        imgBackArrow.setVisibility(View.INVISIBLE);
        dashboardRecycler = findViewById(R.id.dashboardRecyclerView);
        txtWelcome = findViewById(R.id.txtWelcomeUser);
        txtPoliceStation = findViewById(R.id.txtPoliceStationDashboard);
        txtTitle = findViewById(R.id.tvTitle);
        txtTitle.setText("Dashboard");
        initDashboardDataList();
    }
    private void setListeners(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_support:
                        startActivity(new Intent(DashboardActivity.this,SupportActivity.class));
                        return true;

                    case R.id.nav_settings:
                        startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
                        return true;

                }
                return false;
            }
        });

    }
    private void initDashboardDataList(){
        mainDashboardList.add(new DashboardModel(0,"Guest Labourers"));
        mainDashboardList.add(new DashboardModel(0,"Sponsors"));
        mainDashboardList.add(new DashboardModel(0,"House Owners"));
        mainDashboardList.add(new DashboardModel(0,"Houses"));
        mainDashboardList.add(new DashboardModel(0,"Pending Geotags"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        initRecyclerView();
        userid = appConfig.getUserId();
        String md5Info  = getString(R.string.md5_info);
        txtWelcome.setText("Welcome "+ appConfig.getUserName());
        txtPoliceStation.setText("Your Policestation: "+appConfig.getNearestPoliceStation());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait a moment...");
        progressDialog.show();

        if(appConfig.isNetworkAvailable()){
            DashboardRequestBody requestBody = new DashboardRequestBody(userid,md5Info);
            Call<DashboardResponse> call = ApiClient.
                    getRetrofitInstance().
                    create(ApiInterface.class).
                    getDashboardData(requestBody);


            call.enqueue(new Callback<DashboardResponse>() {

                @Override
                public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                    progressDialog.dismiss();
                    String URL = call.request().url().toString();
                    System.out.println("Retrofit URL : " + URL);
                    Log.i("Retrofit URL", "Retrofit URL" + URL);
                    if (response.code() == 200)
                    {

                        if(response.body()!=null)
                        {
                            DashboardResponse dashboardResponse = response.body();
                            List<DashboardModel> dashboardList = new ArrayList<>();

                            if(dashboardResponse.getStatus()== 1)
                            {
                                bottomNavigationView.setVisibility(View.VISIBLE);
                                List<DashboardResponse.DashboardData> data = dashboardResponse.getData();
                                for(DashboardResponse.DashboardData item : data) {
                                    txtWelcome.setText("Welcome " + item.getStaff() + " !");
                                    dashboardList.add(new DashboardModel(item.getGuestLabour(), "Guest Labourers"));
                                    dashboardList.add(new DashboardModel(item.getSponsers(), "Sponsors"));
                                    dashboardList.add(new DashboardModel(item.getOwners(), "House Owners"));
                                    dashboardList.add(new DashboardModel(item.getProperty(), "Houses"));
                                    dashboardList.add(new DashboardModel(item.getPendingGeoTag(), "Pending Geotags"));
                                }
                                mainDashboardList = dashboardList;
                                initRecyclerView();
                            }

                            else {
                                progressDialog.dismiss();
                                Toast.makeText(DashboardActivity.this,
                                        "Something went wrong..Please try again later", Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(DashboardActivity.this,
                                "Something went wrong..Please try again later..",Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<DashboardResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(DashboardActivity.this,
                            "Unable to connect to server. Please try again later",Toast.LENGTH_LONG).show();

                }
            });

        }else{
            progressDialog.dismiss();
            Toast.makeText(DashboardActivity.this,getString(R.string.no_network),Toast.LENGTH_LONG).show();
        }
    }

    private void initRecyclerView() {

        layoutManager = new GridLayoutManager(this,2);
        mAdapter = new DashboardAdapter(this,mainDashboardList);
        dashboardRecycler.setAdapter(mAdapter);
        dashboardRecycler.setLayoutManager(layoutManager);


    }
}
