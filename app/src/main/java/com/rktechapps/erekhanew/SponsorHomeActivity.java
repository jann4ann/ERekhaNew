package com.rktechapps.erekhanew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rktechapps.erekhanew.adapters.DashboardAdapter;
import com.rktechapps.erekhanew.apputil.AppConfig;
import com.rktechapps.erekhanew.models.DashboardModel;
import com.rktechapps.erekhanew.models.DashboardRequestBody;
import com.rktechapps.erekhanew.models.DashboardResponse;
import com.rktechapps.erekhanew.models.RegisterPoliceResponse;
import com.rktechapps.erekhanew.retrofit.ApiClient;
import com.rktechapps.erekhanew.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SponsorHomeActivity extends AppCompatActivity {

    private GridLayoutManager layoutManager;
    private List<DashboardModel> mainDashboardList = new ArrayList();
    private RecyclerView dashboardRecycler;
    private AppConfig appConfig;
    private String userid="";
    private TextView txtWelcome, txtTitle;
    private DashboardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_home);
        initViews();
    }
    private void initViews(){
        appConfig = new AppConfig(this);
        dashboardRecycler = findViewById(R.id.dashboardRecyclerView);
        txtWelcome = findViewById(R.id.txtWelcomeUser);
        txtTitle = findViewById(R.id.tvTitle);
        txtTitle.setText("Dashboard");
    }

    @Override
    protected void onStart() {
        super.onStart();
        initRecyclerView();
        userid = appConfig.getUserId();
        String md5Info  = getString(R.string.md5_info);
        txtWelcome.setText("Welcome "+appConfig.getUserName()+" !!");
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
                                List<DashboardResponse.DashboardData> data = dashboardResponse.getData();
                                for(DashboardResponse.DashboardData item : data) {
                                    txtWelcome.setText("Welcome " + item.getStaff() + " !");
                                    dashboardList.add(new DashboardModel(item.getGuestLabour(), "Guest Labourers"));
                                    dashboardList.add(new DashboardModel(item.getSponsers(), "Sponsors"));
                                    dashboardList.add(new DashboardModel(item.getOwners(), "House Owners"));
                                    dashboardList.add(new DashboardModel(item.getProperty(), "Accommodations"));
                                    dashboardList.add(new DashboardModel(item.getPendingGeoTag(), "Pending Geotags"));
                                }
                                mainDashboardList = dashboardList;
                                initRecyclerView();
                            }

                            else
                                Toast.makeText(SponsorHomeActivity.this,
                                        "Something went wrong..Please try again later",Toast.LENGTH_LONG).show();

                        }

                    }
                    else {
                        Toast.makeText(SponsorHomeActivity.this,
                                "Something went wrong..Please try again later..",Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<DashboardResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(SponsorHomeActivity.this,
                            "Unable to connect to server. Please try again later",Toast.LENGTH_LONG).show();

                }
            });

        }else{
            Toast.makeText(SponsorHomeActivity.this,getString(R.string.no_network),Toast.LENGTH_LONG).show();
        }
    }

    private void initRecyclerView() {

        layoutManager = new GridLayoutManager(this,2);
        mAdapter = new DashboardAdapter(this,mainDashboardList);
        dashboardRecycler.setAdapter(mAdapter);
        dashboardRecycler.setLayoutManager(layoutManager);


    }
}
