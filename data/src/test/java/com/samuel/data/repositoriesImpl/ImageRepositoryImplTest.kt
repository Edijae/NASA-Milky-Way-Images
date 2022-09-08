package com.samuel.data.repositoriesImpl

import com.samuel.data.database.AppDatabase
import com.samuel.data.models.Metadata
import com.samuel.data.responses.ImageCollection
import com.samuel.data.responses.ImageResponse
import com.samuel.data.services.ImagesService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

class ImageRepositoryImplTest {

    private var annotations: AutoCloseable? = null

    @Mock
    private lateinit var imagesService: ImagesService

    @Mock
    private lateinit var mockDb: AppDatabase

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
    fun getImageSuccess() {
        return runTest {
            given(imagesService.getImage(milkway, imageType, 2017, 2017))
                .willReturn(Response.success(response))
            val result = repository.getImages(2017, 2017)
            println(result)
            assertEquals(Response.success(response), result)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getImageFailure() {
        return runTest {
            val result = repository.getImages(2017, 2017)
            println(result)
            assertEquals(null, result)
        }
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }
}