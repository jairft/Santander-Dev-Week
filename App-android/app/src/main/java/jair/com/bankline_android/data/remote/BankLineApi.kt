package jair.com.bankline_android.data.remote

import jair.com.bankline_android.domain.Movimentacao
import retrofit2.http.GET
import retrofit2.http.Path

//Conexão com Api
interface BankLineApi {

    @GET("movimentacoes/{id}")
    suspend fun findBankStatement(@Path("id") accountHolder: Int): List<Movimentacao>
}