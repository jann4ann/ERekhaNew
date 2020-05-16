package com.rktechapps.erekhanew.ui.settings;

import android.os.AsyncTask;
import android.util.Log;

import com.rktechapps.erekhanew.datasources.Countries;
import com.rktechapps.erekhanew.datasources.CountriesDAO;
import com.rktechapps.erekhanew.datasources.Districts;
import com.rktechapps.erekhanew.datasources.DistrictsDAO;
import com.rktechapps.erekhanew.datasources.States;
import com.rktechapps.erekhanew.datasources.StatesDAO;
import com.rktechapps.erekhanew.models.md5RequestBody;
import com.rktechapps.erekhanew.models.CountsResponseModel;
import com.rktechapps.erekhanew.models.DistrictsResponseModel;
import com.rktechapps.erekhanew.models.StatesRequestBody;
import com.rktechapps.erekhanew.models.StatesResponseModel;
import com.rktechapps.erekhanew.retrofit.ApiClient;
import com.rktechapps.erekhanew.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsPresenter {

    private SettingsView mSettingsView;
    private CountriesDAO mCountriesDao;
    private StatesDAO mStatesDao;
    private DistrictsDAO mDistrictsDao;
    private Integer totalStateReccursion = 0, totalDistrictReccursion = 0;
    private Integer stateCounts = 0, districtCounts = 0;
    private Integer statePage = 0, districtPage = 0;

    public SettingsPresenter(SettingsView mSettingsView, CountriesDAO mCountriesDao, StatesDAO mStatesDAO,
                             DistrictsDAO mDistrictsDao) {
        this.mSettingsView = mSettingsView;
        this.mCountriesDao = mCountriesDao;
        this.mStatesDao = mStatesDAO;
        this.mDistrictsDao = mDistrictsDao;
    }
    //*****************************************************************************************************************************
    //method to return district counts from api

    public void callGetDistrictCountApi() {
        Call<CountsResponseModel> call = ApiClient.getRetrofitInstance()
                .create(ApiInterface.class)
                .fetchDistrictCounts();
        call.enqueue(new Callback<CountsResponseModel>() {
            @Override
            public void onResponse(Call<CountsResponseModel> call, Response<CountsResponseModel> response) {
                mSettingsView.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    CountsResponseModel countsResponse = response.body();
                    districtCounts = countsResponse.getCount();
                    totalDistrictReccursion = (int) Math.ceil(districtCounts / 250);
                    districtPage = 0;
                    mSettingsView.setDistrictCount(countsResponse.getCount());

                } else {
                    mSettingsView.onFetchCountFail();
                }
            }

            @Override
            public void onFailure(Call<CountsResponseModel> call, Throwable t) {
                mSettingsView.hideProgressBar();
                mSettingsView.onFetchCountFail();
            }
        });
    }
    //****************************************************************************************************************
    //method to get districts and insert each

    /*public void callGetDistrictsApi(final String md5info) {
        int count =0;
        final StatesRequestBody requestBody = new StatesRequestBody(md5info,districtPage);
        Call<DistrictsResponseModel> call = ApiClient.getRetrofitInstance().
                create(ApiInterface.class).fetchDistricts(requestBody);
        call.enqueue(new Callback<DistrictsResponseModel>() {
            @Override
            public void onResponse(Call<DistrictsResponseModel> call, Response<DistrictsResponseModel> response) {

                mSettingsView.hideProgressBar();
                if(response.code() == 200 && response.body() != null){
                    List<Districts> fetchedDistricts = response.body().getDistricts_list();
                    //insertDistricts(fetchedDistricts);
                    for(Districts district :fetchedDistricts){
                        insertDistrict(district);
                    }
                    districtPage++;
                   if(districtPage >= totalDistrictReccursion){
                        //mSettingsView.onDistrictInsertSuccess(count);
                        new getDistrictRowsCountAsyncTask(mDistrictsDao).execute();
                    }else{
                        callGetDistrictsApi(md5info);
                    }

                }else {
                    //Toast.makeText(SettingsActivity.this,getString(R.string.default_error),Toast.LENGTH_LONG).show();
                    districtPage++;
                    Log.i("Erekha exception", "erekha exception" + response.code());
                    if(districtPage >= totalDistrictReccursion){
                        //mSettingsView.onDistrictInsertSuccess(count);
                        new getDistrictRowsCountAsyncTask(mDistrictsDao).execute();
                    }else{
                        callGetDistrictsApi(md5info);
                    }
                    mSettingsView.onFetchCountFail();
                }
            }

            @Override
            public void onFailure(Call<DistrictsResponseModel> call, Throwable t) {
                mSettingsView.hideProgressBar();
                mSettingsView.onFetchCountFail();
                Log.i("Erekha exception", "erekha exception" + t.toString());
                System.out.println("exception thrown ===== "+t.getMessage());
                districtPage++;
                if(districtPage >= totalDistrictReccursion){
                    //mSettingsView.onDistrictInsertSuccess(count);
                    new getDistrictRowsCountAsyncTask(mDistrictsDao).execute();
                }else{
                    callGetDistrictsApi(md5info);
                }
            }
        });
    }*/
    public void callGetDistrictsApi(final String md5info) {

        final StatesRequestBody requestBody = new StatesRequestBody(md5info, districtPage);
        Call<DistrictsResponseModel> call = ApiClient.getRetrofitInstance().
                create(ApiInterface.class).fetchDistricts(md5info, districtPage);

        call.enqueue(new Callback<DistrictsResponseModel>() {
            @Override
            public void onResponse(Call<DistrictsResponseModel> call, Response<DistrictsResponseModel> response) {

                mSettingsView.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    List<Districts> fetchedDistricts = response.body().getDistricts_list();
                    //insertDistricts(fetchedDistricts);

                    for (Districts district : fetchedDistricts) {
                        insertDistrict(district);
                    }

                    districtPage++;
                    if (districtPage >= totalDistrictReccursion) {
                        //mSettingsView.onDistrictInsertSuccess(count);
                      new getDistrictRowsCountAsyncTask(mDistrictsDao).execute();
                       // mSettingsView.onDistrictInsertFail("Completed");
                    } else {
                        callGetDistrictsApi(md5info);
                        Log.i("Erekha test", "erekha exception" );
                    }

                } else {
                    //Toast.makeText(SettingsActivity.this,getString(R.string.default_error),Toast.LENGTH_LONG).show();
                    districtPage++;
                    Log.i("Erekha exception", "erekha exception" + response.code());
                    if (districtPage >= totalDistrictReccursion) {
                        //mSettingsView.onDistrictInsertSuccess(count);
                        new getDistrictRowsCountAsyncTask(mDistrictsDao).execute();
                    } else {
                        callGetDistrictsApi(md5info);
                    }
                    mSettingsView.onFetchCountFail();
                }
            }

            @Override
            public void onFailure(Call<DistrictsResponseModel> call, Throwable t) {
                mSettingsView.hideProgressBar();
                mSettingsView.onFetchCountFail();
                Log.i("Erekha exception", "erekha exception" + t.toString());
                System.out.println("exception thrown ===== " + t.getMessage());
                districtPage++;
                if (districtPage >= totalDistrictReccursion) {
                    //mSettingsView.onDistrictInsertSuccess(count);
                    //new getDistrictRowsCountAsyncTask(mDistrictsDao).execute();
                } else {
                    callGetDistrictsApi(md5info);
                }
            }
        });
    }

    public void getDistCount(){
        new getDistrictRowsCountAsyncTask(mDistrictsDao).execute();
    }


    //***************************************************************************************************************

    //method to insert each district
    private void insertDistrict(Districts district) {
        try {
            //new insertDistrictAsyncTask(mDistrictsDao).execute(district);
            mDistrictsDao.insertDistrict(district);
        } catch (Exception e) {
            Log.i("EErekha exceptiondb DB ", "exception" + e.getMessage());
        }
    }

    public void callGetReligionsApi() {

    }

    /*private class insertDistrictAsyncTask extends AsyncTask<Districts, Void, Void> {

        private DistrictsDAO districtsDAO;

        public insertDistrictAsyncTask(DistrictsDAO districtsDAO) {
            this.districtsDAO = districtsDAO;
        }

        @Override
        protected Void doInBackground(final Districts... params) {
            mDistrictsDao.insertDistrict(params[0]);
            System.out.println("Inserted " + params[0].getDistrict_name());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //mSettingsView.onDistrictInsertSuccess(count);
        }
    }
*/
    //*********************************************************************************************************************************
    //method to get total district count inserted
    private class getDistrictRowsCountAsyncTask extends AsyncTask<Void, Void, Integer> {
        private DistrictsDAO districtsDAO;
        private int count;


        public getDistrictRowsCountAsyncTask(DistrictsDAO districtsDAO) {
            this.districtsDAO = districtsDAO;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return districtsDAO.getDistrictsCount();
        }

        @Override
        protected void onPostExecute(Integer count) {
            mSettingsView.onDistrictInsertSuccess(count);
        }
    }

    //***********************************************************************************************************************
    //method to get state count from web service
    public void callGetStateCountApi() {
        Call<CountsResponseModel> call = ApiClient.getRetrofitInstance()
                .create(ApiInterface.class)
                .fetchStateCounts();
        call.enqueue(new Callback<CountsResponseModel>() {
            @Override
            public void onResponse(Call<CountsResponseModel> call, Response<CountsResponseModel> response) {
                mSettingsView.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    CountsResponseModel countsResponse = response.body();
                    stateCounts = countsResponse.getCount();
                    totalStateReccursion = (int) (Math.ceil(stateCounts / 250));
                    statePage = 0;
                    mSettingsView.setStateCount(countsResponse.getCount());

                } else {
                    mSettingsView.onFetchCountFail();
                }
            }

            @Override
            public void onFailure(Call<CountsResponseModel> call, Throwable t) {
                mSettingsView.hideProgressBar();
                mSettingsView.onFetchCountFail();
            }
        });
    }

