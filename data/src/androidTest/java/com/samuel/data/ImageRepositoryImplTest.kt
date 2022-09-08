package com.samuel.data

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.samuel.data.database.AppDatabase
import com.samuel.data.models.Metadata
import com.samuel.data.repositoriesImpl.ImagesRepositoryImpl
import com.samuel.data.responses.ImageCollection
import com.samuel.data.responses.ImageResponse
import com.samuel.data.services.ImagesService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ImageRepositoryImplTest {

    private var annotations: AutoCloseable? = null

    @Mock
    private lateinit var imagesService: ImagesService

    private var mockDb: AppDatabase = AppDatabase.create(
        ApplicationProvider.getApplicationContext(),
        useInMemory = true
    )

    private lateinit var response: ImageResponse

    private lateinit var repository: ImagesRepositoryImpl

    private val milkway = "milky way"
    private val imageType = "image"

    @Before
    fun setUp() {
        annotations = MockitoAnnotations.openMocks(this)
        response = ImageResponse(ImageCollection("", "", emptyList(), Metadata()))
        repository = ImagesRepositoryImpl(imagesService, mockDb)
    }

    @After
    fun release() {
        annotations?.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getImageSuccess() = runTest {
        given(imagesService.getImage(milkway, imageType, 2017, 2017))
            .willReturn(Response.success(response))
        val result = repository.getImages(2017, 2017)
        println(result)
        assertEquals(true, result.success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getImageFailure() {
        return runTest {
            given(imagesService.getImage(milkway, imageType, 2017, 2017))
                .willReturn(Response.error(500, "".toResponseBody(null)))
            val result = repository.getImages(2017, 2017)
            assertEquals(false, result.success)
        }
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }
}