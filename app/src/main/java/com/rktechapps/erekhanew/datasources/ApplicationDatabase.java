package com.rktechapps.erekhanew.datasources;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;


@Database(entities = {Countries.class,States.class,Districts.class},
            version = 3)
    public abstract class ApplicationDatabase extends RoomDatabase {

        private static ApplicationDatabase instance;
        public abstract CountriesDAO countriesDAO();
        public abstract StatesDAO statesDAO();
        public abstract DistrictsDAO districtsDAO();

        public synchronized static ApplicationDatabase getInstance(Context context) {
            if (instance == null) {
                instance = buildDatabase(context);
            }
            return instance;
        }

        private static ApplicationDatabase buildDatabase(final Context context) {
            return Room.databaseBuilder(context,
                    ApplicationDatabase.class,
                    "eRekhaSettingsDb")
                    .fallbackToDestructiveMigration()
                    /*.addCallback(new Callback() {
                        @Override
                        public void onOpen (@NonNull SupportSQLiteDatabase db){
                            super.onOpen(db);
                            new PopulateDbAsync(instance).execute();
                        }

                    })*/
                    .build();
        }



}








