package jair.com.bankline_android.ui.statement

import androidx.lifecycle.ViewModel
import jair.com.bankline_android.data.BankLineRepository

class BankStatementViewModel : ViewModel() {

    fun findBankStatement(accountHolderId: Int) =
        BankLineRepository.findBankStatement(accountHolderId)
}