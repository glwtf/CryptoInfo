package com.example.cryptoinfo.domain.usecase

import com.example.cryptoinfo.domain.Repository

class LoadDataUseCase  (private val repository: Repository) {
    suspend operator fun invoke() = repository.loadData()
}