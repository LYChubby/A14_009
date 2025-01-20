package com.example.villaapps.ui.view.pages.villaview

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.navigation.DestinasiNavigasi
import com.example.villaapps.ui.customwidget.CostumeTopAppBar
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.DaftarVillaUiState
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.DaftarVillaViewModel

object DestinasiVilla : DestinasiNavigasi {
    override val route = "daftar_villa"
    override val titleRes = "Daftar Villa"
}

@Composable
fun DaftarVillaCard(
    daftarVilla: DaftarVilla,
    modifier: Modifier = Modifier,
    onDeleteClick: (DaftarVilla) -> Unit,
) {
    Card (
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = daftarVilla.namaVilla,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { onDeleteClick(daftarVilla) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = daftarVilla.kamarTersedia.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = daftarVilla.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun DaftarVillaLayout(
    daftarVilla: List<DaftarVilla>,
    modifier: Modifier = Modifier,
    onDetailClick: (DaftarVilla) -> Unit,
    onDeleteClick: (DaftarVilla) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(daftarVilla) { villa ->
            DaftarVillaCard(
                daftarVilla = villa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(villa) },
                onDeleteClick = {
                    onDeleteClick(villa)
                }
            )
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Text(text = "Loading...")
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Loading Failed. Retry?",
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = retryAction
        ) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun DaftarVillaStatus(
    daftarVillaUiState: DaftarVillaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (DaftarVilla) -> Unit = {},
){
    when (daftarVillaUiState) {
        is DaftarVillaUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DaftarVillaUiState.Success ->
            if (daftarVillaUiState.daftarVilla.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak Ada Data Villa")
                }
            } else {
                DaftarVillaLayout(
                    daftarVilla = daftarVillaUiState.daftarVilla,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idVilla.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is DaftarVillaUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarVillaScreen(
    navigateToitemEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: DaftarVillaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiVilla.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToitemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Villa")
            }
        },
    ){ innerPadding ->
        DaftarVillaStatus(
            daftarVillaUiState = viewModel.daftarVillaUiState,
            retryAction = { viewModel.getDaftarVilla() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deleteDaftarVilla(it.idVilla)
                viewModel.getDaftarVilla()
            }
        )
    }
}