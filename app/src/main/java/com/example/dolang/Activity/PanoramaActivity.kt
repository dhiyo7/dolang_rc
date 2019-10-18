package com.example.dolang.Activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dolang.Adapter.PanoramaPreviewAdapter
import com.example.dolang.Model.Panorama
import com.example.dolang.R

import kotlinx.android.synthetic.main.activity_panorama.*
import kotlinx.android.synthetic.main.content_panorama.*

class PanoramaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panorama)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        rv_pano.apply {
            layoutManager = LinearLayoutManager(this@PanoramaActivity)
            adapter = PanoramaPreviewAdapter(getPanoramas(), this@PanoramaActivity)
        }
    }

    private fun getPanoramas() = intent.getParcelableArrayListExtra<Panorama>("PANORAMAS")

}
