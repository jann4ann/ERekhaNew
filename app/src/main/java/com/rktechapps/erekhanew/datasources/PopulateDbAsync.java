package com.rktechapps.erekhanew.datasources;

import android.os.AsyncTask;

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final CountriesDAO mCountriesDao;

    PopulateDbAsync(ApplicationDatabase db) {
        mCountriesDao = db.countriesDAO();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        mCountriesDao.deleteAllCountries();
        return null;
    }
}
