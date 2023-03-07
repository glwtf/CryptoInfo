package com.example.cryptoinfo.domain

import androidx.lifecycle.LiveData

interface Repository {
    fun getCryptoItem(fromSymbol: String) : LiveData<CryptoInfo>
    fun getCryptoList() : LiveData<List<CryptoInfo>>
}