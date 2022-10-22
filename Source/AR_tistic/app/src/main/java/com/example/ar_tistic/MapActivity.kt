package com.example.ar_tistic

import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
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

class MapActivity : AppCompatActivity() {
    private lateinit var map:MapView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(
            applicationContext, PreferenceManager.getDefaultSharedPreferences(
                applicationContext
            )
        )

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

        val mRotationGestureOverlay = RotationGestureOverlay(applicationContext, map)
        mRotationGestureOverlay.setEnabled(true)
        map.getOverlays().add(mRotationGestureOverlay)


        // MAP CONTROL SETTING
        val mapController: IMapController = map.getController()
        mapController.setZoom(20.0)

        // L0CATION CREATE
        val loc = MyLocationNewOverlay(
            GpsMyLocationProvider(
                applicationContext
            ), map
        )
        map.getOverlays().add(loc)
        loc.enableMyLocation()
        loc.enableFollowLocation()

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

        // ITEMS ARRAY
        val items = ArrayList<OverlayItem>()
        val home = OverlayItem("IUT Clermont-Ferrand", "IUT de Clermont Auvergne", IUT)
        val m = home.getMarker(0)
        items.add(home)

        // DISPLAY ITEMS INFO
        val mOverlay = ItemizedOverlayWithFocus(
            applicationContext, items, object : OnItemGestureListener<OverlayItem?> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    return false
                }
            })

        // ITEM FOCUS
        mOverlay.setFocusItemsOnTap(true)
        map.getOverlays().add(mOverlay)

        
        //BUTTONS
        val paintBtn = findViewById<ImageButton>(R.id.drawButton)
        paintBtn.setOnClickListener {
            val intent = Intent(this, PaintActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }



}