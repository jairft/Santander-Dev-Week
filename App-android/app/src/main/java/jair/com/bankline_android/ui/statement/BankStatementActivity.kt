package jair.com.bankline_android.ui.statement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import jair.com.bankline_android.R
import jair.com.bankline_android.data.State
import jair.com.bankline_android.databinding.ActivityBankStatementBinding
import jair.com.bankline_android.databinding.ActivityWelcomeBinding
import jair.com.bankline_android.domain.Correntista
import jair.com.bankline_android.domain.Movimentacao
import jair.com.bankline_android.domain.TipoMovimentacao
import java.lang.IllegalArgumentException

class BankStatementActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ACCOUNT_HOLDER = "jair.com.bankline_android.ui.statement.EXTRA_ACCOUNT_HOLDER"
    }
    private val binding by lazy {
        ActivityBankStatementBinding.inflate(layoutInflater)
    }
    private val accounteHolder by lazy {
        intent.getParcelableExtra<Correntista>(EXTRA_ACCOUNT_HOLDER) ?: throw IllegalArgumentException()
    }
    private val viewModel by viewModels<BankStatementViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvBankStatement.layoutManager = LinearLayoutManager(this)

        findBankStatement()

        binding.srlBankStatement.setOnRefreshListener { findBankStatement() }

    }

    private fun findBankStatement() {
       viewModel.findBankStatement(accounteHolder.id).observe(this) { state ->
            when(state) {
                is State.Success -> {
                    binding.rvBankStatement.adapter = state.date?.let { BankStatementAdapter(it) }
                    binding.srlBankStatement.isRefreshing = false
                }
                is State.Error -> {
                    state.message?.let { Snackbar.make(binding.rvBankStatement, it, Snackbar.LENGTH_LONG).show() }
                    binding.srlBankStatement.isRefreshing = false
                }
                State.Wait -> binding.srlBankStatement.isRefreshing = true
            }
       }
    }
}