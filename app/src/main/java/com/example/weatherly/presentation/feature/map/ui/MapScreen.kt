package com.example.weatherly.presentation.feature.map.ui

import android.content.Context
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weatherly.R
import com.example.weatherly.presentation.feature.map.viewmodel.MapViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    navController: NavHostController,
    viewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    rememberCoroutineScope()

    val mapView = remember { MapView(context) }
    val marker = remember { Marker(mapView) }

    LaunchedEffect(Unit) {
        Configuration.getInstance()
            .load(context, context.getSharedPreferences("osm", Context.MODE_PRIVATE))
        Configuration.getInstance().userAgentValue = context.packageName
    }

    LaunchedEffect(viewModel.errorMessage) {
        viewModel.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.errorShown()
        }
    }

    LaunchedEffect(viewModel.selectedLocation) {
        viewModel.selectedLocation?.let { loc ->
            val point = GeoPoint(loc.lat, loc.lon)
            mapView.controller.animateTo(point)
            marker.position = point
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = loc.cityName

            if (!mapView.overlays.contains(marker)) {
                mapView.overlays.add(marker)
            }
            marker.showInfoWindow()
            mapView.invalidate()
        }

    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 100.dp, start = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black.copy(alpha = 0.7f))
                        .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(text = data.visuals.message, color = Color.White)
                }
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    mapView.apply {
                        val customInfoWindow =
                            object : MarkerInfoWindow(R.layout.custom_info_window, mapView) {
                                override fun onOpen(item: Any?) {
                                    val m = item as Marker
                                    val title = mView.findViewById<TextView>(R.id.bubble_title)
                                    val description =
                                        mView.findViewById<TextView>(R.id.bubble_description)

                                    title.text = m.title
                                    description.text = "Tap to confirm this location"

                                    mView.setOnClickListener {
                                        close()
                                    }
                                }
                            }
                        marker.infoWindow = customInfoWindow
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(12.0)
                        controller.setCenter(GeoPoint(30.0444, 31.2357))

                        val eventsReceiver = object : MapEventsReceiver {
                            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean = false
                            override fun longPressHelper(p: GeoPoint?): Boolean {
                                p?.let { viewModel.onMapLongPress(it.latitude, it.longitude) }
                                return true
                            }
                        }
                        overlays.add(MapEventsOverlay(eventsReceiver))
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            ) {
                MapSearchBar(
                    query = viewModel.searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChange(it) }
                )

                if (viewModel.searchResults.isNotEmpty()) {
                    SearchResultsDropdown(
                        results = viewModel.searchResults,
                        onSelect = { viewModel.onLocationSelected(it) }
                    )
                }
            }

            if (viewModel.selectedLocation != null) {
                Button(
                    onClick = {
                        navController.previousBackStackEntry?.savedStateHandle?.apply {
                            set("lat", viewModel.selectedLocation?.lat)
                            set("lon", viewModel.selectedLocation?.lon)
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 40.dp)
                        .fillMaxWidth(0.7f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9D53FF)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text(
                        text = "Confirm ${viewModel.selectedLocation?.cityName}",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}