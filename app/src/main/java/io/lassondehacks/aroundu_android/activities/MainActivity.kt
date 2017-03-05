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
import android.support.annotation.NonNull
import android.content.pm.PackageManager




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_whatshot_black_24dp))
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_query_builder_black_24dp))
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_my_location_black_24dp))
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_person_black_24dp))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabsPagerAdapter(supportFragmentManager, tab_layout.tabCount)
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
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf<String>(Manifest.permission.CAMERA), 0)
            } else {
                val takeVideoIntent = Intent(MediaStore.ACTION_)
                if (takeVideoIntent.resolveActivity(packageManager) != null) {
                    startActivityForResult(takeVideoIntent, 0)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val videoUri = intent.data
            println(videoUri)
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
        }
    }
}
