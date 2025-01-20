package com.example.villaapps.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.villaapps.ui.view.pages.DestinasiHome
import com.example.villaapps.ui.view.pages.HomeView
import com.example.villaapps.ui.view.pages.villaview.DaftarVillaScreen
import com.example.villaapps.ui.view.pages.villaview.DestinasiVilla

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            HomeView(
                onDaftarVillaClick = { navController.navigate(DestinasiVilla.route) },
                onDaftarPelangganClick = {  },
                onDaftarReviewClick = {  },
                onDaftarReservasiClick = {  }
            )
        }
        composable(DestinasiVilla.route) {
            DaftarVillaScreen(
                navigateToitemEntry = {  },
                navigateBack = { navController.popBackStack() },
                onDetailClick = {  }
            )
        }
    }
}