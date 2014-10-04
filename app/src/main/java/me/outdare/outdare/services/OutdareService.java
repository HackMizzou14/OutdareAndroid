package me.outdare.outdare.services;

import java.util.List;

import me.outdare.outdare.dare.Dare;
import me.outdare.outdare.login.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface OutdareService {
    @FormUrlEncoded
    @POST("/login.php")
    void login(@Field("user") String user,
               @Field("password") String password,
               Callback<User> callback);

    @FormUrlEncoded
    @POST("/createUser.php")
    void createUser(@Field("user") String user,
                    @Field("password") String password,
                    @Field("email") String email,
                    @Field("phone") String phone,
                    Callback<User> callback);

    @FormUrlEncoded
    @POST("/dares/create.php")
    void createDare(@Field("activity_dare") String dare,
                    @Field("challenger") String challenger,
                    @Field("latitude") double latitude,
                    @Field("longitude") double longitude,
                    Callback<Dare> callback);

    @GET("/dares/")
    void getDares(@Query("user") String user,
                  @Query("latitude") double latitude,
                  @Query("longitude") double longitude,
                  Callback<List<Dare>> callback);
}
