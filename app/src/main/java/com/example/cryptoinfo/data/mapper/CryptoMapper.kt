package com.example.cryptoinfo.data.mapper

import com.example.cryptoinfo.data.database.CryptoInfoDbModel
import com.example.cryptoinfo.data.network.model.CryptoInfoDto
import com.example.cryptoinfo.data.network.model.CryptoInfoJsonContainerDto
import com.example.cryptoinfo.data.network.model.CryptoNamesListDto
import com.example.cryptoinfo.domain.CryptoInfo
import com.google.gson.Gson

class CryptoMapper {

    fun mapDtoToDbModel(dto: CryptoInfoDto) = CryptoInfoDbModel(
            fromSymbol = dto.fromSymbol,
            toSymbol = dto.toSymbol,
            price = dto.price,
            lastUpdate = dto.lastUpdate,
            highDay = dto.highDay,
            lowDay = dto.lowDay,
            lastMarket = dto.lastMarket,
            imageUrl = dto.imageUrl
        )

    fun mapJsonToListCryptoInfo(json: CryptoInfoJsonContainerDto) : List<CryptoInfoDto> {
        val result = mutableListOf<CryptoInfoDto>()
        val jsonObject = json.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CryptoInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(list: CryptoNamesListDto) = list.names?.map {
            it.cryptoName?.name
        }?.joinToString(",") ?: ""

    fun mapDbModelToEntity(dbModel: CryptoInfoDbModel) = CryptoInfo(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = dbModel.price,
        lastUpdate = dbModel.lastUpdate,
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )
}