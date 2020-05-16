package com.rktechapps.erekhanew.ui.settings;

public interface SettingsView {


    void hideProgressBar();
    void onFetchCountFail();

    void setCountryCount(int size);
    void onCountryInsertSuccess();
    void onCountryInsertFail();

    void setStateCount(int count);





    void onFetchCountriesFail();

    void onStatesInsertSuccess(Integer count);


    void setDistrictCount(int count);

    void onDistrictInsertFail(String message);

    void onDistrictInsertSuccess(Integer count);
}
