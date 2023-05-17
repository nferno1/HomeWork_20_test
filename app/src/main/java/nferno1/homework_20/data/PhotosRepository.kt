package nferno1.homework_20.data

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosRepository @Inject constructor(@ApplicationContext appContext: Context) {

    private var db: PhotoDataBase

    init {
        db = Room.databaseBuilder(
            appContext,
            PhotoDataBase::class.java,
            "db"
        ).build()
    }

    suspend fun savePhoto(uri: String, date: String) {
        try {
            db.photoDao().insert(
                Photo(uri, date)
            )
        } catch (_: Throwable) {
        }
    }

    fun getAllPhotos(): Flow<List<Photo>> {
        return db.photoDao().getAll()
    }
}