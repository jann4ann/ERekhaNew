package com.rktechapps.erekhanew.ui.particulardetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.Telephony;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rktechapps.erekhanew.R;
import com.rktechapps.erekhanew.adapters.ParticularDetailsAdapter;
import com.rktechapps.erekhanew.commonutils.DelayAutoCompleteTextView;
import com.rktechapps.erekhanew.datasources.ApplicationDatabase;
import com.rktechapps.erekhanew.datasources.Countries;
import com.rktechapps.erekhanew.models.ParticularAdapterModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class ParticularDetailsActivity extends AppCompatActivity implements View.OnClickListener,ParticularDetailsView {

    private TextView txtTitle, lblRecentSearches;
    private ImageView imgBack;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private ParticularDetailsAdapter mAdapter;
    private List<ParticularAdapterModel> list;
    private ApplicationDatabase db;
    private ParticularDetailsPresenter mPresenter;
    private DelayAutoCompleteTextView atvSearch;
    private Integer flag = -1;
    private List<String> searchResults = new ArrayList<>();
    private TextView txtSearchResult;
    private Boolean exact_search_flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_particular_details);
        initViews();
        setListeners();
    }

    private void initViews() {

        imgBack = findViewById(R.id.imageBackArrow);
        txtTitle = findViewById(R.id.tvTitle);
        list = new ArrayList<>();
        atvSearch = findViewById(R.id.atvSearchParticular);
        db = ApplicationDatabase.getInstance(this);

        lblRecentSearches = findViewById(R.id.lblRecentSearches);
        flag = getIntent().getIntExtra(getString(R.string.particulars_flag),-1);
        populateValues(flag);

        mRecycler = findViewById(R.id.particularDetailsRecyclerView);
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mAdapter = new ParticularDetailsAdapter(this,searchResults);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
       txtSearchResult = findViewById(R.id.txtSearchResult);



    }

    private void populateValues(Integer flag) {
        list.clear();
        switch (flag){
            case 0:
                //fetch countries
                txtTitle.setText("Countries");
                mPresenter = new ParticularDetailsPresenter(db.countriesDAO(),this);
                break;
            case 1:
                //fetch states
                txtTitle.setText("States");
                mPresenter = new ParticularDetailsPresenter(db.countriesDAO(),this,db.statesDAO());
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

   private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    atvSearch.setText(adapterView.getItemAtPosition(i).toString());
                    ParticularAdapterModel selected = (ParticularAdapterModel) adapterView.getItemAtPosition(i);
                    switch (flag){
                        case 0:
                            //countries
                            txtSearchResult.setText(selected.getName());
                            searchResults.add(selected.getName());
                            atvSearch.setText("");
                            break;
                        case 1:
                            //states
                            Integer c_id = selected.getParent_id();
                            mPresenter.findCountry(c_id);
                            break;
                    }
                    txtSearchResult.setText(adapterView.getItemAtPosition(i).toString());
                }
            };

    private void setListeners() {
        imgBack.setOnClickListener(this);
        atvSearch.setOnItemClickListener(onItemClickListener);
        atvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String query = charSequence.toString().trim();
                if(query.length()>1){
                    switch (flag){
                        case 0:
                            //search for countries
                            atvSearch.setLoadingIndicator((ProgressBar) findViewById(R.id.pb_loading_indicator));
                            mPresenter.searchCountry("%"+query+"%");
                            break;
                        case 1:
                            //search for states
                            atvSearch.setLoadingIndicator((ProgressBar) findViewById(R.id.pb_loading_indicator));
                            mPresenter.searchState("%"+query+"%");
                            break;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        atvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String query = v.getText().toString().trim();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    switch (flag) {
                        case 0:
                            //countries
                            mPresenter.searchCountryByName(query);
                            exact_search_flag = true;
                            break;
                        case 1:
                            //states
                            mPresenter.searchStateByName(query);
                            exact_search_flag = true;
                            break;
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imageBackArrow)
            super.onBackPressed();
    }

    @Override
    public void onSearchSuccess(List<ParticularAdapterModel> result) {
        atvSearch = findViewById(R.id.atvSearchParticular);
        ArrayAdapter<ParticularAdapterModel> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,
                result.toArray(new ParticularAdapterModel[0]));
        atvSearch.setAdapter(arrayAdapter);
        atvSearch.setThreshold(1);
    }

    @Override
    public void onCountryFound(String country) {
        String selectedState = atvSearch.getText().toString();
        txtSearchResult.setText(selectedState + ", "+country);
        searchResults.add(selectedState + ", "+country);
        atvSearch.setText("");
    }

    @Override
    public void onExactSearchSuccess(List<ParticularAdapterModel> result) {
        if(result != null) {
            if(result.size()>0) {
                switch(flag) {
                    case 0:
                        //countries
                        txtSearchResult.setText(result.get(0).getName());
                        searchResults.add(result.get(0).getName());
                        atvSearch.setText("");
                        break;
                    case 1:
                        //states
                        Integer c_id = result.get(0).getParent_id();
                        mPresenter.findCountry(c_id);
                        break;
                }
            }
        }
    }

    @Override
    public void noMatchesFound() {
        Toast.makeText(ParticularDetailsActivity.this,"No matches found",Toast.LENGTH_LONG).show();
    }


}
