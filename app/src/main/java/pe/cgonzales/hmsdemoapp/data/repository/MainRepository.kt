package pe.cgonzales.hmsdemoapp.data.repository

import pe.cgonzales.hmsdemoapp.data.api.ApiHelper
import pe.cgonzales.hmsdemoapp.data.model.ReverseGeocodingRequest

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getSites(body: ReverseGeocodingRequest) = apiHelper.getSites(body)
}