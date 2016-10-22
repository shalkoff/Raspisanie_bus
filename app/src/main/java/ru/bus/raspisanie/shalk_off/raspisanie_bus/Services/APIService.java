package ru.bus.raspisanie.shalk_off.raspisanie_bus.Services;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.JSONRequest.JSONAddToDB;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.JSONRequest.JSONAvtobuses;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.JSONRequest.JSONVersion;

/**
 * Created by shalk_off on 07.10.2016.
 */

public interface APIService {

   // @POST("android/get_all_products.php")
  //  Call<JSONAvtobuses> loadRepo();

    @FormUrlEncoded
    @POST("android/getRaspisanie.php")
    Call<JSONAvtobuses> getAllInfo(@Field("info") String param);

    @FormUrlEncoded
    @POST("android/getRaspisanie.php")
    Call<JSONVersion> getVers(@Field("info") String param);

    @FormUrlEncoded
    @POST("android/testToken.php")
    Call<JSONAddToDB> getData(@Field("id") String param);

    @FormUrlEncoded
    @POST("android/testUser.php")
    Call<String> setUser(@Field("nameDivece") String nameDivece,
                         @Field("tokenKlient") String tokenKlient,
                         @Field("androidId") String androidId,
                         @Field("md5TokenKlient") String md5TokenKlient);
}
