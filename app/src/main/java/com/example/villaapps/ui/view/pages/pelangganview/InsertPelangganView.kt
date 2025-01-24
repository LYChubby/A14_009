package com.example.villaapps.ui.view.pages.pelangganview

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villaapps.navigation.DestinasiNavigasi
import com.example.villaapps.ui.customwidget.CostumeTopAppBar
import com.example.villaapps.ui.view.pages.villaview.DestinasiInsertVilla
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.InsertPelangganUiEvent
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.InsertPelangganUiState
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.InsertPelangganViewModel
import kotlinx.coroutines.launch

object DestinasiInsertPelanggan: DestinasiNavigasi {
    override val route = "Entry_Pelanggan"
    override val titleRes = "Tambah Pelanggan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPelanggan(
    insertPelangganUiEvent: InsertPelangganUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPelangganUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertPelangganUiEvent.namaPelanggan,
            onValueChange = { onValueChange(insertPelangganUiEvent.copy(namaPelanggan = it)) },
            label = { Text(text = "Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPelangganUiEvent.noHp,
            onValueChange = { onValueChange(insertPelangganUiEvent.copy(noHp = it)) },
            label = { Text(text = "No Hp") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
fun EntryBodyPelanggan(
    insertPelangganUiState: InsertPelangganUiState,
    onPelangganValueChange: (InsertPelangganUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(26.dp)
    ) {
        FormInputPelanggan(
            insertPelangganUiEvent = insertPelangganUiState.insertPelangganUiEvent,
            onValueChange = onPelangganValueChange,
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
fun EntryPelangganScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPelangganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertPelanggan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {
            innerPadding ->
        EntryBodyPelanggan(
            insertPelangganUiState = viewModel.insertPelangganUiState,
            onPelangganValueChange = viewModel::updateInsertPelangganUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPelanggan()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}