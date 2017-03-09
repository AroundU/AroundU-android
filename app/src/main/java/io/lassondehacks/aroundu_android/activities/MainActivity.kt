package io.lassondehacks.aroundu_android.activities

import android.Manifest
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import io.lassondehacks.aroundu_android.R
import io.lassondehacks.aroundu_android.adapters.TabsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.TabLayout.OnTabSelectedListener
import android.view.Menu
import android.content.Intent
import android.provider.MediaStore.ACTION_VIDEO_CAPTURE
import android.provider.MediaStore
import android.Manifest.permission
import android.content.Context
import android.support.annotation.NonNull
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.widget.FrameLayout
import android.opengl.ETC1.getWidth
import android.opengl.ETC1.getHeight
import android.os.Environment
import kotlinx.android.synthetic.main.fab_layout.*
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.AnimationUtils
import io.lassondehacks.aroundu_android.services.PostService
import android.os.Environment.DIRECTORY_PICTURES
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.support.v4.content.FileProvider
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import io.lassondehacks.aroundu_android.services.LocationService
import java.io.ByteArrayOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    val VIDEO_CAPTURE_REQUEST = 0
    val PHOTO_CAPTURE_REQUEST = 1

    var fabOpen = false

    var currentPhotoPath = ""

    var googleApiClient: GoogleApiClient? = null
    var mLastLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_whatshot_black_24dp))
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_query_builder_black_24dp))
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_my_location_black_24dp))
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_person_black_24dp))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabsPagerAdapter(this, supportFragmentManager, tab_layout.tabCount)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        postButton.setOnClickListener {
            runOnUiThread {
                if (!fabOpen) {
                    showFab1()
                    showFab2()
                    showFab3()
                    fabOpen = true
                } else {
                    hideFab1()
                    hideFab2()
                    hideFab3()
                    fabOpen = false
                }
            }
        }
    }

    fun showFab1() {
        val show_fab_1 = AnimationUtils.loadAnimation(application, R.animator.fab1_show)
        var layoutParams = fab_1.layoutParams as FrameLayout.LayoutParams
        layoutParams.rightMargin += (fab_1.getWidth() * 1.4).toInt()
        layoutParams.bottomMargin += (fab_1.getHeight() * 0.80).toInt()
        fab_1.layoutParams = layoutParams
        fab_1.startAnimation(show_fab_1)
        fab_1.isClickable = true

        fab_1.setOnClickListener {
            startVideoCaptureIntent()
            hideFab1()
            hideFab2()
            hideFab3()
            fabOpen = false
        }

    }

    fun hideFab1() {
        val hide_fab_1 = AnimationUtils.loadAnimation(application, R.animator.fab1_hide)
        var layoutParams = fab_1.layoutParams as FrameLayout.LayoutParams
        layoutParams.rightMargin -= (fab_1.getWidth() * 1.4).toInt()
        layoutParams.bottomMargin -= (fab_1.getHeight() * 0.80).toInt()
        fab_1.layoutParams = layoutParams
        fab_1.startAnimation(hide_fab_1)
        fab_1.isClickable = true
    }

    fun showFab2() {
        val show_fab_2 = AnimationUtils.loadAnimation(application, R.animator.fab2_show)
        var layoutParams = fab_2.layoutParams as FrameLayout.LayoutParams
        layoutParams.rightMargin -= (fab_2.getWidth() * 1.4).toInt()
        layoutParams.bottomMargin += (fab_2.getHeight() * 0.80).toInt()
        fab_2.layoutParams = layoutParams
        fab_2.startAnimation(show_fab_2)
        fab_2.isClickable = true

        fab_2.setOnClickListener {
            startPhotoCaptureIntent()
            hideFab1()
            hideFab2()
            hideFab3()
            fabOpen = false
        }

    }

    fun hideFab2() {
        val hide_fab_2 = AnimationUtils.loadAnimation(application, R.animator.fab2_hide)
        var layoutParams = fab_2.layoutParams as FrameLayout.LayoutParams
        layoutParams.rightMargin += (fab_2.getWidth() * 1.4).toInt()
        layoutParams.bottomMargin -= (fab_2.getHeight() * 0.80).toInt()
        fab_2.layoutParams = layoutParams
        fab_2.startAnimation(hide_fab_2)
        fab_2.isClickable = true
    }

    fun showFab3() {
        val show_fab_3 = AnimationUtils.loadAnimation(application, R.animator.fab3_show)
        var layoutParams = fab_3.layoutParams as FrameLayout.LayoutParams
        layoutParams.bottomMargin += (fab_3.getHeight() * 1.6).toInt()
        fab_3.layoutParams = layoutParams
        fab_3.startAnimation(show_fab_3)
        fab_3.isClickable = true

        fab_3.setOnClickListener {
            startSimplePost()
            hideFab1()
            hideFab2()
            hideFab3()
            fabOpen = false
        }

    }

    fun hideFab3() {
        val hide_fab_3 = AnimationUtils.loadAnimation(application, R.animator.fab3_hide)
        var layoutParams = fab_3.layoutParams as FrameLayout.LayoutParams
        layoutParams.bottomMargin -= (fab_3.getHeight() * 1.6).toInt()
        fab_3.layoutParams = layoutParams
        fab_3.startAnimation(hide_fab_3)
        fab_3.isClickable = true
    }

    fun startSimplePost() {

    }

    fun startPhotoCaptureIntent() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf<String>(Manifest.permission.CAMERA), 0)
        } else {
            var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                // Create the File where the photo should go
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) {
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    var photoURI = FileProvider.getUriForFile(this,
                            "io.lassondehacks.fileprovider",
                            photoFile)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, PHOTO_CAPTURE_REQUEST)
                }
            }
        }
    }

    fun startVideoCaptureIntent() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf<String>(Manifest.permission.CAMERA), 0)
        } else {
            val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            if (takeVideoIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takeVideoIntent, VIDEO_CAPTURE_REQUEST)
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == PHOTO_CAPTURE_REQUEST && resultCode == Activity.RESULT_OK) {

            val in1 = Intent(this, ImagePostEditViewActivity::class.java)
            in1.putExtra("image_path", this.currentPhotoPath)
            in1.putExtra("latitude", this.mLastLocation?.latitude)
            in1.putExtra("longitude", this.mLastLocation?.longitude)
            startActivity(in1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                if (takeVideoIntent.resolveActivity(packageManager) != null) {
                    startActivityForResult(takeVideoIntent, 0)
                }
            } else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        } else if(requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updatePosition()
            } else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }

    override fun onStart() {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()
        }
        googleApiClient?.connect()
        super.onStart()
    }

    override fun onStop() {
        googleApiClient?.disconnect()
        super.onStop()
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnected(p0: Bundle?) {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            updatePosition()
        }
    }

    fun updatePosition() {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
        LocationService.lastLocation = mLastLocation
    }
}
