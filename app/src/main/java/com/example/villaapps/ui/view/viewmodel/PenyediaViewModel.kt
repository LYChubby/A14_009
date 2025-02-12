package com.example.villaapps.ui.view.viewmodel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.villaapps.VillaApplications
import com.example.villaapps.model.Reservasi
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.DetailPelangganViewModel
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.InsertPelangganViewModel
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.PelangganViewModel
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.UpdatePelangganViewModel
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.DetailReservasiViewModel
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.InsertReservasiViewModel
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.ReservasiViewModel
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.UpdateReservasiViewModel
import com.example.villaapps.ui.view.viewmodel.reviewviewmodel.DetailReviewViewModel
import com.example.villaapps.ui.view.viewmodel.reviewviewmodel.InsertReviewViewModel
import com.example.villaapps.ui.view.viewmodel.reviewviewmodel.ReviewViewModel
import com.example.villaapps.ui.view.viewmodel.reviewviewmodel.UpdateReviewViewModel
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.DaftarVillaViewModel
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.DetailDaftarVillaViewModel
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.InsertDaftarVillaViewModel
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.UpdateDaftarVillaViewModel

object PenyediaViewModel {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)

    val Factory = viewModelFactory {
        initializer {
            DaftarVillaViewModel(aplikasiReservasi().villacontainer.daftarVillaRepository)
        }
        initializer {
            InsertDaftarVillaViewModel(aplikasiReservasi().villacontainer.daftarVillaRepository)
        }
        initializer {
            DetailDaftarVillaViewModel(
                createSavedStateHandle(),
                aplikasiReservasi().villacontainer.daftarVillaRepository,
                aplikasiReservasi().reservasicontainer.reviewRepository
            )
        }
        initializer {
            UpdateDaftarVillaViewModel(aplikasiReservasi().villacontainer.daftarVillaRepository)
        }


        initializer {
            PelangganViewModel(aplikasiReservasi().pelanggancontainer.pelangganRepository)
        }
        initializer {
            InsertPelangganViewModel(aplikasiReservasi().pelanggancontainer.pelangganRepository)
        }
        initializer {
            DetailPelangganViewModel(
                createSavedStateHandle(),
                aplikasiReservasi().pelanggancontainer.pelangganRepository
            )
        }
        initializer {
            UpdatePelangganViewModel(aplikasiReservasi().pelanggancontainer.pelangganRepository)
        }

        initializer {
            ReservasiViewModel(
                aplikasiReservasi().reservasicontainer.reservasiRepository,
                aplikasiReservasi().reservasicontainer.daftarVillaRepository,
                aplikasiReservasi().reservasicontainer.pelangganRepository
            )
        }
        initializer {
            InsertReservasiViewModel(
                aplikasiReservasi().reservasicontainer.reservasiRepository,
                aplikasiReservasi().reservasicontainer.daftarVillaRepository,
                aplikasiReservasi().reservasicontainer.pelangganRepository
            )
        }
        initializer {
            DetailReservasiViewModel(
                createSavedStateHandle(),
                aplikasiReservasi().reservasicontainer.reservasiRepository,
                aplikasiReservasi().reservasicontainer.daftarVillaRepository,
                aplikasiReservasi().reservasicontainer.pelangganRepository
            )
        }
        initializer {
            UpdateReservasiViewModel(aplikasiReservasi().reservasicontainer.reservasiRepository)
        }

        initializer {
            ReviewViewModel(
                aplikasiReservasi().reviewcontainer.reviewRepository,
                aplikasiReservasi().reservasicontainer.pelangganRepository,
                aplikasiReservasi().reservasicontainer.reservasiRepository
            )
        }
        initializer {
            InsertReviewViewModel(
                aplikasiReservasi().reviewcontainer.reviewRepository,
                aplikasiReservasi().reservasicontainer.pelangganRepository
            )
        }
        initializer {
            DetailReviewViewModel(
                createSavedStateHandle(),
                aplikasiReservasi().reviewcontainer.reviewRepository,
                aplikasiReservasi().reservasicontainer.pelangganRepository,
                aplikasiReservasi().reservasicontainer.reservasiRepository
            )
        }
        initializer {
            UpdateReviewViewModel(aplikasiReservasi().reviewcontainer.reviewRepository)
        }
    }
}

fun CreationExtras.aplikasiReservasi(): VillaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VillaApplications)