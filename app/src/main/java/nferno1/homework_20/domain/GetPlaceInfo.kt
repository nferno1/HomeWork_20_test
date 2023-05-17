package nferno1.homework_20.domain

import nferno1.homework_20.data.PlacesRepository
import com.example.example.PlaceWithInfo
import javax.inject.Inject

class GetPlaceInfo @Inject constructor(
    private val placesRepository: PlacesRepository
) {
    suspend fun execute(xid: String): PlaceWithInfo {
        return placesRepository.getPlaceInfo(xid)
    }
}