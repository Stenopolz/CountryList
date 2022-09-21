package com.stenopolz.countrylist

import com.stenopolz.countrylist.extensions.CallResult
import com.stenopolz.countrylist.model.data.application.CountryShortInfo
import com.stenopolz.countrylist.model.data.application.DispatchersHolder
import com.stenopolz.countrylist.model.repository.CountryRepository
import com.stenopolz.countrylist.viewmodel.CountryListViewModel
import com.stenopolz.countrylist.viewmodel.CountryShortUiModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryListViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(testDispatcher)

    private val countryRepository = mockk<CountryRepository>().apply {
        coEvery { fetchCountryList() } returns CallResult.Success(emptyList())
    }

    private val dispatchers = DispatchersHolder(
        main = testDispatcher,
        io = testDispatcher,
        default = testDispatcher
    )

    private val testCountry = CountryShortInfo(
        name = "test",
        capital = "test",
        region = "test",
        flagUrl = "test",
        id = "test"
    )

    private val testCountryUi = CountryShortUiModel(
        name = "test",
        capital = "test",
        region = "test",
        flagUrl = "test",
        id = "test",
        showFlag = true
    )

    private fun getViewModel() =
        CountryListViewModel(
            countryRepository = countryRepository,
            dispatchers = dispatchers
        )

    @Test
    fun `content is shown when loading successful`() = runTest(testDispatcher) {
        // given
        coEvery { countryRepository.fetchCountryList() } returns CallResult.Success(emptyList())

        //when
        val viewModel = getViewModel()
        advanceUntilIdle()

        // then
        assertEquals(true, viewModel.contentVisible.value)
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(false, viewModel.isError.value)
    }

    @Test
    fun `error is shown when loading error`() = runTest(testDispatcher) {
        // given
        coEvery { countryRepository.fetchCountryList() } returns CallResult.Error(
            IllegalArgumentException()
        )

        //when
        val viewModel = getViewModel()
        advanceUntilIdle()

        // then
        assertEquals(false, viewModel.contentVisible.value)
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(true, viewModel.isError.value)
    }

    @Test
    fun `flag is shown by default`() = runTest(testDispatcher) {
        // given
        coEvery { countryRepository.fetchCountryList() } returns CallResult.Success(
            listOf(
                testCountry,
                testCountry,
                testCountry
            )
        )
        val expectedResult = listOf(
            testCountryUi.copy(showFlag = true),
            testCountryUi.copy(showFlag = true),
            testCountryUi.copy(showFlag = true),
        )

        //when
        val viewModel = getViewModel()
        advanceUntilIdle()

        // then
        assertEquals(expectedResult, viewModel.countryList.value)
    }

    @Test
    fun `flag visibility change is reflected in the list`() = runTest(testDispatcher) {
        // given
        coEvery { countryRepository.fetchCountryList() } returns CallResult.Success(
            listOf(
                testCountry,
                testCountry,
                testCountry
            )
        )
        val expectedResult = listOf(
            testCountryUi.copy(showFlag = false),
            testCountryUi.copy(showFlag = false),
            testCountryUi.copy(showFlag = false),
        )

        //when
        val viewModel = getViewModel()
        viewModel.setShouldShowFlags(false)
        advanceUntilIdle()

        // then
        assertEquals(expectedResult, viewModel.countryList.value)
    }
}
