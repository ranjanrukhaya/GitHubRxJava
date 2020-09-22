package com.gaura.learn.githubrxjava.data

import com.gaura.learn.githubrxjava.model.GitHubRepo
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.github.com/";

interface GitHubService {

    companion object {
        fun create(): GitHubService {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build()


            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(GitHubService::class.java)
        }
    }

    @GET("users/{user}/starred")
    fun getStarredRepos(@Path("user") userName: String): Observable<List<GitHubRepo>>
}