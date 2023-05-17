package nferno1.homework_20.domain

import nferno1.homework_20.data.PhotosRepository
import javax.inject.Inject

class SavePhotoUseCase @Inject constructor(
    private val photosRepository: PhotosRepository
) {
    suspend fun execute(uri: String, date: String) {
        photosRepository.savePhoto(uri, date)
    }
}