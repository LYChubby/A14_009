package com.example.villaapps.ui.view.pages.reservasiview

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Villa
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.villaapps.ui.view.pages.villaview.ItemDetailVilla
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.DetailReservasiUiState
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.DetailReservasiViewModel
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.DetailDaftarVillaUiState

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
        title = { Text("Delete Data", color = Color.Red) },
        text = { Text("Apakah Anda Yakin Ingin Menghapus Data Ini?") },
        modifier = modifier,
        confirmButton = {
            TextButton(
                onClick = onDeleteConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDeleteCancel
            ) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
fun ItemDetailReservasi(
    modifier: Modifier = Modifier,
    reservasi: Reservasi,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color(0xFFF0F0F0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Hotel,
                contentDescription = "Reservasi",
                modifier = Modifier.size(120.dp),
                tint = Color.Gray
            )
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Nama Pelanggan : ${reservasi.idPelanggan}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth()
            )

            AssistChip(
                onClick = {},
                label = {
                    Text(
                        "${reservasi.jumlahKamar} Kamar",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Hotel,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color(0xFFF0F0F0),
                    labelColor = Color.Black
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Check In",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Check In : ${reservasi.checkIn}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Check Out",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Check In : ${reservasi.checkOut}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Nama Villa : ${reservasi.idVilla}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun BodyDetailReservasi(
    modifier: Modifier = Modifier,
    detailReservasiUiState: DetailReservasiUiState,
    onDeleteClick: () -> Unit = { }
) {
    var deleteConfirmationReservasi by rememberSaveable { mutableStateOf(false) }

    when (detailReservasiUiState) {
        is DetailReservasiUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF2196F3),
                    strokeWidth = 3.dp
                )
            }
        }
        is DetailReservasiUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
            ) {
                ItemDetailReservasi(
                    reservasi = detailReservasiUiState.reservasi,
                    modifier = Modifier
                )

                Button(
                    onClick = { deleteConfirmationReservasi = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5252)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Hapus Reservasi",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (deleteConfirmationReservasi) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationReservasi = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationReservasi= false }
                    )
                }
            }
        }
        is DetailReservasiUiState.Error -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data Tidak Ditemukan",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFFF5252)
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
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFF2196F3)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Reservasi", tint = Color.White)
            }
        }
    ) { innerPadding ->

        BodyDetailReservasi(
            modifier = modifier.padding(innerPadding),
            detailReservasiUiState = viewModel.detailReservasiUiState,
            onDeleteClick = {
                viewModel.deleteReservasi(idReservasi)
                onDeleteClick()
            }
        )
    }
}