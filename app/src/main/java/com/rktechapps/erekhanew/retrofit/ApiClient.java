package com.rktechapps.erekhanew.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public static final String BASE_URL = "http://e-rekha.in/SmartKeralaWebService/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()).build();
        }
        return retrofit;
    }



    /*private static Retrofit retrofit;
        private static final String BASE_URL = "http://e-rekha.in/SmartKeralaWebService/";

        public static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build()).build();
            }
            return retrofit;
        }*/

}
