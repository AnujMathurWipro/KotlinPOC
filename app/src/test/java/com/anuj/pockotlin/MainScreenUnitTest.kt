package com.anuj.pockotlin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData

import com.anuj.pockotlin.models.BaseResult
import com.anuj.pockotlin.models.Response
import com.anuj.pockotlin.models.RowsItem
import com.anuj.pockotlin.repository.Repository
import com.anuj.pockotlin.util.Constants
import com.anuj.pockotlin.viewmodel.MainScreenViewModel

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

import java.util.ArrayList

import org.junit.Assert.*

class MainScreenUnitTest {
    @get:Rule
    var executorRule = InstantTaskExecutorRule()

    private var service: MainScreenViewModel? = null
    @Before
    fun setup() {
        val res = MutableLiveData<BaseResult<Response>>()
        val r = Repository()
        service = Mockito.spy(MainScreenViewModel(res, r))
    }

    @After
    fun tearDown() {
        service = null
    }

    @Test
    fun testGetErrorMessage_Error() {
        assertEquals(Constants.INTERNET_CONNECTIVITY_ERROR, service!!.errorMessage)
    }

    @Test
    fun testIsSuccessfulResponse_NoError() {
        val response = Mockito.mock(Response::class.java)
        val responseList = ArrayList<RowsItem>()
        responseList.add(RowsItem("", "", ""))
        responseList.add(RowsItem("", "", ""))
        Mockito.`when`(response.rows).thenReturn(responseList)
        assertTrue(service!!.isSuccessful(response))
    }

    @Test
    fun testIsSuccessfulResponse_Error() {
        val response = Mockito.mock(Response::class.java)
        val responseList: ArrayList<RowsItem>? = null
        Mockito.`when`(response.rows).thenReturn(responseList)
        assertFalse(service!!.isSuccessful(response))
    }

    @Test
    fun test_ShouldGetLiveData() {
        val data = service!!.getResult()
        assertNotNull("Live Data", data)
    }

    @Test
    fun test_ShouldFetchResponse() {
        val repo = Mockito.mock(Repository::class.java)
        val res = MutableLiveData<BaseResult<Response>>()
        service = Mockito.spy(MainScreenViewModel(res, repo))
        Mockito.doNothing().`when`(repo).getMainScreenList(res)

        val fetch = service!!.getResponse(false)
        assertTrue("Force = false and null data", fetch)
        val fetch2 = service!!.getResponse(true)
        assertTrue("Force = true and null data", fetch2)

        val res2 = MutableLiveData<BaseResult<Response>>()
        res2.value = BaseResult()
        service = Mockito.spy(MainScreenViewModel(res2, repo))

        val fetch3 = service!!.getResponse(false)
        assertFalse("Force = false and null data", fetch3)
        val fetch4 = service!!.getResponse(true)
        assertTrue("Force = true and null data", fetch4)
    }
}