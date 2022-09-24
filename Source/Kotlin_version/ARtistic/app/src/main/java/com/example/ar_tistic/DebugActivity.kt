package com.example.ar_tistic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class DebugActivity : AppCompatActivity() {
    private lateinit var map:MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(
            applicationContext, PreferenceManager.getDefaultSharedPreferences(
                applicationContext
            )
        )

        // LOAD LAYOUT
        setContentView(R.layout.activity_debug)

        // MAP Initialization
        map = findViewById<MapView>(R.id.map_debug)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setBuiltInZoomControls(false)
        map.setMultiTouchControls(true)

        // MAP CONTROL SETTING
        val mapController: IMapController = map.getController()
        mapController.setZoom(15.0)
        //mapController.setCenter(IUT)
        //mapController.setCenter(loc.myLocation)

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
            applicationContext, items, object :
                ItemizedIconOverlay.OnItemGestureListener<OverlayItem?> {
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