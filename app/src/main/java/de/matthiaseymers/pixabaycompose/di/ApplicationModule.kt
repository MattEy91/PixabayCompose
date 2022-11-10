package de.matthiaseymers.pixabaycompose.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.matthiaseymers.pixabaycompose.PixabayApplication
import de.matthiaseymers.pixabaycompose.core.Constants.BASE_URL
import de.matthiaseymers.pixabaycompose.data.local.PixabayDatabase
import de.matthiaseymers.pixabaycompose.data.remote.PixabayApi
import de.matthiaseymers.pixabaycompose.data.repository.HitRepositoryImpl
import de.matthiaseymers.pixabaycompose.domain.repository.HitRepository
import de.matthiaseymers.pixabaycompose.domain.usecase.GetLocalHitsUC
import de.matthiaseymers.pixabaycompose.domain.usecase.SearchHitsUC
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    private val readTimeout = 30
    private val writeTimeout = 30
    private val connectionTimeout = 10
    private val cacheSize = 100000L

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): PixabayApplication {
        return app as PixabayApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: PixabayApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(
        @ApplicationContext
        context: Context
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(connectionTimeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(writeTimeout.toLong(), TimeUnit.SECONDS)
            .cache(provideHttpCacheFolder(context))

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providePixabayApi(retrofit: Retrofit): PixabayApi {
        return retrofit.create(PixabayApi::class.java)
    }

    @Provides
    @Singleton
    fun providePixabayDatabase(application: Application): PixabayDatabase {
        return Room.databaseBuilder(application, PixabayDatabase::class.java, "pixabay_db").build()
    }

    @Provides
    @Singleton
    fun provideHitRepository(api: PixabayApi, db: PixabayDatabase): HitRepository {
        return HitRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideSearchHitsUseCase(repo: HitRepository): SearchHitsUC {
        return SearchHitsUC(repo)
    }

    @Provides
    @Singleton
    fun provideGetLocalHitsUseCase(repo: HitRepository): GetLocalHitsUC {
        return GetLocalHitsUC(repo)
    }

    private fun provideHttpCacheFolder(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, cacheSize)
}