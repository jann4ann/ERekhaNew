package com.rktechapps.erekhanew.datasources;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ReligionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertReligion(Religion religion);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void pushReligions(List<Religion> religions_list);

    @Query("SELECT * FROM States")
    public LiveData<List<States>> getAllStates();

    @Query("DELETE FROM States")
    public void deleteAllStates();

    @Query("SELECT count(*) FROM States")
    public int getStateCount();

    @Query("SELECT * FROM States WHERE SE_name LIKE :searchKey OR SE_name = :searchKey ")
    List<States> search(String searchKey);
}
