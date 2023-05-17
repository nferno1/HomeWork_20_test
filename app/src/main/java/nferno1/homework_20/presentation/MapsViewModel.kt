package nferno1.homework_20.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import nferno1.homework_20.domain.GetPlaceInfo
import nferno1.homework_20.domain.GetPlacesListUseCase
import nferno1.homework_20.data.place_data_classes.Place
import com.example.example.PlaceWithInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val getPlacesListUseCase: GetPlacesListUseCase,
    private val getPlaceInfo: GetPlaceInfo
) : ViewModel() {
    private val _places = MutableStateFlow<List<Place>?>(null)
    val places = _places.asStateFlow()
    var showMarkers = true

    private val _placeWithInfo = MutableStateFlow<PlaceWithInfo?>(null)
    val placeWithInfo = _placeWithInfo.asStateFlow()

    fun loadMarkers(lon_min: Double, lat_min: Double, lon_max: Double, lat_max: Double) {
        viewModelScope.launch {
            _places.value = getPlacesListUseCase.execute(lon_min, lat_min, lon_max, lat_max)
        }
    }

    fun markerOnClick(xid: String) {
        viewModelScope.launch {
            _placeWithInfo.value = null
            _placeWithInfo.value = getPlaceInfo.execute(xid)
        }
    }
}