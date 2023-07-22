package de.matthiaseymers.pixabaycompose

import app.cash.turbine.test
import de.matthiaseymers.pixabaycompose.domain.model.SearchHitEvent
import de.matthiaseymers.pixabaycompose.domain.usecase.GetLocalHitsUC
import de.matthiaseymers.pixabaycompose.domain.usecase.SearchHitsUC
import de.matthiaseymers.pixabaycompose.ui.search.SearchViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchHitsViewModelTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private val searchHitUC: SearchHitsUC = mockk()

    private val getLocalHitsUC: GetLocalHitsUC = mockk()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel(searchHitUC, getLocalHitsUC)
    }

    @Test
    fun `invoke searchHitUC when isOnline is true and return success`() = runTest {
        val query = "fruits"

        coEvery { searchHitUC.invoke(query) }.returns(flowOf(SearchHitEvent.Success(listOf())))

        viewModel.searchHits(query, true)

        viewModel.searchHitEvent.test {
            assertEquals(SearchHitEvent.Loading, awaitItem())

            coVerify { searchHitUC.invoke(query) }

            assertEquals(SearchHitEvent.Success(listOf()), awaitItem())

            expectNoEvents()
        }
    }

    @Test
    fun `invoke getLocalHitsUC when isOnline is false and return success`() = runTest {
        coEvery { getLocalHitsUC.invoke() }.returns(flowOf(SearchHitEvent.Success(listOf())))

        viewModel.searchHits(query = "", isOnline = false)

        viewModel.searchHitEvent.test {
            assertEquals(SearchHitEvent.Loading, awaitItem())

            coVerify { getLocalHitsUC.invoke() }

            assertEquals(SearchHitEvent.Success(listOf()), awaitItem())

            expectNoEvents()
        }
    }

    @Test
    fun `invoke searchHitUC emit Error when something went wrong`() = runTest {
        val expectedError = SearchHitEvent.Error(cause = IllegalStateException())
        val query = "fruits"

        coEvery { searchHitUC.invoke(query = query) }.returns(flowOf(expectedError))

        viewModel.searchHits(query = query, isOnline = true)

        viewModel.searchHitEvent.test {
            assertEquals(SearchHitEvent.Loading, awaitItem())

            coVerify { searchHitUC.invoke(query = query) }

            assertEquals(expectedError, awaitItem())

            expectNoEvents()
        }
    }

    @Test
    fun `invoke getLocalHitsUC emit Error when something went wrong`() = runTest {
        val expectedError = SearchHitEvent.Error(cause = IllegalStateException())
        coEvery { getLocalHitsUC.invoke() }.returns(flowOf(expectedError))

        viewModel.searchHits(query = "", isOnline = false)

        viewModel.searchHitEvent.test {
            assertEquals(SearchHitEvent.Loading, awaitItem())

            coVerify { getLocalHitsUC.invoke() }

            assertEquals(expectedError, awaitItem())

            expectNoEvents()
        }
    }
}