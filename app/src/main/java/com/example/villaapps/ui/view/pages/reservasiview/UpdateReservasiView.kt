package com.example.villaapps.ui.view.pages.reservasiview

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villaapps.navigation.DestinasiNavigasi
import com.example.villaapps.ui.customwidget.CostumeTopAppBar
import com.example.villaapps.ui.customwidget.DynamicSelectedView
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.UpdateReservasiUiEvent
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.UpdateReservasiUiState
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.UpdateReservasiViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateReservasi: DestinasiNavigasi {
    override val route = "update_reservasi"
    override val titleRes = "Update Reservasi"
    const val IDRESERVASI = "idReservasi"
    val routeWithArgs = "$route/{$IDRESERVASI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateFormInputReservasi(
    updateReservasiUiEvent: UpdateReservasiUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateReservasiUiEvent) -> Unit = {},
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
            selectedValue = updateReservasiUiEvent.idVilla.toString(),
            options = daftarVilla.map { it.second },
            label = "Pilih Villa",
            onValueChangedEvent = { selectedVilla ->
                val idVilla = daftarVilla.firstOrNull { it.second == selectedVilla }?.first
                idVilla?.let {
                    onValueChange(updateReservasiUiEvent.copy(idVilla = it))
                    onVillaSelected(it)
                }
            }
        )

        DynamicSelectedView(
            selectedValue = updateReservasiUiEvent.idPelanggan.toString(),
            options = daftarPelanggan.map { it.second },
            label = "Nama Pelanggan",
            onValueChangedEvent = { selectedPelanggan ->
                val idPelanggan = daftarPelanggan.firstOrNull { it.second == selectedPelanggan }?.first
                idPelanggan?.let {
                    onValueChange(updateReservasiUiEvent.copy(idPelanggan = it))
                    onPelangganSelected(it)
                }
            }
        )

        OutlinedTextField(
            value = updateReservasiUiEvent.checkIn,
            onValueChange = { onValueChange(updateReservasiUiEvent.copy(checkIn = it)) },
            label = { Text(text = "Check In") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateReservasiUiEvent.checkOut,
            onValueChange = { onValueChange(updateReservasiUiEvent.copy(checkOut = it)) },
            label = { Text(text = "Check Out") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateReservasiUiEvent.jumlahKamar.toString(),
            onValueChange = { val newValue = it.toIntOrNull() ?: 0
                onValueChange(updateReservasiUiEvent.copy(jumlahKamar = newValue)) },
            label = { Text(text = "Jumlah Kamar") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}

@Composable
fun UpdateBodyReservasi(
    updateReservasiUiState: UpdateReservasiUiState,
    onValueChange: (UpdateReservasiUiEvent) -> Unit,
    onUpdateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(26.dp)
    ) {
        UpdateFormInputReservasi(
            updateReservasiUiEvent = updateReservasiUiState.updateReservasiUiEvent,
            onValueChange = onValueChange,
            daftarVilla = updateReservasiUiState.daftarVilla,
            daftarPelanggan = updateReservasiUiState.daftarPelanggan,
            onVillaSelected = { idVilla ->
                onValueChange(updateReservasiUiState.updateReservasiUiEvent.copy(idVilla = idVilla))
            },
            onPelangganSelected = { idPelanggan ->
                onValueChange(updateReservasiUiState.updateReservasiUiEvent.copy(idPelanggan = idPelanggan))
            },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onUpdateClick,
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = "Update")
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateReservasiScreen(
    idReservasi: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateReservasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idReservasi) {
        viewModel.loadReservasi(idReservasi)
    }

    val updateUiState = viewModel.UpdateReservasiUiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateReservasi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBodyReservasi(
            updateReservasiUiState = updateUiState,
            onValueChange = viewModel::updateReservasiState,
            onUpdateClick = {
                coroutineScope.launch {
                    viewModel.updateReservasi()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}