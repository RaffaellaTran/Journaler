package com.example.rafaellat.journaler.activity

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.rafaellat.journaler.R
import com.example.rafaellat.journaler.permission.PermissionCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


abstract class BaseActivity : PermissionCompatActivity() {

    companion object {
        val REQUEST_GPS = 0 // ID for the request
//        private var fontExoBold: Typeface? = null
//        private var fontExoRegular: Typeface? = null
//
//        fun applyFonts(view: View, ctx: Context) {
//            var vTag = ""
//            if (view.tag is String) {
//                vTag = view.tag as String
//            }
//            when (view) {
//
//                is ViewGroup -> {
//                    for (x in 0..view.childCount - 1) {
//                        applyFonts(view.getChildAt(x), ctx)
//                    }
//                }
//                is Button -> {
//                    when (vTag) {
//                        ctx.getString(R.string.tag_font_bold) -> {
//                            view.typeface = fontExoBold
//                        }
//                        else -> {
//                            view.typeface = fontExoRegular
//                        }
//                    }
//                }
//                is TextView -> {
//                    when (vTag) {
//                        ctx.getString(R.string.tag_font_bold) -> {
//                            view.typeface = fontExoBold
//                        }
//                        else -> {
//                            view.typeface = fontExoRegular
//                        }
//                    }
//                }
//                is EditText -> {
//                    when (vTag) {
//                        ctx.getString(R.string.tag_font_bold) -> {
//                            view.typeface = fontExoBold
//                        }
//                        else -> {
//                            view.typeface = fontExoRegular
//                        }
//                    }
//                }
//            }
//        }
    }

    protected abstract val tag: String
    protected abstract fun getLayout(): Int
    protected abstract fun getActivityTitle(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        setContentView(getLayout())
        setSupportActionBar(toolbar)
        // requestGpsPermissions() //permission request for the ID defined
        requestPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        Log.v(tag, "[ ON CREATE]")
    }

//    private fun requestGpsPermissions() {
//        ActivityCompat.requestPermissions(
//            this@BaseActivity, arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ), REQUEST_GPS
//        )
//    }

    // assign a menu to the application bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.v(tag, "[ ON POST CREATE ]")
        // applyFonts()
    }

    override fun onRestart() {
        super.onRestart()
        Log.v(tag, "[ ON RESTART ]")
    }

    override fun onStart() {
        super.onStart()
        Log.v(tag, "[ ON START ]")
    }

    override fun onResume() {
        super.onResume()
        Log.v(tag, "[ ON RESUME ]")
     //   val animation = getAnimation(R.anim.top_to_bottom)
     //   findViewById<View>(R.id.toolbar).startAnimation(animation)
    }

  //  fun Activity.getAnimation(animation: Int): Animation = AnimationUtils.loadAnimation(this, animation)

    override fun onPostResume() {
        super.onPostResume()
        Log.v(tag, "[ ON POST RESUME ]")
    }


    override fun onPause() {
        super.onPause()
        Log.v(tag, "[ ON PAUSE ]")
     //   val animation = getAnimation(R.anim.hide_to_top)
     //   findViewById<View>(R.id.toolbar).startAnimation(animation)
    }

    override fun onStop() {
        super.onStop()
        Log.v(tag, "[ ON STOP ]")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(tag, "[ ON DESTROY ]")
    }

    //result for the request
    override fun onRequestPermissionsResult(
        requestCode:
        Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_GPS) {
            for (grantResult in grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    Log.i(tag, String.format(Locale.ENGLISH, "Permission granted [ %d ]", requestCode))
                } else {
                    Log.e(tag, String.format(Locale.ENGLISH, "Permission not granted [ %d ]", requestCode))
                }
            }
        }
    }


//    protected fun applyFonts() {
//        initFonts()
//        Log.v(tag, "Applying fonts [ START ]")
//        val rootView = findViewById<View>(android.R.id.content)
//        applyFonts(rootView, this)
//        Log.v(tag, "Applying fonts [ END ]")
//    }
//
//    private fun initFonts() {
//        if (fontExoBold == null) {
//            Log.v(tag, "Initializing font [ Exo2-Bold ]")
//            fontExoBold = Typeface.createFromAsset(assets, "fonts/Exo2-Bold.ttf")
//        }
//        if (fontExoRegular == null) {
//            Log.v(tag, "Initializing font [ Exo2-Regular ]")
//            fontExoRegular = Typeface.createFromAsset(assets, "fonts/Exo2-Regular.ttf")
//        }
//    }
}