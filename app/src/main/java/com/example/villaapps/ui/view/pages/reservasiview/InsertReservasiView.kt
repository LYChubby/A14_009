package com.example.villaapps.ui.view.pages.reservasiview

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Villa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villaapps.navigation.DestinasiNavigasi
import com.example.villaapps.ui.customwidget.CostumeTopAppBar
import com.example.villaapps.ui.customwidget.DynamicSelectedView
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.InsertReservasiUiEvent
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.InsertReservasiUiState
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.InsertReservasiViewModel
import kotlinx.coroutines.launch

object DestinasiInsertReservasi: DestinasiNavigasi {
    override val route = "Entry_Reservasi"
    override val titleRes = "Reservasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputReservasi(
    insertReservasiUiEvent: InsertReservasiUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertReservasiUiEvent) -> Unit = {},
    enabled: Boolean = true,
    daftarVilla: List<Pair<Int, String>>,
    daftarPelanggan: List<Pair<Int, String>>,
    onVillaSelected: (Int) -> Unit,
    onPelangganSelected: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DynamicSelectedView(
            selectedValue = daftarVilla.firstOrNull { it.first == insertReservasiUiEvent.idVilla }?.second ?: "Pilih Villa",
            options = daftarVilla.map { it.second },
            label = "Pilih Villa",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Villa,
                    contentDescription = null,
                    tint = Color(0xFF2196F3)
                )
            },
            onValueChangedEvent = { selectedVilla ->
                val idVilla = daftarVilla.firstOrNull { it.second == selectedVilla }?.first
                idVilla?.let {
                    onValueChange(insertReservasiUiEvent.copy(idVilla = it))
                    onVillaSelected(it)
                }
            }
        )

        DynamicSelectedView(
            selectedValue = daftarPelanggan.firstOrNull { it.first == insertReservasiUiEvent.idPelanggan }?.second ?: "Pilih Pelanggan",
            options = daftarPelanggan.map { it.second },
            label = "Pilih Pelanggan",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50)
                )
            },
            onValueChangedEvent = { selectedPelanggan ->
                val idPelanggan = daftarPelanggan.firstOrNull { it.second == selectedPelanggan }?.first
                idPelanggan?.let {
                    onValueChange(insertReservasiUiEvent.copy(idPelanggan = it))
                    onPelangganSelected(it)
                }
            }
        )

        OutlinedTextField(
            value = insertReservasiUiEvent.checkIn,
            onValueChange = { onValueChange(insertReservasiUiEvent.copy(checkIn = it)) },
            label = { Text("Tanggal Check-In") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = Color(0xFF2196F3)
                )
            }
        )

        OutlinedTextField(
            value = insertReservasiUiEvent.checkOut,
            onValueChange = { onValueChange(insertReservasiUiEvent.copy(checkOut = it)) },
            label = { Text("Tanggal Check-Out") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = Color(0xFF2196F3)
                )
            }
        )

        OutlinedTextField(
            value = insertReservasiUiEvent.jumlahKamar.toString(),
            onValueChange = {
                val newValue = it.toIntOrNull() ?: 0
                onValueChange(insertReservasiUiEvent.copy(jumlahKamar = newValue))
            },
            label = { Text("Jumlah Kamar") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Hotel,
                    contentDescription = null,
                    tint = Color(0xFFFF9800)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (enabled) {
            Text(
                text = "Isi Semua Data !!!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}


@Composable
fun EntryBodyReservasi(
    insertReservasiUiState: InsertReservasiUiState,
    onReservasiValueChange: (InsertReservasiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(26.dp)
    ) {
        FormInputReservasi(
            insertReservasiUiEvent = insertReservasiUiState.insertReservasiUiEvent,
            onValueChange = onReservasiValueChange,
            modifier = Modifier.fillMaxWidth(),
            daftarVilla = insertReservasiUiState.daftarVilla,
            daftarPelanggan = insertReservasiUiState.daftarPelanggan,
            onVillaSelected = { idVilla ->
                onReservasiValueChange(insertReservasiUiState.insertReservasiUiEvent.copy(idVilla = idVilla))
            },
            onPelangganSelected = { idPelanggan ->
                onReservasiValueChange(insertReservasiUiState.insertReservasiUiEvent.copy(idPelanggan = idPelanggan))
            }
        )
        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Simpan Reservasi",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryReservasiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertReservasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertReservasi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {
            innerPadding ->
        EntryBodyReservasi(
            insertReservasiUiState = viewModel.insertReservasiUiState,
            onReservasiValueChange = viewModel::updateInsertReservasiUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertReservasi()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}