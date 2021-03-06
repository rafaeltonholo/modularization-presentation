package dev.tonholo.awesomeapp.data.remote.fake

import dev.tonholo.awesomeapp.data.model.Exif
import dev.tonholo.awesomeapp.data.model.Tag
import dev.tonholo.awesomeapp.data.model.UnsplashImage
import dev.tonholo.awesomeapp.data.model.Urls
import dev.tonholo.awesomeapp.data.remote.UnsplashApi
import dev.tonholo.awesomeapp.data.remote.dto.SearchDto

object FakeUnsplashApi : UnsplashApi {
    val fakeImage = UnsplashImage(
        blurHash = "fake",
        color = "#B00020",
        createdAt = "2022-05-15T02:03:58-04:00",
        currentUserCollections = emptyList(),
        description = "fake ".repeat(10),
        height = 1000,
        id = "fake",
        likedByUser = false,
        likes = 100,
        links = null,
        updatedAt = "2022-05-15T02:03:58-04:00",
        urls = Urls(
            full = "https://fakeimg.pl/1080/",
            raw = "https://fakeimg.pl/2048/",
            regular = "https://fakeimg.pl/720/",
            small = "https://fakeimg.pl/360/",
            thumb = "https://fakeimg.pl/240/",
        ),
        user = null,
        width = 1000,
        downloads = null,
        exif = Exif(
            make = "Canon",
            model = " EOS 5DS R",
            name = "Canon, EOS 5DS R",
            exposureTime = "1/100",
            aperture = "2.8",
            focalLength = "61.0",
            iso = 320,
        ),
        location = null,
        publicDomain = null,
        tags = (0..5).map { Tag(title = "tag $it") },
    )

    override suspend fun getPhotos(page: Int, perPage: Int, orderBy: String): List<UnsplashImage> =
        (0..100).map {
            UnsplashImage(
                blurHash = "fake",
                color = "fake",
                createdAt = "fake",
                currentUserCollections = emptyList(),
                description = "fake",
                height = 1000,
                id = "fake $it",
                likedByUser = it % 2 == 0,
                likes = 100,
                links = null,
                updatedAt = "fake",
                urls = Urls(
                    full = "https://fakeimg.pl/1080/",
                    raw = "https://fakeimg.pl/2048/",
                    regular = "https://fakeimg.pl/720/",
                    small = "https://fakeimg.pl/360/",
                    thumb = "https://fakeimg.pl/240/",
                ),
                user = null,
                width = 1000,
                downloads = null,
                exif = null,
                location = null,
                publicDomain = null,
                tags = emptyList(),
            )
        }

    override suspend fun getDetail(id: String): UnsplashImage =
        fakeImage

    override suspend fun search(query: String, page: Int, perPage: Int, orderBy: String): SearchDto =
        SearchDto(0, 0, emptyList())
}
