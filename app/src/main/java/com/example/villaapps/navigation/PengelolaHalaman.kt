package com.example.villaapps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.villaapps.ui.view.pages.DestinasiHome
import com.example.villaapps.ui.view.pages.HomeView
import com.example.villaapps.ui.view.pages.Villa.DaftarVillaView
import com.example.villaapps.ui.view.pages.Villa.DestinasiVilla

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController(),
                     modifier: Modifier = Modifier) {

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
            DaftarVillaView(
            )
        }
    }
}