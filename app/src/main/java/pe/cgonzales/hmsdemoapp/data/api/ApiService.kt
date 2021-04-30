package pe.cgonzales.hmsdemoapp.data.api

import pe.cgonzales.hmsdemoapp.data.model.ReverseGeocoding
import pe.cgonzales.hmsdemoapp.data.model.ReverseGeocodingRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @POST
    suspend fun getSites(@Url url: String, @Body body: ReverseGeocodingRequest): ReverseGeocoding
}