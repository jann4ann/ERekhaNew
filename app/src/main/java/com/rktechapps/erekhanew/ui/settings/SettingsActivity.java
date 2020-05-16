package com.rktechapps.erekhanew.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rktechapps.erekhanew.R;
import com.rktechapps.erekhanew.apputil.AppConfig;
import com.rktechapps.erekhanew.datasources.ApplicationDatabase;
import com.rktechapps.erekhanew.datasources.CountriesDAO;
import com.rktechapps.erekhanew.ui.particulardetails.ParticularDetailsActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener,SettingsView {

    private TextView txtTitle, txtCountryCount, txtStateCount, txtDistrictCount, txtAirportCount,
            txtReligionCount;
    private ImageView imgBack;
    private ImageButton btnCountrySync, btnStateSync, btnDistrictSync, btnAirportSync, btnReligionSync;
    private AppConfig appConfig;
    private SettingsPresenter mPresenter;
    private CountriesDAO mCountryDao;
    ProgressDialog mDialog;
    ApplicationDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        setListeners();
    }
    public void initViews(){
        appConfig = new AppConfig(this);
        txtTitle = findViewById(R.id.tvTitle);
        txtTitle.setText("Settings");
        imgBack = findViewById(R.id.imageBackArrow);
        btnCountrySync = findViewById(R.id.btnCountrySync);
        txtCountryCount = findViewById(R.id.txtCountryCount);
        btnStateSync = findViewById(R.id.btnStateSync);
        txtStateCount = findViewById(R.id.txtStateCount);
        btnDistrictSync = findViewById(R.id.btnDistrictSync);
        txtDistrictCount = findViewById(R.id.txtDistrictCount);
        btnAirportSync = findViewById(R.id.btnSyncAirport);
        txtAirportCount = findViewById(R.id.txtAirportCount);
        btnReligionSync = findViewById(R.id.btnSyncReligion);
        txtReligionCount = findViewById(R.id.txtReligionCount);
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Please wait a moment");
        db = ApplicationDatabase.getInstance(this);
        mPresenter = new SettingsPresenter(this,db.countriesDAO(),db.statesDAO(),db.districtsDAO());
    }
    public void setListeners(){
        imgBack.setOnClickListener(this);
        btnCountrySync.setOnClickListener(this);
        txtCountryCount.setOnClickListener(this);
        btnStateSync.setOnClickListener(this);
        txtStateCount.setOnClickListener(this);
        btnDistrictSync.setOnClickListener(this);
        txtDistrictCount.setOnClickListener(this);
        btnAirportSync.setOnClickListener(this);
        //txtAirportCount.setOnClickListener(this);
        txtAirportCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getDistCount();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBackArrow:
                super.onBackPressed();
                break;
            case  R.id.btnCountrySync:
                fetchCountries();
                break;
            case R.id.txtCountryCount:
                if(txtCountryCount.getText() == "")
                    Toast.makeText(this,"Please sync countries to view details",Toast.LENGTH_LONG).show();
                else {
                    Intent intent = new Intent(SettingsActivity.this, ParticularDetailsActivity.class);
                    intent.putExtra(getString(R.string.particulars_flag),0);
                    startActivity(intent);
                }
                break;
            case  R.id.btnStateSync:
                //check id countries are synced
                if(txtCountryCount.getText() == "")
                    Toast.makeText(this,"Please sync countries first",Toast.LENGTH_LONG).show();
                else
                    fetchStates();
                break;

            case R.id.txtStateCount:
                if(txtCountryCount.getText() == "")
                    Toast.makeText(this,"Please sync countries first",Toast.LENGTH_LONG).show();
                else {
                    Intent i = new Intent(SettingsActivity.this, ParticularDetailsActivity.class);
                    i.putExtra(getString(R.string.particulars_flag), 1);
                    startActivity(i);
                }
                break;
            case R.id.btnDistrictSync:
                if(txtStateCount.getText() == "")
                    Toast.makeText(this,"Please sync states first",Toast.LENGTH_LONG).show();
                else
                    fetchDistricts();
                break;
            case R.id.txtDistrictCount:
                if(txtStateCount.getText() == "")
                    Toast.makeText(this,"Please sync states first",Toast.LENGTH_LONG).show();
                else {
                    Intent i = new Intent(SettingsActivity.this, ParticularDetailsActivity.class);
                    i.putExtra(getString(R.string.particulars_flag), 2);
                    startActivity(i);
                }
                break;
            case R.id.btnSyncAirport:
                fetchAirports();
                break;
            case R.id.btnSyncReligion:
                fetchReligions();
                break;
        }

    }

    private void fetchReligions() {

        if(appConfig.isNetworkAvailable()) {
            mDialog.show();
            mPresenter.callGetReligionsApi();
        }
        else
            Toast.makeText(SettingsActivity.this,getString(R.string.no_network),Toast.LENGTH_LONG).show();
    }

    private void fetchAirports() {

        if(appConfig.isNetworkAvailable()) {
            mDialog.show();
            mPresenter.callGetAirportsApi();
        }
        else
            Toast.makeText(SettingsActivity.this,getString(R.string.no_network),Toast.LENGTH_LONG).show();

    }

    private void fetchDistricts() {
        if(appConfig.isNetworkAvailable()) {
            mDialog.show();
            mPresenter.callGetDistrictCountApi();
        }
        else
            Toast.makeText(SettingsActivity.this,getString(R.string.no_network),Toast.LENGTH_LONG).show();

    }

    private void fetchStates() {

        if(appConfig.isNetworkAvailable()) {
            mDialog.show();
            mPresenter.callGetStateCountApi();
        }
        else
            Toast.makeText(SettingsActivity.this,getString(R.string.no_network),Toast.LENGTH_LONG).show();

    }

    public void fetchCountries(){
        if(appConfig.isNetworkAvailable()) {
            String md5info = getString(R.string.md5_info);
            mPresenter.callFetchCountriesApi(md5info);
        }
    }

    @Override
    public void onCountryInsertSuccess() {
        txtCountryCount.setEnabled(true);
    }

    @Override
    public void onCountryInsertFail() {
        Toast.makeText(this,"Countries insert failed",Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideProgressBar() {
        mDialog.dismiss();
    }

    @Override
    public void setStateCount(int count) {
        txtStateCount.setText(String.valueOf(count));
        String md5info = getString(R.string.md5_info);
        mPresenter.callGetStatesApi(md5info);
    }

    @Override
    public void onFetchCountFail() {
        Toast.makeText(this,getString(R.string.default_error),Toast.LENGTH_LONG).show();
    }

    @Override
    public void setCountryCount(int size) {
        txtCountryCount.setText(String.valueOf(size));
    }

    @Override
    public void onFetchCountriesFail() {
        Toast.makeText(this,getString(R.string.default_error),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatesInsertSuccess(Integer count) {
        Toast.makeText(SettingsActivity.this,count+" states inserted",Toast.LENGTH_LONG).show();
        txtStateCount.setEnabled(true);
    }

    @Override
    public void setDistrictCount(int count) {
        txtDistrictCount.setText(String.valueOf(count));
        String md5info = getString(R.string.md5_info);
        mPresenter.callGetDistrictsApi(md5info);

    }

    @Override
    public void onDistrictInsertFail(String message) {
        Toast.makeText(this,getString(R.string.default_error)+message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDistrictInsertSuccess(Integer count) {
        txtDistrictCount.setEnabled(true);
        Toast.makeText(this,count+" Districts saved",Toast.LENGTH_LONG).show();
    }

}
