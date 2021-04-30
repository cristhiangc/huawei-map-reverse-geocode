package pe.cgonzales.hmsdemoapp.data.model

data class ReverseGeocoding (
     val sites: List<Site>?
)

data class Site (
    val formatAddress: String?
)

data class ReverseGeocodingRequest (
    val location: LocationBody,
    val language: String = "en"
)

data class LocationBody(
        val lng: Double,
        val lat: Double
)
