package com.example.cryptoinfo.domain.usecase

import com.example.cryptoinfo.domain.Repository

class GetCryptoListUseCase (private val repository: Repository) {
    operator fun invoke() = repository.getCryptoList()
}