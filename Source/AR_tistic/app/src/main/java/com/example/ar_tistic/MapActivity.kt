package com.example.ar_tistic

import User
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapActivity : AppCompatActivity(){
    lateinit var usr:User
    private lateinit var map:MapView

    @SuppressLint("WrongViewCast")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(
            applicationContext, PreferenceManager.getDefaultSharedPreferences(
                applicationContext
            )
        )
        //LOAD USER
        usr = intent.getSerializableExtra("usr") as User
        // LOAD LAYOUT
        setContentView(R.layout.activity_map)
        // MAP Initialization
        map = findViewById<MapView>(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setBuiltInZoomControls(false)
        map.setMultiTouchControls(true)
        map.setZoomRounding(true)
        map.minZoomLevel = 19.0
        map.maxZoomLevel = 20.0

        // MAP CONTROL SETTING
        val mapController: IMapController = map.getController()
        mapController.setZoom(20.0)

        // L0CATION CREATE
        var loc = MyLocationNewOverlay(GpsMyLocationProvider(applicationContext), map)
        loc.enableMyLocation()
        loc.enableFollowLocation()
        loc.isDrawAccuracyEnabled = false
        map.getOverlays().add(loc)

        val mRotationGestureOverlay = RotationGestureOverlay(applicationContext, map)
        mRotationGestureOverlay.setEnabled(true)
        map.getOverlays().add(mRotationGestureOverlay)

        // COMPASS
        val compass = CompassOverlay(
            applicationContext, InternalCompassOrientationProvider(
                applicationContext
            ), map
        )
        compass.enableCompass()
        map.getOverlays().add(compass)

        // GEOPOINT CREATE
        val IUT = GeoPoint(45.76196, 3.10846)
        val AncienneEglise = GeoPoint(45.77355, 3.28696)
        val Casa =  GeoPoint(45.77252, 3.28249)

        // ITEMS ARRAY
        val items = ArrayList<OverlayItem>()
        val home = OverlayItem("IUT Clermont-Ferrand", "IUT de Clermont Auvergne", IUT)
        val m = home.getMarker(0)
        val eglise = OverlayItem("Ancienne Eglise de Vertaizon", "Ancienne Eglise de Vertaizon", AncienneEglise)
        val e = eglise.getMarker(0)
        val casa = OverlayItem("CASA", "CASA", Casa)
        val c = casa.getMarker(0)
        items.add(home)
        items.add(eglise)
        items.add(casa)

        // DISPLAY ITEMS INFO
        val overlayTap = ItemizedOverlayWithFocus(
            applicationContext, items, object : OnItemGestureListener<OverlayItem?> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    return false
                }
            })
        overlayTap.setFocusItemsOnTap(true)
        map.getOverlays().add(overlayTap)

        val centerLoc = findViewById<ImageButton>(R.id.centerLoc)
        centerLoc.setOnClickListener{
            loc.enableFollowLocation()
        }

        //BUTTONS
        val paintBtn = findViewById<ImageButton>(R.id.drawButton)
        paintBtn.setOnClickListener {
            val intent = Intent(this, PaintActivity::class.java)
            startActivity(intent)
            finish()
        }
        val profilBtn = findViewById<ImageButton>(R.id.profileButton)
        profilBtn.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            intent.putExtra("usr", usr)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

}
