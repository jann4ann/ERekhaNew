package com.rktechapps.erekhanew.ui.particulardetails;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.rktechapps.erekhanew.datasources.Countries;
import com.rktechapps.erekhanew.datasources.CountriesDAO;
import com.rktechapps.erekhanew.datasources.States;
import com.rktechapps.erekhanew.datasources.StatesDAO;
import com.rktechapps.erekhanew.models.ParticularAdapterModel;

import java.util.ArrayList;
import java.util.List;

public class ParticularDetailsPresenter {

    private CountriesDAO countriesDAO;
    private ParticularDetailsView mView;
    private StatesDAO statesDAO;

    public ParticularDetailsPresenter(CountriesDAO countriesDAO, ParticularDetailsView mView) {
        this.countriesDAO = countriesDAO;
        this.mView = mView;
    }

    public ParticularDetailsPresenter(ParticularDetailsView mView, StatesDAO statesDAO) {
        this.mView = mView;
        this.statesDAO = statesDAO;
    }

    public ParticularDetailsPresenter(CountriesDAO countriesDAO, ParticularDetailsView mView, StatesDAO statesDAO) {
        this.countriesDAO = countriesDAO;
        this.mView = mView;
        this.statesDAO = statesDAO;
    }

    public LiveData<List<Countries>> fetchCountry(){
         return countriesDAO.getCountries();
    }
    public LiveData<List<States>> fetchStates(){
        return statesDAO.getAllStates();
    }

    public void searchCountry(String query) {
        try{
            new searchCountryAsyncTask(countriesDAO).execute(query);
        }catch (Exception e){

        }
    }

    public void searchStateByName(String query) {
    }

    public class searchCountryAsyncTask extends AsyncTask<String,Void,List<Countries>>{

        CountriesDAO countriesDAO;

        public searchCountryAsyncTask(CountriesDAO countriesDAO) {
            this.countriesDAO = countriesDAO;
        }

        @Override
        protected List<Countries> doInBackground(String... strings) {
            return countriesDAO.search(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Countries> searchResults) {
            if(searchResults.size() ==0)
                mView.noMatchesFound();
            else {
                List<ParticularAdapterModel> searchResultModel = new ArrayList<>();
                for (Countries country : searchResults) {
                    searchResultModel.add(new ParticularAdapterModel(country.getCy_id(), country.getCy_name()));
                }
                mView.onSearchSuccess(searchResultModel);
            }
        }
    }

    public void searchCountryByName(String query) {
        new searchCountryByNameAsyncTask(countriesDAO).execute(query);
    }
    public class searchCountryByNameAsyncTask extends AsyncTask<String,Void,List<Countries>>{

        CountriesDAO countriesDAO;

        public searchCountryByNameAsyncTask(CountriesDAO countriesDAO) {
            this.countriesDAO = countriesDAO;
        }

        @Override
        protected List<Countries> doInBackground(String... strings) {
            return countriesDAO.searchByName(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Countries> searchResults) {
            if(searchResults.size()==0)
                mView.noMatchesFound();
            else {
                List<ParticularAdapterModel> searchResultModel = new ArrayList<>();
                for (Countries country : searchResults) {
                    searchResultModel.add(new ParticularAdapterModel(country.getCy_id(), country.getCy_name()));
                }
                mView.onExactSearchSuccess(searchResultModel);
            }
        }
    }


    public void findCountry(Integer id) {
        new findCountryAsyncTask(countriesDAO).execute(id);
        System.out.println("++++++++++++++++++++++++++++++++++++++++"+id);
    }
    public class findCountryAsyncTask extends AsyncTask<Integer,Void,String>{

        CountriesDAO countriesDAO;

        public findCountryAsyncTask(CountriesDAO countriesDAO) {
            this.countriesDAO = countriesDAO;
        }

        @Override
        protected String doInBackground(Integer... integers) {
            return countriesDAO.findCountry(integers[0]);
        }

        @Override
        protected void onPostExecute(String country) {
            mView.onCountryFound(country);
        }
    }


    public void searchState(String query) {
        try{
            new searchStateAsyncTask(statesDAO).execute(query);
        }catch (Exception e){

        }
    }
    public class searchStateAsyncTask extends AsyncTask<String,Void,List<States>>{

        StatesDAO statesDAO;


        public searchStateAsyncTask(StatesDAO statesDAO) {
            this.statesDAO = statesDAO;
        }

        @Override
        protected List<States> doInBackground(String... strings) {
            return statesDAO.search(strings[0]);
        }

        @Override
        protected void onPostExecute(List<States> searchResults) {
            if(searchResults.size() ==0)
                mView.noMatchesFound();
            else {
                List<ParticularAdapterModel> searchResultModel = new ArrayList<>();
                for (States state : searchResults) {
                    searchResultModel.add(new ParticularAdapterModel(state.getSE_id(), state.getSE_name(), state.getCY_id()));
                }
                mView.onSearchSuccess(searchResultModel);
            }
        }
    }


}
