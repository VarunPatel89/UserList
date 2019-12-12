package com.app.userlist.network;

import com.app.userlist.BuildConfig;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient
{

    private static Retrofit retrofit = null;

    public static RestApiMethods getRestApiMethods()
    {
        return createRetrofit().create(RestApiMethods.class);
    }

    private static Retrofit createRetrofit()
    {
        if (retrofit == null)
        {
            OkHttpClient.Builder httpClient = getBuilder();
            httpClient.protocols(Arrays.asList(Protocol.HTTP_1_1));
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    @NonNull
    private static OkHttpClient.Builder getBuilder()
    {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        if (BuildConfig.IS_DEBUG)
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout((long) 60 * 3, TimeUnit.SECONDS)
                .readTimeout((long) 60 * 3, TimeUnit.SECONDS)
                .writeTimeout((long) 60 * 3, TimeUnit.SECONDS);
        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        return httpClient;
    }


}