//***********************************************************************************************************************
    //method to get states

    public void callGetStatesApi(final String md5info) {

        final StatesRequestBody requestBody = new StatesRequestBody(md5info, statePage);
        Call<StatesResponseModel> call = ApiClient.getRetrofitInstance().
                create(ApiInterface.class).fetchStates(requestBody);
        call.enqueue(new Callback<StatesResponseModel>() {
            @Override
            public void onResponse(Call<StatesResponseModel> call, Response<StatesResponseModel> response) {

                mSettingsView.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    List<States> fetchedStates = response.body().getStatesList();
                    insertStates(fetchedStates);
                    statePage++;
                    if (statePage >= totalStateReccursion) {
                        //mSettingsView.onStatesInsertSuccess();
                        new getStateCountAsyncTask(mStatesDao).execute();
                    } else {
                        callGetStatesApi(md5info);
                    }
                } else {
                    //Toast.makeText(SettingsActivity.this,getString(R.string.default_error),Toast.LENGTH_LONG).show();
                    mSettingsView.onFetchCountFail();
                }
            }

            @Override
            public void onFailure(Call<StatesResponseModel> call, Throwable t) {
                mSettingsView.hideProgressBar();
                mSettingsView.onFetchCountriesFail();
            }
        });
    }

    //****************************************************************************************************************
    //method to insert states
    private void insertStates(List<States> fetchedStates) {
        try {
            new insertStatesAsyncTask(mStatesDao).execute(fetchedStates);
        } catch (Exception e) {
            mSettingsView.onCountryInsertFail();
        }
    }

    private class insertStatesAsyncTask extends AsyncTask<List<States>, Void, Void> {

        private StatesDAO statesDAO;

        public insertStatesAsyncTask(StatesDAO statesDAO) {
            this.statesDAO = statesDAO;
        }

        @Override
        protected Void doInBackground(final List<States>... params) {
            mStatesDao.pushStates(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //mSettingsView.onStatesInsertSuccess(count);
        }
    }

    //***********************************************************************************************************************
    //method to get state counts
    private class getStateCountAsyncTask extends AsyncTask<Void, Void, Integer> {
        private StatesDAO statesDAO;
        private int count;


        public getStateCountAsyncTask(StatesDAO statesDAO) {
            this.statesDAO = statesDAO;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return mStatesDao.getStateCount();
        }

        @Override
        protected void onPostExecute(Integer count) {
            mSettingsView.onStatesInsertSuccess(count);
        }
    }

    //*************************************************************************************************************************//
    //method to get countries
    public void callFetchCountriesApi(String md5info) {

        final md5RequestBody requestBody = new md5RequestBody(md5info);
        Call<List<Countries>> call = ApiClient.getRetrofitInstance().
                create(ApiInterface.class).fetchCountries(requestBody);
        call.enqueue(new Callback<List<Countries>>() {
            @Override
            public void onResponse(Call<List<Countries>> call, Response<List<Countries>> response) {

                mSettingsView.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    List<Countries> fetchedCountries = response.body();
                    mSettingsView.setCountryCount(fetchedCountries.size());
                    insertCountries(fetchedCountries);
                } else {
                    //Toast.makeText(SettingsActivity.this,getString(R.string.default_error),Toast.LENGTH_LONG).show();
                    mSettingsView.onFetchCountriesFail();
                }
            }

            @Override
            public void onFailure(Call<List<Countries>> call, Throwable t) {
                mSettingsView.hideProgressBar();
                mSettingsView.onFetchCountriesFail();
            }
        });
    }
