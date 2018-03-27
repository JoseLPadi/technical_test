package com.josepadilla.technical_test.services;



import java.io.IOException;


import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 03/24/2018.
 * I dont like how to implement this class.
 */


public class RetrofitHelper {

    private static  String  cred = Credentials.basic("twp_k9ejP88LcuojHjmFkUFuYIUNYalg", "x");
    private OkHttpClient getHttpClient(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor("twp_k9ejP88LcuojHjmFkUFuYIUNYalg", "x"))
                .build();
        return client;
    }

    public Retrofit getRetrofit(){
        OkHttpClient client = getHttpClient();
        return new Retrofit.Builder()
                .baseUrl("https://yat.teamwork.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public EndpointInterface getServiceForSendAllNewTasks(String idTask){
        OkHttpClient client = getHttpClient();
        Retrofit rt = new  Retrofit.Builder()
                .baseUrl("https://yat.teamwork.com/tasklists/" + idTask + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return rt.create(EndpointInterface.class);
    }

    public EndpointInterface getServiceForGetAlltasks(int idProject){
        OkHttpClient client = getHttpClient();
        Retrofit rt = new  Retrofit.Builder()
                .baseUrl("https://yat.teamwork.com/projects/" + String.valueOf(idProject) + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return rt.create(EndpointInterface.class);
    }

    public EndpointInterface getService() {
        return getRetrofit().create(EndpointInterface.class);

    }

    public class BasicAuthInterceptor implements Interceptor {
        private String credentials;
        public BasicAuthInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }

    }


}
