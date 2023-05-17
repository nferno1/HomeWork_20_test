package nferno1.homework_20.data

import nferno1.homework_20.data.place_data_classes.Place
import com.example.example.PlaceWithInfo
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class PlacesRepository @Inject constructor() {
    object RetrofitServices {
        private const val BASE_URL = "https://api.opentripmap.com/0.1/ru/places/"
        private val gson = GsonBuilder().setLenient().create()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val searchPlacesApi: SearchPlacesApi = retrofit.create(
            SearchPlacesApi::class.java
        )
        val searchPlaceInfo: SearchPlaceInfo = retrofit.create(
            SearchPlaceInfo::class.java
        )

        interface SearchPlacesApi {
            @GET("bbox?kinds=interesting_places&format=json&apikey=5ae2e3f221c38a28845f05b695f7836e29d776cc9d1f63bda301ec98")
            suspend fun getPlaces(
                @Query("lon_min") lon_min: String,
                @Query("lat_min") lat_min: String,
                @Query("lon_max") lon_max: String,
                @Query("lat_max") lat_max: String
            ): List<Place>
        }

        interface SearchPlaceInfo {
            @GET("xid/{id}?apikey=5ae2e3f221c38a28845f05b695f7836e29d776cc9d1f63bda301ec98")
            suspend fun getPlaceInfo(@Path("id") id: String): PlaceWithInfo

        }

    }

    suspend fun getPlaces(
        lon_min: Double,
        lat_min: Double,
        lon_max: Double,
        lat_max: Double
    ): List<Place>? {
        return try {
            RetrofitServices.searchPlacesApi.getPlaces(
                lon_min.toString(),
                lat_min.toString(),
                lon_max.toString(),
                lat_max.toString()
            )
        } catch (e: Throwable) {
            null
        }
    }

    suspend fun getPlaceInfo(xid: String): PlaceWithInfo {
        return RetrofitServices.searchPlaceInfo.getPlaceInfo(xid)
    }
}