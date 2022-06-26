package com.sideproject.foodpandafake.hilt

import android.content.Context
import androidx.room.Room
import com.sideproject.foodpandafake.db.AppDb
import com.sideproject.foodpandafake.repo.FoodPanda
import com.sideproject.foodpandafake.repo.FoodPandaRepo
import com.sideproject.foodpandafake.repo.JsonAPI
import com.sideproject.foodpandafake.repo.JsonAPIRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Config.FoodPandaURL)
        .addConverterFactory(ScalarsConverterFactory.create())
//        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .removeHeader("User-Agent")
                        .addHeader(
                            "User-Agent",
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (HTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36"
                        )
                        .build()
                )
            }.build()
    }

    @Singleton
    @Provides
    fun provideJsonAPIRepo(retrofit: Retrofit): JsonAPIRepo =
        JsonAPIRepo(retrofit.create(JsonAPI::class.java))

    @Singleton
    @Provides
    fun provideFoodPandaRepo(retrofit: Retrofit): FoodPandaRepo =
        FoodPandaRepo(retrofit.create(FoodPanda::class.java))

    @Provides
    @Singleton
    fun provideDbInstance(@ApplicationContext context: Context): AppDb =
        Room.databaseBuilder(
            context.applicationContext,
            AppDb::class.java, "foodPanda.db"
        ).build()

}