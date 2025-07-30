package com.sbmicroservices.apis.product_server;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

@Component
public class ProductServerApiClient {

    private ProductServerApis productServerApis;

    public ProductServerApis productServerApis() {
        if (productServerApis == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(httpLoggingInterceptor)
                            .addInterceptor(chain -> {
                                Request build = chain.request().newBuilder()
                                        .build();
                                return chain.proceed(build);
                            })
                            .writeTimeout(180, TimeUnit.MILLISECONDS)
                            .readTimeout(180, TimeUnit.MILLISECONDS)
                            .build()

                    ).baseUrl("http://localhost:4000/product/")
                    .build();
            productServerApis = retrofit.create(ProductServerApis.class);
        }
        return productServerApis;
    }
}
