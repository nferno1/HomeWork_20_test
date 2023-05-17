package nferno1.homework_20

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import nferno1.homework_20.data.Photo
import nferno1.homework_20.data.PhotoDataBase
import nferno1.homework_20.data.PhotosRepository
import nferno1.homework_20.domain.SavePhotoUseCase
import nferno1.homework_20.presentation.CameraViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("nferno1.homework_20", appContext.packageName)
    }
}

@RunWith(AndroidJUnit4::class)
class DataBaseTest {
    private lateinit var db: PhotoDataBase
    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    @Before
    fun init(){
        db = Room.databaseBuilder(
            appContext,
            PhotoDataBase::class.java,
            "db").build()
    }
    @Test
    fun useAppContextTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("nferno1.homework_20", appContext.packageName)
    }
    @Test
    fun dbIsInitializedTest() {
        assertTrue(this::db.isInitialized)
    }
    @Test
    fun dbInsertTest(){
        val photo = Photo("aaaa","aaaa")
        val outPhoto:Photo
        runBlocking {
            db.photoDao().insert(photo)
            outPhoto = db.photoDao().getAllNotFLow().first()
        }
        assertEquals(photo,outPhoto)

    }
    @Test
    fun dbDeleteTest(){
        val photo = Photo("46","20230515")
        val list:List<Photo>
        runBlocking {
            db.photoDao().delete(photo)
            list =db.photoDao().getAllNotFLow()
        }
        assertEquals(0,list.size)
    }
    @Test
    fun dbClearTest(){
        val photo1 = Photo("aaaa","2023-05-15")
        val photo2 = Photo("bbbb","bbbb")
        var list:List<Photo>
        runBlocking {
            db.photoDao().insert(photo1)
            db.photoDao().insert(photo2)
            list = db.photoDao().getAllNotFLow()
        }
        assertEquals(2,list.size)
        runBlocking {
            db.photoDao().clear()
            list = db.photoDao().getAllNotFLow()
        }
        assertEquals(0,list.size)
    }
}

@RunWith(AndroidJUnit4::class)
class ViewModelAndDatabaseIntegrationTest {
    private lateinit var db: PhotoDataBase
    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var cameraViewModel:CameraViewModel
    private lateinit var savePhotoUseCase: SavePhotoUseCase
    private lateinit var photosRepository: PhotosRepository

    @Before
    fun init(){
        db = Room.databaseBuilder(
            appContext,
            PhotoDataBase::class.java,
            "db").build()
        photosRepository = PhotosRepository(appContext)
        savePhotoUseCase =SavePhotoUseCase(photosRepository)
        cameraViewModel = CameraViewModel(savePhotoUseCase)
    }
    @Test
    fun useAppContextTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("nferno1.homework_20", appContext.packageName)
    }
    @Test
    fun dbIsInitializedTest() {
        assertTrue(this::db.isInitialized)
    }
    @Test
    fun savePhotoTest(){
        val photo = Photo("aaaa","aaaa")
        val outPhoto:Photo
        runBlocking {
            cameraViewModel.savePhoto(photo.uri,photo.date)
            outPhoto = db.photoDao().getAllNotFLow().first()
        }
        assertEquals(photo,outPhoto)
    }

}