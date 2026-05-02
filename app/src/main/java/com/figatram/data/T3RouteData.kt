package com.figatram.data

import com.google.android.gms.maps.model.LatLng

object T3RouteData {
    val stops = listOf(
        Stop("Müze", LatLng(36.8841, 30.6800)),
        Stop("Eğitim Araştırma Hastanesi", LatLng(36.8860, 30.6850)),
        Stop("Sigorta", LatLng(36.8880, 30.6900)),
        Stop("Şarampol", LatLng(36.8900, 30.6950)),
        Stop("Muratpaşa", LatLng(36.8920, 30.7000)),
        Stop("İsmetpaşa", LatLng(36.8900, 30.7050)),
        Stop("Doğu Garajı", LatLng(36.8880, 30.7100)),
        Stop("Burhanettin Onat", LatLng(36.8860, 30.7150)),
        Stop("Meydan", LatLng(36.8840, 30.7200)),
        Stop("Kışla", LatLng(36.8860, 30.7250)),
        Stop("Topçular", LatLng(36.8880, 30.7300)),
        Stop("Demokrasi", LatLng(36.8900, 30.7350)),
        Stop("Cırnık", LatLng(36.8920, 30.7400)),
        Stop("Altınova", LatLng(36.8940, 30.7450)),
        Stop("Yenigöl", LatLng(36.8960, 30.7500)),
        Stop("Sinan", LatLng(36.8980, 30.7550)),
        Stop("Yonca Kavşak", LatLng(36.9000, 30.7600)),
        Stop("Havalimanı", LatLng(36.9020, 30.7650))
    )

    // Dummy polyline connecting the stops
    val routePolyline = stops.map { it.location }
}

data class Stop(
    val name: String,
    val location: LatLng
)
