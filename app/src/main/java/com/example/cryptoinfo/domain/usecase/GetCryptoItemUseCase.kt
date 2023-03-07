package com.example.cryptoinfo.domain.usecase

import com.example.cryptoinfo.domain.Repository

class GetCryptoItemUseCase (private val repository: Repository) {
    operator fun invoke(fromSymbol: String) = repository.getCryptoItem(fromSymbol)
}