package com.example.villaapps.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.villaapps.ui.view.pages.DestinasiHome
import com.example.villaapps.ui.view.pages.HomeView
import com.example.villaapps.ui.view.pages.villaview.DaftarVillaScreen
import com.example.villaapps.ui.view.pages.villaview.DestinasiDetailVilla
import com.example.villaapps.ui.view.pages.villaview.DestinasiDetailVilla.IDVILLA
import com.example.villaapps.ui.view.pages.villaview.DestinasiInsertVilla
import com.example.villaapps.ui.view.pages.villaview.DestinasiUpdateVilla
import com.example.villaapps.ui.view.pages.villaview.DestinasiVilla
import com.example.villaapps.ui.view.pages.villaview.DetailVillaView
import com.example.villaapps.ui.view.pages.villaview.EntryVillaScreen
import com.example.villaapps.ui.view.pages.villaview.UpdateVillaScreen

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
                navigateToitemEntry = { navController.navigate(DestinasiInsertVilla.route) },
                navigateBack = { navController.popBackStack() },
                onDetailClick = { idVilla ->
                    navController.navigate("${DestinasiDetailVilla.route}/$idVilla")
                    println(
                        "PengelolaHalaman: ID Villa = $idVilla"
                    )
                }
            )
        }
        composable(DestinasiInsertVilla.route) {
            EntryVillaScreen(navigateBack = {
                navController.navigate(DestinasiVilla.route) {
                    popUpTo(DestinasiVilla.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            DestinasiDetailVilla.routeWithArgs,
            arguments = listOf(
                navArgument(IDVILLA) {
                    type = NavType.IntType
                }
            )
        ) {
            val idVilla = it.arguments?.getInt(IDVILLA)
            idVilla?.let { idVilla ->
                DetailVillaView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateVilla.route}/$it")
                    },
                    idVilla = idVilla,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiUpdateVilla.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateVilla.IDVILLA) {
                    type = NavType.IntType
                }
            )
        ) {
            val idVilla = it.arguments?.getInt(DestinasiUpdateVilla.IDVILLA)
            idVilla?.let { idVilla ->
                UpdateVillaScreen(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    idVilla = idVilla
                )
            }
        }
    }
}