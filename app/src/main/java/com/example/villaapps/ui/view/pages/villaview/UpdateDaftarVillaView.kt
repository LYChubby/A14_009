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
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.UpdateDaftarVillaUiEvent
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.UpdateDaftarVillaUiState
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.UpdateDaftarVillaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateVilla: DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Villa"
    const val IDVILLA = "idVilla"
    val routeWithArgs = "$route/{$IDVILLA}"
}

@Composable
fun UpdateFormVillaInput(
    updateDaftarVillaUiEvent: UpdateDaftarVillaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateDaftarVillaUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateDaftarVillaUiEvent.namaVilla,
            onValueChange = { onValueChange(updateDaftarVillaUiEvent.copy(namaVilla = it)) },
            label = { Text(text = "Nama Villa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateDaftarVillaUiEvent.alamat,
            onValueChange = { onValueChange(updateDaftarVillaUiEvent.copy(alamat = it)) },
            label = { Text(text = "Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateDaftarVillaUiEvent.kamarTersedia.toString(),
            onValueChange = { val newValue = it.toIntOrNull() ?: 0
                onValueChange(updateDaftarVillaUiEvent.copy(kamarTersedia = newValue)) },
            label = { Text(text = "Kamar Tersedia") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}

@Composable
fun UpdateBodyVilla(
    updateDaftarVillaUiState: UpdateDaftarVillaUiState,
    onValueChange: (UpdateDaftarVillaUiEvent) -> Unit,
    onUpdateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(26.dp)
    ) {
        UpdateFormVillaInput(
            updateDaftarVillaUiEvent = updateDaftarVillaUiState.updateDaftarVillaUiEvent,
            onValueChange = onValueChange,
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
fun UpdateMhsScreen(
    idVilla: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateDaftarVillaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idVilla) {
        viewModel.loadDaftarVilla(idVilla)
    }

    val updateUiState = viewModel.UpdateDaftarVillaUiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateVilla.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBodyVilla(
            updateDaftarVillaUiState = updateUiState,
            onValueChange = viewModel::UpdateDaftarVillaState,
            onUpdateClick = {
                coroutineScope.launch {
                    viewModel.updateDaftarVilla()
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

