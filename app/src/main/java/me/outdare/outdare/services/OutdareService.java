package me.outdare.outdare.services;

import me.outdare.outdare.dare.Dare;
import me.outdare.outdare.login.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

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
    void createDare(@Field("dare") String dare,
                    @Field("challenger") String challenger,
                    @Field("latitude") Double latitude,
                    @Field("longitude") Double longitude,
                    Callback<Dare> callback);
}
