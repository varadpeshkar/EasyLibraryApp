package com.easylibrary.android.api.network;

import android.content.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by rohan on 26/2/17.
 */

public class ELRetrofit {


    private static String BASE_URL;
    private static Context applicationContext;
    private static ELRetrofit instance;

    public static synchronized void setUp(Context context, String baseUrl) {
        applicationContext = context;
        BASE_URL = baseUrl;
    }

    private ELRetrofit() {

    }

    public static ELRetrofit getInstance() {
        if (instance == null) {
            instance = new ELRetrofit();
        }
        return instance;
    }

    /**
     * Method to create unauthenticated api service with root included while jackson parsing
     *
     * @param serviceClass - Service interface class
     * @param <S>
     * @return service instance to call API methods
     */
    public <S> S createServiceWithRoot(Class<S> serviceClass) {
        return createService(serviceClass, true, null, null);
    }

    /**
     * Method to create unauthenticated api service without root included while jackson parding
     *
     * @param serviceClass - Service interface class
     * @param <S>
     * @return service instance to call API methods
     */
    public <S> S createServiceWithoutRoot(Class<S> serviceClass) {
        return createService(serviceClass, false, null, null);
    }

    /**
     * Method to create authenticated api service with root included while jackson parding
     *
     * @param serviceClass - Service interface class
     * @param <S>
     * @return service instance to call API methods
     */
    public <S> S createServiceWithRoot(Class<S> serviceClass, String authToken, String email) {
        return createService(serviceClass, true, authToken, email);
    }


    /**
     * Method to create authenticated api service without root included while jackson parding
     *
     * @param serviceClass - Service interface class
     * @param <S>
     * @return service instance to call API methods
     */
    public <S> S createServiceWithoutRoot(Class<S> serviceClass, String authToken, String email) {
        return createService(serviceClass, false, authToken, email);
    }

    /**
     * Generic method to create service based on requirement.
     * All the retrofit configuration is done in this method,
     * also jackson and okhttp config is done
     *
     * @param serviceClass - service class
     * @param includeRoot  - boolean telling about root inclusion
     * @param authToken    - authToken (null in case of unauthenticated requests)
     * @param <S>
     * @return service instance to call API methods
     */
    private <S> S createService(Class<S> serviceClass,
                                boolean includeRoot,
                                final String authToken,
                                final String email) {
        ObjectMapper objectMapper = new ObjectMapper();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor(getAcceptHeaderInterceptor());


        if (authToken != null && email != null) {
            okHttpClientBuilder.addNetworkInterceptor(getAuthorizationHeaderInterceptor(authToken, email));
        }

        if (includeRoot) {
            objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
            objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        }
        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        okHttpClient.dispatcher().setMaxRequests(1);
        okHttpClient.dispatcher().setMaxRequestsPerHost(1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(serviceClass);
    }

    public <S> S createMediaService(Class<S> serviceClass,
                                    final boolean skipRetry) {
        ObjectMapper objectMapper = new ObjectMapper();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(logging);

        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        okHttpClient.dispatcher().setMaxRequests(1);
        okHttpClient.dispatcher().setMaxRequestsPerHost(1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(serviceClass);
    }

    private Interceptor getAcceptHeaderInterceptor() {
        return chain -> {
            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    //.header("Host", "api.ur-nl.com")
                    //.header("Connection","close;")
                    .method(original.method(), original.body());

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }


    /**
     * Get interceptor for adding header with authToken
     *
     * @param authToken - Users authToken
     * @return - Okhttp Interceptor
     */
    private Interceptor getAuthorizationHeaderInterceptor(String authToken, String email) {
        return chain -> {
            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder()
                    .header("auth_token", authToken)
                    .header("email", email)
                    .method(original.method(), original.body());

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }


}
