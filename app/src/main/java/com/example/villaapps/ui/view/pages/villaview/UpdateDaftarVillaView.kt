package com.example.villaapps.ui.view.pages.villaview

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Villa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
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
    override val route = "Update_Villa"
    override val titleRes = "Update Villa"
    const val IDVILLA = "idVilla"
    val routeWithArgs = "$route/{$IDVILLA}"
}

@OptIn(ExperimentalMaterial3Api::class)
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
        TextField(
            value = updateDaftarVillaUiEvent.namaVilla,
            onValueChange = { onValueChange(updateDaftarVillaUiEvent.copy(namaVilla = it)) },
            label = { Text("Nama Villa") },
            enabled = enabled,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Villa,
                    contentDescription = "Villa",
                    tint = Color(0xFF2196F3)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color(0xFF2196F3),
                unfocusedIndicatorColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp)
        )

        TextField(
            value = updateDaftarVillaUiEvent.alamat,
            onValueChange = { onValueChange(updateDaftarVillaUiEvent.copy(alamat = it)) },
            label = { Text("Alamat") },
            enabled = enabled,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Alamat",
                    tint = Color(0xFF4CAF50)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color(0xFF2196F3),
                unfocusedIndicatorColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp)
        )

        TextField(
            value = updateDaftarVillaUiEvent.kamarTersedia.toString(),
            onValueChange = {
                val newValue = it.toIntOrNull() ?: 0
                onValueChange(updateDaftarVillaUiEvent.copy(kamarTersedia = newValue))
            },
            label = { Text("Kamar Tersedia") },
            enabled = enabled,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Hotel,
                    contentDescription = "Kamar",
                    tint = Color(0xFFFF9800)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color(0xFF2196F3),
                unfocusedIndicatorColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp)
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
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        UpdateFormVillaInput(
            updateDaftarVillaUiEvent = updateDaftarVillaUiState.updateDaftarVillaUiEvent,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onUpdateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Update Villa",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateVillaScreen(
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

