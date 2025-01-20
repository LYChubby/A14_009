package com.example.villaapps

import android.app.Application
import com.example.villaapps.container.AppPelangganContainer
import com.example.villaapps.container.AppReservasiContainer
import com.example.villaapps.container.AppReviewContainer
import com.example.villaapps.container.AppVillaContainer
import com.example.villaapps.container.PelangganContainer
import com.example.villaapps.container.ReservasiContainer
import com.example.villaapps.container.ReviewContainer
import com.example.villaapps.container.VillaContainer

class VillaApplications: Application() {
    lateinit var villacontainer: AppVillaContainer
    lateinit var pelanggancontainer: AppPelangganContainer
    lateinit var reservasicontainer: AppReservasiContainer
    lateinit var reviewcontainer: AppReviewContainer
    override fun onCreate() {
        super.onCreate()
        villacontainer = VillaContainer()
        pelanggancontainer = PelangganContainer()
        reservasicontainer = ReservasiContainer()
        reviewcontainer = ReviewContainer()
    }
}