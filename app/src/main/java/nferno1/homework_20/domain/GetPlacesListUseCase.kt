package nferno1.homework_20.domain

import nferno1.homework_20.data.PlacesRepository
import nferno1.homework_20.data.place_data_classes.Place
import javax.inject.Inject

class GetPlacesListUseCase @Inject constructor(
    private val placesRepository: PlacesRepository
) {
    suspend fun execute(
        lon_min: Double,
        lat_min: Double,
        lon_max: Double,
        lat_max: Double
    ): List<Place>? {
        return placesRepository.getPlaces(lon_min, lat_min, lon_max, lat_max)
    }
}