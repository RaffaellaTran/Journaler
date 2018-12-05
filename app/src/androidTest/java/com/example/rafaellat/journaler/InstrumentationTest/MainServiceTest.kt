package com.example.rafaellat.journaler.InstrumentationTest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.test.InstrumentationRegistry
import com.example.rafaellat.journaler.service.MainService
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull

class MainServiceTest {

    private var ctx: Context? = null
    private val tag = "Main service test"

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.v(tag, "Service disconnect")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.v(tag, "Service connect")
        }

    }

    @Before
    fun beforemainServiceTest() {
        Log.v(tag, "Starting")
        ctx = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun testMainService() {
        Log.v(tag, "Running")
        assertNotNull(ctx)
        val serviceIntent = Intent(ctx, MainService::class.java)
//        ctx?.startService(serviceIntent)
        val result = ctx?.bindService(
            serviceIntent, serviceConnection, android.content.Context.BIND_AUTO_CREATE
        )

        assertNotNull(result)
        //     assertTrue(result!!)
    }

    @After
    fun afterMainServiceTest() {
        Log.v(tag, "Finishing")
        ctx?.unbindService(serviceConnection)
        val serviceIntent = Intent(ctx, MainService::class.java)
        ctx?.stopService(serviceIntent)
    }
}