package com.rktechapps.erekhanew.datasources;

import androidx.room.Database;





    @Database(entities = {},
            version = 1)
    public abstract class ApplicationDatabase extends RoomDatabase {

        private static ApplicationDatabase instance;

        public abstract MeasurementsDAO measurementDAO();
        public abstract ReadingsDAO readingsDAO();
        public abstract AppointmentsDAO appointmentsDAO();
        public  abstract MedicineTimesDAO medicineTimesDAO();
        public abstract MedicineDetailsDAO medicineDetailsDAO();
        public abstract ReminderDetailsDAO reminderDetailsDAO();

        public synchronized static ApplicationDatabase getInstance(Context context) {
            if (instance == null) {
                instance = buildDatabase(context);
            }
            return instance;
        }

        private static ApplicationDatabase buildDatabase(final Context context) {
            return Room.databaseBuilder(context,
                    ApplicationDatabase.class,
                    "myNewDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    getInstance(context).measurementDAO().insertAll(Measurements.populateData());
                                }
                            });
                        }
                    })
                    .build();
        }
    }


