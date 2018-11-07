package com.example.rafaellat.journaler.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.GravityCompat
import android.util.Log
import android.view.MenuItem

import com.example.rafaellat.journaler.R
import com.example.rafaellat.journaler.fragment.ItemsFragment
import com.example.rafaellat.journaler.navigation.NavigationDrawerAdapter
import com.example.rafaellat.journaler.navigation.NavigationDrawerItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val tag = "Main activity"

    override fun getLayout() = R.layout.activity_main

    override fun getActivityTitle() = R.string.app_name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pager.adapter = ViewPagerAdapter(supportFragmentManager)
        val menuItems = mutableListOf<NavigationDrawerItem>()
        val today = NavigationDrawerItem(
            getString(R.string.today),
            Runnable { pager.setCurrentItem(0, true) }
        )
        val next7Days = NavigationDrawerItem(
            getString(R.string.next_seven_days),
            Runnable { pager.setCurrentItem(1, true) }
        )
        val todos = NavigationDrawerItem(
            getString(R.string.todos),
            Runnable { pager.setCurrentItem(2, true) }
        )
        val notes = NavigationDrawerItem(
            getString(R.string.notes),
            Runnable { pager.setCurrentItem(3, true) }
        )

        menuItems.add(today)
        menuItems.add(next7Days)
        menuItems.add(todos)
        menuItems.add(notes)

        val navigationDraweAdapter =
            NavigationDrawerAdapter(this, menuItems)
        left_drawer.adapter = navigationDraweAdapter
    }

    private class ViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
        override fun getItem(position: Int): Fragment {
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
            R.id.options_menu -> {
                Log.v(tag, "Options menu.")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}
