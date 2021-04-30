package pe.cgonzales.hmsdemoapp.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import pe.cgonzales.hmsdemoapp.data.model.LocationBody
import pe.cgonzales.hmsdemoapp.data.model.ReverseGeocodingRequest
import pe.cgonzales.hmsdemoapp.data.repository.MainRepository
import pe.cgonzales.hmsdemoapp.utils.Resource

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getSites(longitude: Double, latitude: Double) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val locationBody = LocationBody(longitude, latitude)
            val bodyRequest = ReverseGeocodingRequest(locationBody)
            emit(Resource.success(data = mainRepository.getSites(bodyRequest)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}