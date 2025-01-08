package com.example.transactionsapp.di

import android.content.Context
import com.example.transactionsapp.data.api.AuthApi
import com.example.transactionsapp.data.api.TransactionsApi
import com.example.transactionsapp.data.repository.AuthRepositoryImpl
import com.example.transactionsapp.data.repository.TransactionsRepositoryImpl
import com.example.transactionsapp.domain.repository.AuthRepository
import com.example.transactionsapp.domain.repository.TransactionsRepository
import com.example.transactionsapp.utils.BASE_URL
import com.example.transactionsapp.utils.SecurePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


class AuthInterceptor @Inject constructor(
    private val securePreferences: SecurePreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        println("Started - ${securePreferences.getToken()}")
        val token = securePreferences.getToken()
        val request = if (!token.isNullOrEmpty()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}

// AppModule.kt
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideAuthApi(): AuthApi {

        val interceptor = HttpLoggingInterceptor()

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    fun provideTransactionsApi(okHttpClient: OkHttpClient): TransactionsApi {
//
        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TransactionsApi::class.java)
    }

    @Provides
    fun provideAuthRepository(apiService: AuthApi): AuthRepository {
        return AuthRepositoryImpl(apiService)
    }

    @Provides
    fun provideTransactionRepository(apiService: TransactionsApi): TransactionsRepository {
        return TransactionsRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSecurePreferences(@ApplicationContext context: Context): SecurePreferences {
        return SecurePreferences(context)
    }
}
