package jair.com.bankline_android.data

import android.util.Log
import androidx.lifecycle.liveData
import jair.com.bankline_android.data.remote.BankLineApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object BankLineRepository {

    private val TAG = javaClass.simpleName

    private val restApi by lazy {
        Retrofit.Builder()
        .baseUrl("http://192.168.0.103:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BankLineApi::class.java)
        }

    fun findBankStatement(accountHolderId: Int) = liveData {
        emit(State.Wait)
        try {
            emit(State.Success(date = restApi.findBankStatement(accountHolderId)))
        }catch (e: Exception) {
            Log.e(TAG, e.message, e)
            emit(State.Error(e.message))
        }
    }
}