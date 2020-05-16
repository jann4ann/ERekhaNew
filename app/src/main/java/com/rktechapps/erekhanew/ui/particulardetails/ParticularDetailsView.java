package com.rktechapps.erekhanew.ui.particulardetails;

import com.rktechapps.erekhanew.models.ParticularAdapterModel;

import java.util.List;

public interface ParticularDetailsView {

    void onSearchSuccess(List<ParticularAdapterModel> result);

    void onCountryFound(String country);

    void onExactSearchSuccess(List<ParticularAdapterModel> searchResultModel);

    void noMatchesFound();
}
