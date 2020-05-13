package com.rktechapps.erekhanew.retrofit;

import com.rktechapps.erekhanew.models.DashboardRequestBody;
import com.rktechapps.erekhanew.models.DashboardResponse;
import com.rktechapps.erekhanew.models.FetchPoliceStationRequestBody;
import com.rktechapps.erekhanew.models.FetchPoliceStationResponse;
import com.rktechapps.erekhanew.models.LoginPoliceBody;
import com.rktechapps.erekhanew.models.LoginPoliceResponse;
import com.rktechapps.erekhanew.models.ResendOtpRequestBody;
import com.rktechapps.erekhanew.models.ResendOtpResponse;
import com.rktechapps.erekhanew.models.ValidateOtpRequestBody;
import com.rktechapps.erekhanew.models.ValidateOtpResponse;
import com.rktechapps.erekhanew.models.RegisterPoliceRequestBody;
import com.rktechapps.erekhanew.models.RegisterPoliceResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
}
