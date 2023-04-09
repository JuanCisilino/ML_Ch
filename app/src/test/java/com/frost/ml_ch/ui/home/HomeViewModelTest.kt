package com.frost.ml_ch.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.frost.ml_ch.model.Item
import com.frost.ml_ch.network.uc.ItemUC
import com.frost.ml_ch.ui.utils.LoadState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest{

    @RelaxedMockK
    private lateinit var itemUC: ItemUC

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(itemUC)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all items and set it`() = runTest {
        val mockedList = listOf(Item(title = "Test"))
        //Given
        coEvery { itemUC.search("ofertas") } returns mockedList

        //When
        viewModel.onCreate()

        //Then
        assert(viewModel.itemsLiveData.value == mockedList)
    }

    @Test
    fun `when search returns null validate live data value as null`() = runTest {
        //Given
        coEvery { itemUC.search("test") } returns null

        //When
        viewModel.getItems("test")

        //Then
        assert(viewModel.itemsLiveData.value == null)
    }

    @Test
    fun `when search returns null validate live data state value as null`() = runTest {
        //Given
        coEvery { itemUC.search("test") } returns null

        //When
        viewModel.getItems("test")

        //Then
        assert(viewModel.loadStateLiveData.value == LoadState.Error)
    }

}