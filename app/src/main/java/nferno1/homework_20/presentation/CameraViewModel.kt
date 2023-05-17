package nferno1.homework_20.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nferno1.homework_20.domain.SavePhotoUseCase
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val savePhotoUseCase: SavePhotoUseCase,
) : ViewModel() {
    fun savePhoto(uri: String, date: String) {
        viewModelScope.launch {
            savePhotoUseCase.execute(uri, date)
        }
    }
}