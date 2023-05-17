package nferno1.homework_20.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nferno1.homework_20.domain.GetPhotosUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {
    val allPhotos = getPhotosUseCase.execute()
}