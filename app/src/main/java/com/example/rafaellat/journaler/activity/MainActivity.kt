package com.example.rafaellat.journaler.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import android.util.Log
import android.view.MenuItem
import com.example.rafaellat.journaler.R
import com.example.rafaellat.journaler.fragment.ItemsFragment
import com.example.rafaellat.journaler.navigation.NavigationDrawerAdapter
import com.example.rafaellat.journaler.navigation.NavigationDrawerItem
import com.example.rafaellat.journaler.preferences.PreferencesConfiguration
import com.example.rafaellat.journaler.preferences.PreferencesProvider
import com.example.rafaellat.journaler.service.MainService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val tag = "Main activity"

    override fun getLayout() = R.layout.activity_main

    override fun getActivityTitle() = R.string.app_name

    private val keyPagePosition = "keyPagePosition"

    private var service: MainService? = null
    private val gson = Gson()
    private val synchronize: NavigationDrawerItem by lazy {
        NavigationDrawerItem(
            getString(R.string.synchronize), Runnable { service?.synchronize() },
            false
        )
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            service = null
            synchronize.enabled = false
        }

        override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {
            if (binder is MainService.MainServiceBinder) {
                service = binder.getService()
                service?.let {
                    synchronize.enabled = true
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pager.adapter = ViewPagerAdapter(supportFragmentManager)

        val menuItems = mutableListOf<NavigationDrawerItem>()

        val todos = NavigationDrawerItem(
            getString(R.string.todos),
            Runnable { pager.setCurrentItem(1, true) }
        )
        val notes = NavigationDrawerItem(
            getString(R.string.notes),
            Runnable {
                pager.setCurrentItem(2, true) }
        )

        when(pager.currentItem){
            1 -> {
                val intent= Intent(this, NoteActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                val intent= Intent(this, NoteActivity::class.java)
                startActivity(intent)
            }
            else ->{
                Log.d(tag, "empty")
            }
        }

        menuItems.add(todos)
        menuItems.add(notes)
        menuItems.add(synchronize)

        val navigationDraweAdapter =
            NavigationDrawerAdapter(this, menuItems)
        left_drawer.adapter = navigationDraweAdapter

        val provider = PreferencesProvider()
        val config = PreferencesConfiguration(
            "journaler_prefs",
            Context.MODE_PRIVATE
        )
        val preferences = provider.obtain(config, this)

        pager.adapter = ViewPagerAdapter(supportFragmentManager)
        pager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // Ignore
            }

            override fun onPageScrolled(
                position: Int, positionOffset:
                Float, positionOffsetPixels: Int
            ) {
                // Ignore
            }

            override fun onPageSelected(position: Int) {
                Log.v(tag, "Page [ $position ]")
                preferences.edit().putInt(keyPagePosition, position).apply()
            }
        })

        val pagerPosition = preferences.getInt(keyPagePosition, 0)
        pager.setCurrentItem(pagerPosition, true)

        val serviceIntent = Intent(this, MainService::class.java)
        startService(serviceIntent)

    }

    private class ViewPagerAdapter(manager: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(manager) {
        override fun getItem(position: Int): androidx.fragment.app.Fragment {
            return ItemsFragment()
        }

        override fun getCount(): Int {
            return 5
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawing_menu -> {
                drawer_layout.openDrawer(GravityCompat.START)

                return true
            }
//            R.id.options_menu -> {
//                Log.v(tag, "Options menu.")
//                return true
//            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onResume() {
        super.onResume()
        val intent = Intent(this, MainService::class.java)
        bindService(
            intent, serviceConnection,
            android.content.Context.BIND_AUTO_CREATE
        )
    }

    override fun onPause() {
        super.onPause()
        unbindService(serviceConnection)
    }

}