//*********************************************************************************************************************
    //method to insert countries in local db

    public void insertCountries(List<Countries> countriesList) {
        try {
            new insertCountriesAsyncTask(mCountriesDao).execute(countriesList);
        } catch (Exception e) {
            mSettingsView.onCountryInsertFail();
        }
    }

    private class insertCountriesAsyncTask extends AsyncTask<List<Countries>, Void, Void> {

        private CountriesDAO mCountriesDao;

        insertCountriesAsyncTask(CountriesDAO mCountriesDao) {
            this.mCountriesDao = mCountriesDao;
        }

        @Override
        protected Void doInBackground(final List<Countries>... params) {
            mCountriesDao.pushCountries(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mSettingsView.onCountryInsertSuccess();
        }

    }
    //********************************************************************************************************************
    //async task to get inserted country count

    private class getCountryCountAsyncTask extends AsyncTask<Void, Void, Integer> {
        private CountriesDAO mCountriesDao;
        private int count;

        public getCountryCountAsyncTask(CountriesDAO mCountriesDao) {
            this.mCountriesDao = mCountriesDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return mCountriesDao.getCountryCount();
        }

        @Override
        protected void onPostExecute(Integer count) {
            mSettingsView.setCountryCount(count);
        }
    }

    //**********************************************************************************************************************
    public void callGetAirportsApi() {

    }





    /*private void insertDistricts(List<Districts> fetchedDistricts) {
        try {
            new insertDistrictsAsyncTask(mDistrictsDao).execute(fetchedDistricts);
        }catch (Exception e){
            mSettingsView.onDistrictInsertFail();
        }
    }
    private class insertDistrictsAsyncTask extends AsyncTask<List<Districts>, Void, Void> {

        private DistrictsDAO districtsDAO;

        public insertDistrictsAsyncTask(DistrictsDAO districtsDAO) {
            this.districtsDAO = districtsDAO;
        }

        @Override
        protected Void doInBackground(final List<Districts>... params) {
            mDistrictsDao.pushDistricts(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //mSettingsView.onDistrictInsertSuccess(count);
        }
    }

*/


}
