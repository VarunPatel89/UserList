package com.app.userlist.network;

import com.app.userlist.network.responsemodel.ClsGetListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiMethods
{

    @GET("users")
    Call<ClsGetListResponse> getList(@Query("offset") int pageNo, @Query("limit") int limit);

}
