package nferno1.homework_20.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Photo::class], version = 1)
abstract class PhotoDataBase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}