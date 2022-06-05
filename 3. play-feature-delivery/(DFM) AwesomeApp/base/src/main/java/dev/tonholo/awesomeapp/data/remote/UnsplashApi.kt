package dev.tonholo.awesomeapp.data.remote

import dev.tonholo.awesomeapp.data.model.UnsplashImage
import dev.tonholo.awesomeapp.data.remote.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Represents the requests for Unsplash API.
 * @see <a href="https://unsplash.com/documentation">Unsplash API docs</a> for more reference
 */
interface UnsplashApi {
    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("order_by") orderBy: String = ORDER_BY_LATEST,
    ): List<UnsplashImage>


    @GET("/photos/{id}")
    suspend fun getDetail(
        @Path("id") id: String,
    ): UnsplashImage

    @GET("/search/photos")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("order_by") orderBy: String = ORDER_BY_RELEVANT,
    ): SearchDto

    companion object {
        const val ORDER_BY_RELEVANT = "relevant"
        const val ORDER_BY_LATEST = "latest"
        const val ORDER_BY_OLDEST = "oldest"
        const val ORDER_BY_POPULAR = "POPULAR"
    }
}
