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
import com.example.villaapps.ui.view.pages.villaview.DestinasiInsertVilla
import com.example.villaapps.ui.view.pages.villaview.DestinasiVilla
import com.example.villaapps.ui.view.pages.villaview.DetailVillaView
import com.example.villaapps.ui.view.pages.villaview.EntryVillaScreen

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
                onDetailClick = { navController.navigate(DestinasiDetailVilla.route) }
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
                navArgument(DestinasiDetailVilla.IDVILLA) {
                    type = NavType.IntType
                }
            )
        ) {
            val idVilla = it.arguments?.getInt(DestinasiDetailVilla.IDVILLA)
            idVilla?.let { idVilla ->
                DetailVillaView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
//                        navController.navigate("${DestinasiUpdate.route}/$it")
                    },
                    idVilla = idVilla,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}