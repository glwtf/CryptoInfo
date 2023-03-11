package com.example.cryptoinfo.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

import com.example.cryptoinfo.data.database.DataBase
import com.example.cryptoinfo.data.mapper.CryptoMapper
import com.example.cryptoinfo.data.network.ApiFactory
import com.example.cryptoinfo.domain.CryptoInfo
import com.example.cryptoinfo.domain.Repository
import kotlinx.coroutines.delay

class CryptoRepositoryImpl(
    private val application: Application
): Repository {

    private val coinInfoDao = DataBase.getInstance(application).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService

    private val mapper = CryptoMapper()

    override fun getCryptoList(): LiveData<List<CryptoInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCryptoItem(fromSymbol: String): LiveData<CryptoInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fSyms = mapper.mapNamesListToString(topCoins)
            val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
            val coinInfoDtoList = mapper.mapJsonToListCryptoInfo(jsonContainer)
            val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
            coinInfoDao.insertPriceList(dbModelList)
            delay(10000)
        }
    }
}