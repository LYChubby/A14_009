package com.example.villaapps.ui.view.viewmodel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.villaapps.VillaApplications
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.DaftarVillaViewModel
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.DetailDaftarVillaViewModel
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.InsertDaftarVillaViewModel

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
                aplikasiReservasi().villacontainer.daftarVillaRepository
            )
        }
    }
}

fun CreationExtras.aplikasiReservasi(): VillaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VillaApplications)