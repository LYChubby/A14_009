package com.example.villaapps.ui.view.pages.pelangganview

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.UpdatePelangganUiEvent
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.UpdatePelangganUiState
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.UpdatePelangganViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePelanggan: DestinasiNavigasi {
    override val route = "update_Pelangan"
    override val titleRes = "Update Pelanggan"
    const val IDPELANGGAN = "idPelanggan"
    val routeWithArgs = "$route/{$IDPELANGGAN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateFormInputPelanggan(
    updatePelangganUiEvent: UpdatePelangganUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePelangganUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = updatePelangganUiEvent.namaPelanggan,
            onValueChange = { onValueChange(updatePelangganUiEvent.copy(namaPelanggan = it)) },
            label = { Text("Nama") },
            enabled = enabled,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Nama Pelanggan",
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
            value = updatePelangganUiEvent.noHp,
            onValueChange = { onValueChange(updatePelangganUiEvent.copy(noHp = it)) },
            label = { Text("No Hp") },
            enabled = enabled,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "No Hp",
                    tint = Color(0xFF4CAF50)
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
fun UpdateBodyPelanggan(
    updatePelangganUiState: UpdatePelangganUiState,
    onValueChange: (UpdatePelangganUiEvent) -> Unit,
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
        UpdateFormInputPelanggan(
            updatePelangganUiEvent = updatePelangganUiState.updatePelangganUiEvent,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                text = "Update Pelanggan",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePelangganScreen(
    idPelanggan: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePelangganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idPelanggan) {
        viewModel.loadPelanggan(idPelanggan)
    }

    val updateUiState = viewModel.UpdatePelangganUiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePelanggan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBodyPelanggan(
            updatePelangganUiState = updateUiState,
            onValueChange = viewModel::updateUpdatePelangganState,
            onUpdateClick = {
                coroutineScope.launch {
                    viewModel.updatePelanggan()
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