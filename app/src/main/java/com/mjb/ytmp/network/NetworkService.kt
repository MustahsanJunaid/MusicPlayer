package com.mjb.ytmp.network

//import com.google.gson.GsonBuilder
//import com.mjb.ytmp.BuildConfig
//import com.mjb.ytmp.util.YoutubeHelper
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//import retrofit2.http.Query
//import java.util.concurrent.TimeUnit

interface NetworkService {

    companion object {
        var API_KEY = ""

//        private fun getOkHttpClient(): OkHttpClient {
//            val interceptor = HttpLoggingInterceptor()
//            if (BuildConfig.DEBUG) {
//                interceptor.level = HttpLoggingInterceptor.Level.BODY
//            } else {
//                interceptor.level = HttpLoggingInterceptor.Level.NONE
//            }
//
//            val builder = OkHttpClient.Builder()
//            builder.addInterceptor(interceptor)
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//            return builder.build()
//        }
//
//        val api: NetworkService by lazy {
//            val httpClient = getOkHttpClient()
//            val gson = GsonBuilder()
//                .setLenient()
//                .create()
//
//            Retrofit.Builder()
//                .baseUrl(YoutubeHelper.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(httpClient)
//                .build()
//                .create(NetworkService::class.java)
//        }
    }
}