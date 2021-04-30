package pe.cgonzales.hmsdemoapp.data.api

import pe.cgonzales.hmsdemoapp.data.model.ReverseGeocodingRequest

private const val ROOT_URL = "https://siteapi.cloud.huawei.com/mapApi/v1/siteService/"
private const val connection = "?key="
private const val encodeApiKey = "C..."
private const val url = ROOT_URL + "reverseGeocode" + connection + encodeApiKey

class ApiHelper(private val apiService: ApiService) {

    suspend fun getSites(body: ReverseGeocodingRequest) = apiService.getSites(url, body)
}