package com.example.villaapps.ui.view.pages.reservasiview

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        DynamicSelectedView(
            selectedValue = daftarVilla.firstOrNull { it.first == insertReservasiUiEvent.idVilla }?.second ?: "Pilih Villa",
            options = daftarVilla.map { it.second },
            label = "Pilih Villa",
            onValueChangedEvent = { selectedVilla ->
                val idVilla = daftarVilla.firstOrNull { it.second == selectedVilla }?.first
                if (idVilla == null) {
                    Log.d("Debug", "Villa dengan nama $selectedVilla tidak ditemukan dalam daftar.")
                } else {
                    Log.d("Debug", "Villa yang dipilih: $selectedVilla dengan ID: $idVilla")
                }
                idVilla?.let {
                    onValueChange(insertReservasiUiEvent.copy(idVilla = it))
                    onVillaSelected(it)
                }
            }
        )

        DynamicSelectedView(
            selectedValue = daftarPelanggan.firstOrNull { it.first == insertReservasiUiEvent.idPelanggan }?.second ?: "Pilih Pelanggan",
            options = daftarPelanggan.map { it.second },
            label = "Nama Pelanggan",
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
            label = { Text(text = "Check In") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertReservasiUiEvent.checkOut,
            onValueChange = { onValueChange(insertReservasiUiEvent.copy(checkOut = it)) },
            label = { Text(text = "Check Out") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertReservasiUiEvent.jumlahKamar.toString(),
            onValueChange = { val newValue = it.toIntOrNull() ?: 0
                onValueChange(insertReservasiUiEvent.copy(jumlahKamar = newValue)) },
            label = { Text(text = "Jumlah Kamar") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
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
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = "Simpan")
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