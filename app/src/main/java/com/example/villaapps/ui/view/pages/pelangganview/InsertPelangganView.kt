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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        modifier = modifier.background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = insertPelangganUiEvent.namaPelanggan,
            onValueChange = { onValueChange(insertPelangganUiEvent.copy(namaPelanggan = it)) },
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
            value = insertPelangganUiEvent.noHp,
            onValueChange = { onValueChange(insertPelangganUiEvent.copy(noHp = it)) },
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
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        FormInputPelanggan(
            insertPelangganUiEvent = insertPelangganUiState.insertPelangganUiEvent,
            onValueChange = onPelangganValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                text = "Simpan Pelanggan",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
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
    var showError by remember { mutableStateOf(false) }
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Tambah Pelanggan",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        if (showError) {
            AlertDialog(
                onDismissRequest = { showError = false },
                title = { Text("Error") },
                text = { Text("Semua data harus diisi!") },
                confirmButton = {
                    TextButton(onClick = { showError = false }) {
                        Text("OK")
                    }
                }
            )
        }

        EntryBodyPelanggan(
            insertPelangganUiState = viewModel.insertPelangganUiState,
            onPelangganValueChange = viewModel::updateInsertPelangganUiState,
            onSaveClick = {
                coroutineScope.launch {
                    val success = viewModel.insertPelanggan()
                    if (success) {
                        navigateBack()
                    } else {
                        showError = true
                    }
                }
            },
            modifier = Modifier.padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}