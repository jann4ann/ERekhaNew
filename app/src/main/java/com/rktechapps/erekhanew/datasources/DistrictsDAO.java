package com.rktechapps.erekhanew.datasources;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DistrictsDAO {
    @Insert
    public void insertDistrict(Districts district);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void pushDistricts(List<Districts> Districts_list);

    @Query("SELECT * FROM Districts")
    public LiveData<List<Districts>> getAllDistricts();

    @Query("DELETE FROM Districts")
    public void deleteAllDistricts();

    @Query("SELECT COUNT(*) FROM Districts")
    public int getDistrictsCount();

    @Query("SELECT * FROM Districts WHERE district_name LIKE :searchKey OR district_name = :searchKey ")
    List<Districts> search(String searchKey);


}
