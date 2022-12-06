package com.example.ar_tistic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.classlib.Manager
import com.example.stub.Stub
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

    lateinit var manager:Manager
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
        //Get manager
        //
        manager=intent.getSerializableExtra("manager") as Manager
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
            /*val intent = Intent(this, PaintActivity::class.java)
            startActivity(intent)*/
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        val profilBtn = findViewById<ImageButton>(R.id.profileButton)
        profilBtn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra("manager", manager)
            startActivity(intent)
        }
        val ratingBtn = findViewById<ImageButton>(R.id.leaderboardButton)
        ratingBtn.setOnClickListener {
            val intent = Intent(this, LeaderBoardActivity::class.java)
            intent.putExtra("manager", manager)
            startActivity(intent)
        }
        val mesageBtn = findViewById<ImageButton>(R.id.msgButton)
        mesageBtn.setOnClickListener {
            val intent = Intent(this, MessagesActivity::class.java)
            intent.putExtra("manager", manager)
            startActivity(intent)
        }
        val displayPopUp = findViewById<Button>(R.id.displayPopUp);
        displayPopUp.setOnClickListener {
            popUp();
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
    fun popUp() {

        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.activity_draw,null)
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"OK",Toast.LENGTH_LONG).show()
        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_LONG).show()
        }
        addDialog.create()
        addDialog.show()
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.activity_draw, null)
        val madeBy= dialogLayout.findViewById<TextView>(R.id.madeBy)

        /*with(addDialog)
        {
            madeBy.text="OUIII"
            show()
        }*/
        //addDialog.apply{madeBy.text="OUIII"}.show()

    }


}
