package nferno1.homework_20.domain

import nferno1.homework_20.data.Photo
import nferno1.homework_20.data.PhotosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photosRepository: PhotosRepository
) {
    fun execute(): Flow<List<Photo>> {
        return photosRepository.getAllPhotos()
    }
}