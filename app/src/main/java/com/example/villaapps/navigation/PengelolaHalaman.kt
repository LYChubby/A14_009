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
import com.example.villaapps.ui.view.pages.pelangganview.DestinasiDetailPelanggan
import com.example.villaapps.ui.view.pages.pelangganview.DestinasiInsertPelanggan
import com.example.villaapps.ui.view.pages.pelangganview.DestinasiPelanggan
import com.example.villaapps.ui.view.pages.pelangganview.DestinasiUpdatePelanggan
import com.example.villaapps.ui.view.pages.pelangganview.DetailPelangganView
import com.example.villaapps.ui.view.pages.pelangganview.EntryPelangganScreen
import com.example.villaapps.ui.view.pages.pelangganview.PelangganScreen
import com.example.villaapps.ui.view.pages.pelangganview.UpdatePelangganScreen
import com.example.villaapps.ui.view.pages.reservasiview.DestinasiDetailReservasi
import com.example.villaapps.ui.view.pages.reservasiview.DestinasiInsertReservasi
import com.example.villaapps.ui.view.pages.reservasiview.DestinasiReservasi
import com.example.villaapps.ui.view.pages.reservasiview.DestinasiUpdateReservasi
import com.example.villaapps.ui.view.pages.reservasiview.DetailReservasiView
import com.example.villaapps.ui.view.pages.reservasiview.EntryReservasiScreen
import com.example.villaapps.ui.view.pages.reservasiview.ReservasiScreen
import com.example.villaapps.ui.view.pages.reservasiview.UpdateReservasiScreen
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
                onDaftarPelangganClick = { navController.navigate(DestinasiPelanggan.route) },
                onDaftarReviewClick = {  },
                onDaftarReservasiClick = { navController.navigate(DestinasiReservasi.route) }
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

        composable(DestinasiPelanggan.route) {
            PelangganScreen(
                navigateToitemEntry = { navController.navigate(DestinasiInsertPelanggan.route) },
                navigateBack = { navController.popBackStack() },
                onDetailClick = { idPelanggan ->
                    navController.navigate("${DestinasiDetailPelanggan.route}/$idPelanggan")
                    println(
                        "PengelolaHalaman: ID Pelanggan = $idPelanggan"
                    )
                }
            )
        }
        composable(DestinasiInsertPelanggan.route) {
            EntryPelangganScreen(navigateBack = {
                navController.navigate(DestinasiPelanggan.route) {
                    popUpTo(DestinasiPelanggan.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            DestinasiDetailPelanggan.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPelanggan.IDPELANGGAN) {
                    type = NavType.IntType
                }
            )
        ) {
            val idPelanggan = it.arguments?.getInt(DestinasiDetailPelanggan.IDPELANGGAN)
            idPelanggan?.let { idPelanggan ->
                DetailPelangganView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdatePelanggan.route}/$it")
                    },
                    idPelanggan = idPelanggan,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiUpdatePelanggan.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdatePelanggan.IDPELANGGAN) {
                    type = NavType.IntType
                }
            )
        ) {
            val idPelanggan = it.arguments?.getInt(DestinasiUpdatePelanggan.IDPELANGGAN)
            idPelanggan?.let { idPelanggan ->
                UpdatePelangganScreen(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    idPelanggan = idPelanggan
                )
            }
        }

        composable(DestinasiReservasi.route) {
            ReservasiScreen(
                navigateToitemEntry = { navController.navigate(DestinasiInsertReservasi.route) },
                navigateBack = { navController.popBackStack() },
                onDetailClick = { idReservasi ->
                    navController.navigate("${DestinasiDetailReservasi.route}/$idReservasi")
                    println(
                        "PengelolaHalaman: ID Reservasi = $idReservasi"
                    )
                }
            )
        }
        composable(DestinasiInsertReservasi.route) {
            EntryReservasiScreen(navigateBack = {
                navController.navigate(DestinasiReservasi.route) {
                    popUpTo(DestinasiReservasi.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            DestinasiDetailReservasi.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailReservasi.IDRESERVASI) {
                    type = NavType.IntType
                }
            )
        ) {
            val idReservasi = it.arguments?.getInt(DestinasiDetailReservasi.IDRESERVASI)
            idReservasi?.let { idReservasi ->
                DetailReservasiView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateReservasi.route}/$it")
                    },
                    idReservasi = idReservasi,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiUpdateReservasi.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateReservasi.IDRESERVASI) {
                    type = NavType.IntType
                }
            )
        ) {
            val idReservasi = it.arguments?.getInt(DestinasiUpdateReservasi.IDRESERVASI)
            idReservasi?.let { idReservasi ->
                UpdateReservasiScreen(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    idReservasi = idReservasi
                )
            }
        }
    }
}