package pe.cgonzales.hmsdemoapp.ui.main.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_maps.*
import pe.cgonzales.hmsdemoapp.R
import pe.cgonzales.hmsdemoapp.data.api.ApiHelper
import pe.cgonzales.hmsdemoapp.data.api.RetrofitBuilder
import pe.cgonzales.hmsdemoapp.data.model.Site
import pe.cgonzales.hmsdemoapp.ui.base.ViewModelFactory
import pe.cgonzales.hmsdemoapp.ui.main.viewmodel.MainViewModel
import pe.cgonzales.hmsdemoapp.utils.Status

class MainActivity : AppCompatActivity(), OnMapReadyCallback, HuaweiMap.OnCameraIdleListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var mMap: HuaweiMap
    private val initialLongitude = 77.57606362405674
    private val initialLatitude = 12.96306160968096

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment: SupportMapFragment? = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun doReverseGeocode(longitude: Double, latitude: Double) {
        viewModel.getSites(longitude, latitude).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resource.data?.let { reverseGeocode -> retrieveList(reverseGeocode.sites) }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(sites: List<Site>?) {
        sites?.let {
            if (it.isNotEmpty()) {
                val addressText = it[0].formatAddress ?: "Not found"
                addressTextView.setText(addressText)
            }
        }
    }

    override fun onMapReady(map: HuaweiMap) {
        mMap = map
        val initialPoint = LatLng(initialLatitude, initialLongitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPoint, 17f))
        map.setOnCameraIdleListener(this)
    }

    override fun onCameraIdle() {
        val centerOfMap: LatLng = mMap.cameraPosition.target
        doReverseGeocode(centerOfMap.longitude, centerOfMap.latitude)
    }
}