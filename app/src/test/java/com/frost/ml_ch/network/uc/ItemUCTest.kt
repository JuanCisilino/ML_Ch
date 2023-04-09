package com.frost.ml_ch.network.uc

import com.frost.ml_ch.model.Item
import com.frost.ml_ch.network.repository.MeliRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ItemUCTest{

    @RelaxedMockK
    private lateinit var meliRepository: MeliRepository

    lateinit var itemUC: ItemUC

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        itemUC = ItemUC(meliRepository)
    }

    @Test
    fun `when the api doesnt return anything then return null`() = runBlocking{
        //Given
        coEvery { meliRepository.search("ofertas") } returns emptyList()

        //When
        val response = itemUC.search("ofertas")

        //Then
        assert(response == null)

    }

    @Test
    fun `when the api returns something then return empty list`() = runBlocking{
        val mockedList = listOf(Item(title = "Test"))
        //Given
        coEvery { meliRepository.search("ofertas") } returns mockedList

        //When
        val response = itemUC.search("ofertas")

        //Then
        assert(response != null)

    }
}