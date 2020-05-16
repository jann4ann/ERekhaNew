package com.rktechapps.erekhanew.retrofit;

import com.rktechapps.erekhanew.datasources.Countries;
import com.rktechapps.erekhanew.models.md5RequestBody;
import com.rktechapps.erekhanew.models.CountsResponseModel;
import com.rktechapps.erekhanew.models.DashboardRequestBody;
import com.rktechapps.erekhanew.models.DashboardResponse;
import com.rktechapps.erekhanew.models.DistrictsResponseModel;
import com.rktechapps.erekhanew.models.FetchPoliceStationRequestBody;
import com.rktechapps.erekhanew.models.FetchPoliceStationResponse;
import com.rktechapps.erekhanew.models.HelpDeskRequestBody;
import com.rktechapps.erekhanew.models.HelpDeskResponse;
import com.rktechapps.erekhanew.models.LoginPoliceBody;
import com.rktechapps.erekhanew.models.LoginPoliceResponse;
import com.rktechapps.erekhanew.models.ResendOtpRequestBody;
import com.rktechapps.erekhanew.models.ResendOtpResponse;
import com.rktechapps.erekhanew.models.StatesRequestBody;
import com.rktechapps.erekhanew.models.StatesResponseModel;
import com.rktechapps.erekhanew.models.ValidateOtpRequestBody;
import com.rktechapps.erekhanew.models.ValidateOtpResponse;
import com.rktechapps.erekhanew.models.RegisterPoliceRequestBody;
import com.rktechapps.erekhanew.models.RegisterPoliceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("Register_Policemen")
    Call<RegisterPoliceResponse> registerPoliceMen(@Body RegisterPoliceRequestBody body);

    @POST("fetch_Police_station_")
    Call<FetchPoliceStationResponse> fetchPoliceStationNames(@Body FetchPoliceStationRequestBody policeStationRequestBody);

    @POST("Verify_otp_Reg")
    Call<ValidateOtpResponse> validateOtp(@Body ValidateOtpRequestBody otpRequestBody);

    @POST("Resend_OTP_Reg")
    Call<ResendOtpResponse> resendOTP(@Body ResendOtpRequestBody resendRequestBody);

    @POST("Policeman_Login")
    Call<LoginPoliceResponse> loginPoliceMan(@Body LoginPoliceBody loginPoliceBody);

    @POST("PoliceDashBoard")
    Call<DashboardResponse> getDashboardData(@Body DashboardRequestBody requestBody);

    @POST("HelpDesk")
    Call<HelpDeskResponse> fetchHelpNumber(@Body HelpDeskRequestBody requestBody);

    @POST("fetch_country_")
    Call<List<Countries>> fetchCountries(@Body md5RequestBody md5RequestBody);

    @POST("fetch_State_counts")
    Call<CountsResponseModel> fetchStateCounts();

    @POST("fetch_State_")
    Call<StatesResponseModel> fetchStates(@Body StatesRequestBody stateRequestBody);

    @POST("fetch_District_counts")
    Call<CountsResponseModel> fetchDistrictCounts();

    /*@POST("fetch_District_")
    Call<DistrictsResponseModel> fetchDistricts(@Body StatesRequestBody requestBody);*/

    @POST("fetch_District_")
    Call<DistrictsResponseModel> fetchDistricts(@Query("md5") String md5 , @Query("page") Integer page);

}
