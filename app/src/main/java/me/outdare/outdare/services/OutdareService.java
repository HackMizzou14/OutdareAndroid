package me.outdare.outdare.services;

import java.util.List;

import me.outdare.outdare.dare.Dare;
import me.outdare.outdare.dares.Submission;
import me.outdare.outdare.login.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface OutdareService {
    @FormUrlEncoded
    @POST("/login")
    void login(@Field("user") String user,
               Callback<User> callback);

    @FormUrlEncoded
    @POST("/createUser")
    void createUser(@Field("user") String user,
                    @Field("email") String email,
                    @Field("phone") String phone,
                    Callback<User> callback);

    @FormUrlEncoded
    @POST("/dares/create")
    void createDare(@Field("title") String title,
                    @Field("details") String details,
                    @Field("user_id") String userId,
                    @Field("lat") double lat,
                    @Field("lon") double lon,
                    Callback<Dare> callback);

    @GET("/dares/")
    void getDares(@Query("lat") double lat,
                  @Query("lon") double lon,
                  Callback<List<Dare>> callback);

    @GET("/dares/{id}/submissions/")
    void getSubmissions(@Path("id") String dareId,
                        Callback<List<Submission>> callback);

}
