package com.example.villaapps.ui.view.pages.villaview

import android.os.Build
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
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.InsertDaftarVillaUiEvent
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.InsertDaftarVillaUiState
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.InsertDaftarVillaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertVilla: DestinasiNavigasi {
    override val route = "Item_Entry"
    override val titleRes = "Tambah Villa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputVilla(
    insertDaftarVillaUiEvent: InsertDaftarVillaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertDaftarVillaUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertDaftarVillaUiEvent.namaVilla,
            onValueChange = { onValueChange(insertDaftarVillaUiEvent.copy(namaVilla = it)) },
            label = { Text(text = "Nama Villa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertDaftarVillaUiEvent.alamat,
            onValueChange = { onValueChange(insertDaftarVillaUiEvent.copy(alamat = it)) },
            label = { Text(text = "Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertDaftarVillaUiEvent.kamarTersedia.toString(),
            onValueChange = { val newValue = it.toIntOrNull() ?: 0
                onValueChange(insertDaftarVillaUiEvent.copy(kamarTersedia = newValue)) },
            label = { Text(text = "Kamar Tersedia") },
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
fun EntryBodyVilla(
    insertDaftarVillaUiState: InsertDaftarVillaUiState,
    onSiswaValueChange: (InsertDaftarVillaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(26.dp)
    ) {
        FormInputVilla(
            insertDaftarVillaUiEvent = insertDaftarVillaUiState.insertDaftarVillaUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
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
fun EntryVillaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertDaftarVillaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertVilla.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {
            innerPadding ->
        EntryBodyVilla(
            insertDaftarVillaUiState = viewModel.insertDaftarVillaUiState,
            onSiswaValueChange = viewModel::updateInsertDaftarVillaUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertDaftarVilla()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}