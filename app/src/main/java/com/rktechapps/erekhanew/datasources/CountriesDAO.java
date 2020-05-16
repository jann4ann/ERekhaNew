package com.rktechapps.erekhanew.datasources;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountriesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCountry(Countries country);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void pushCountries(List<Countries> countries_list);

    @Query("SELECT * FROM Countries")
    public LiveData<List<Countries>> getCountries();

    @Query("DELETE FROM Countries")
    public void deleteAllCountries();

    @Query("SELECT count(*) FROM Countries")
    public int getCountryCount();

    @Query("SELECT * FROM Countries WHERE cy_name LIKE :searchKey OR cy_name = :searchKey")
    List<Countries> search(String searchKey);

    @Query("SELECT cy_name FROM Countries WHERE cy_id = :id")
    String findCountry(Integer id);

    @Query("SELECT * FROM Countries WHERE cy_name = :string")
    List<Countries> searchByName(String string);
}
