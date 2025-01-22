package com.example.villaapps.ui.view.pages.reservasiview

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.model.Pelanggan
import com.example.villaapps.model.Reservasi
import com.example.villaapps.navigation.DestinasiNavigasi
import com.example.villaapps.ui.customwidget.CostumeTopAppBar
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.DetailReservasiUiState
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.DetailReservasiViewModel

object DestinasiDetailReservasi: DestinasiNavigasi {
    override val route = "detail_reservasi"
    override val titleRes = "Detail Reservasi"
    const val IDRESERVASI = "idReservasi"
    val routeWithArgs = "$route/{$IDRESERVASI}"
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Data") },
        text = { Text("Apakah Anda Yakin Ingin Menghapus Data Ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}

@Composable
fun ComponentDetailReservasi(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun ItemDetailReservasi(
    modifier: Modifier = Modifier,
    reservasi: Reservasi,
    daftarVilla: DaftarVilla,
    pelanggan: Pelanggan
) {
    Card (
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ){
            ComponentDetailReservasi(judul = "Nama", isinya = pelanggan.namaPelanggan)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailReservasi(judul = "Villa", isinya = daftarVilla.namaVilla)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailReservasi(judul = "Check In", isinya = reservasi.checkIn)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailReservasi(judul = "Check Out", isinya = reservasi.checkOut)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailReservasi(judul = "Jumlah Kamar", isinya = reservasi.jumlahKamar.toString())
            Spacer(modifier = Modifier.padding(4.dp))

        }
    }
}

@Composable
fun BodyDetailMhs(
    modifier: Modifier = Modifier,
    detailReservasiUiState: DetailReservasiUiState,
    onDeleteClick: () -> Unit = { }
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    when (detailReservasiUiState) {
        is DetailReservasiUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DetailReservasiUiState.Success -> {
            Column(
                modifier = modifier.fillMaxWidth().padding(16.dp)
            ) {
                ItemDetailReservasi(
                    reservasi = detailReservasiUiState.reservasi,
                    daftarVilla = detailReservasiUiState.villa,
                    pelanggan = detailReservasiUiState.pelanggan,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }

                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        is DetailReservasiUiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data Tidak Ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailReservasiView(
    idReservasi: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailReservasiViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit = { },
    onDeleteClick: () -> Unit = { }
) {

    LaunchedEffect(idReservasi) {
        viewModel.getReservasiById()
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailReservasi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idReservasi.toString()) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa"
                )
            }
        }
    ) { innerPadding ->

        BodyDetailMhs(
            modifier = modifier.padding(innerPadding),
            detailReservasiUiState = viewModel.detailReservasiUiState,
            onDeleteClick = {
                viewModel.deleteReservasi(idReservasi)
                onDeleteClick()
            }
        )
    }
}