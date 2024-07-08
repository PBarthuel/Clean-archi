package com.pbarthuel.testbfor.data.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DispatchersModuleTest {

    private lateinit var dispatchersModule: DispatchersModule

    @Before
    fun setUp() {
        dispatchersModule = DispatchersModule
    }

    @Test
    fun `test providesDefaultDispatcher`() {
        val defaultDispatcher: CoroutineDispatcher = dispatchersModule.providesDefaultDispatcher()
        assertEquals(Dispatchers.Default, defaultDispatcher)
    }

    @Test
    fun `test providesIoDispatcher`() {
        val ioDispatcher: CoroutineDispatcher = dispatchersModule.providesIoDispatcher()
        assertEquals(Dispatchers.IO, ioDispatcher)
    }

    @Test
    fun `test providesMainDispatcher`() {
        val mainDispatcher: CoroutineDispatcher = dispatchersModule.providesMainDispatcher()
        assertEquals(Dispatchers.Main, mainDispatcher)
    }

    @Test
    fun `test providesMainImmediateDispatcher`() {
        val mainImmediateDispatcher: CoroutineDispatcher = dispatchersModule.providesMainImmediateDispatcher()
        assertEquals(Dispatchers.Main.immediate, mainImmediateDispatcher)
    }
}