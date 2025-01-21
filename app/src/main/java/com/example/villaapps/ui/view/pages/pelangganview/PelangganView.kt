package com.example.villaapps.ui.view.pages.pelangganview

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villaapps.model.Pelanggan
import com.example.villaapps.navigation.DestinasiNavigasi
import com.example.villaapps.ui.customwidget.CostumeTopAppBar
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.PelangganUiState
import com.example.villaapps.ui.view.viewmodel.pelangganviewmodel.PelangganViewModel

object DestinasiPelanggan : DestinasiNavigasi {
    override val route = "pelanggan"
    override val titleRes = "Daftar Pelanggan"
}

@Composable
fun PelangganCard(
    pelanggan: Pelanggan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pelanggan) -> Unit = {}
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
                    text = pelanggan.namaPelanggan,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { onDeleteClick(pelanggan) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
            Text(
                text = pelanggan.noHp,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun PelangganLayout(
    pelanggan: List<Pelanggan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pelanggan) -> Unit,
    onDeleteClick: (Pelanggan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pelanggan) { pelanggan ->
            PelangganCard(
                pelanggan = pelanggan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pelanggan) },
                onDeleteClick = {
                    onDeleteClick(pelanggan)
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
fun PelangganStatus(
    pelangganUiState: PelangganUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Pelanggan) -> Unit = {},
){
    when (pelangganUiState) {
        is PelangganUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is PelangganUiState.Success ->
            if (pelangganUiState.pelanggan.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kontak")
                }
            } else {
                PelangganLayout(
                    pelanggan = pelangganUiState.pelanggan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idPelanggan.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is PelangganUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PelangganScreen(
    navigateToitemEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: PelangganViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiPelanggan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPelanggan()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToitemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kontak")
            }
        },
    ){ innerPadding ->
        PelangganStatus(
            pelangganUiState = viewModel.pelangganUiState,
            retryAction = { viewModel.getPelanggan() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deletePelanggan(it.idPelanggan)
                viewModel.getPelanggan()
            }
        )
    }
}