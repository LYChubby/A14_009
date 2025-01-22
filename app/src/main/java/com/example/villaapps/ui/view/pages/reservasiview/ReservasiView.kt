package com.example.villaapps.ui.view.pages.reservasiview

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.model.Pelanggan
import com.example.villaapps.model.Reservasi
import com.example.villaapps.navigation.DestinasiNavigasi
import com.example.villaapps.ui.customwidget.CostumeTopAppBar
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.ReservasiUiState
import com.example.villaapps.ui.view.viewmodel.reservasiviewmodel.ReservasiViewModel

object DestinasiReservasi : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Mhs"
}

@Composable
fun ReservasiCard(
    reservasi: Reservasi,
    pelanggan: Pelanggan,
    daftarVilla: DaftarVilla,
    modifier: Modifier = Modifier,
    onDeleteClick: (Reservasi) -> Unit = {}
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
                    onClick = { onDeleteClick(reservasi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = reservasi.jumlahKamar.toString() + " kamar",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = daftarVilla.namaVilla,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ReservasiLayout(
    reservasi: List<Reservasi>,
    pelangganMap: Map<Int, Pelanggan>,
    villaMap: Map<Int, DaftarVilla>,
    modifier: Modifier = Modifier,
    onDetailClick: (Reservasi) -> Unit,
    onDeleteClick: (Reservasi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(reservasi) { reservasi ->
            val pelanggan = pelangganMap[reservasi.idPelanggan]
            val villa = villaMap[reservasi.idVilla]

            if (pelanggan != null && villa != null) {
                ReservasiCard(
                    reservasi = reservasi,
                    pelanggan = pelanggan,
                    daftarVilla = villa,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDetailClick(reservasi) },
                    onDeleteClick = {
                        onDeleteClick(reservasi)
                    }
                )
            }
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
            text = "Loading Failed. Retry ?",
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = retryAction
        ) {
            Text("Retry")
        }
    }
}

@Composable
fun ReservasiStatus(
    reservasiUiState: ReservasiUiState,
    pelangganMap: Map<Int, Pelanggan>,
    villaMap: Map<Int, DaftarVilla>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Reservasi) -> Unit = {},
){
    when (reservasiUiState) {
        is ReservasiUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is ReservasiUiState.Success ->
            if (reservasiUiState.resrvasi.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kontak")
                }
            } else {
                ReservasiLayout(
                    reservasi = reservasiUiState.resrvasi,
                    pelangganMap = pelangganMap,
                    villaMap = villaMap,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idReservasi.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is ReservasiUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservasiScreen(
    navigateToitemEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: ReservasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pelangganMap by viewModel.pelangganMap.collectAsState()
    val villaMap by viewModel.villaMap.collectAsState()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiReservasi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getReservasi()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToitemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Reservasi")
            }
        },
    ){ innerPadding ->
        ReservasiStatus(
            reservasiUiState = viewModel.reservasiUiState,
            pelangganMap = pelangganMap,
            villaMap = villaMap,
            retryAction = { viewModel.getReservasi() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deleteReservasi(it.idReservasi)
                viewModel.getReservasi()
            }
        )
    }
}