package me.outdare.outdare.services;

import java.util.List;

import me.outdare.outdare.login.Contributor;
import me.outdare.outdare.login.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface OutdareService {
    @FormUrlEncoded
    @POST("/login.php")
    void login(@Field("user") String user, @Field("password") String password, Callback<User> callback);

    @GET("/repos/{owner}/{repo}/contributors")
    void contributors(
            @Path("owner") String owner,
            @Path("repo") String repo,
            Callback<List<Contributor>> contribs
    );
}
