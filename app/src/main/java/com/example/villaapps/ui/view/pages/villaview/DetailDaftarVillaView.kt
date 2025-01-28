package com.example.villaapps.ui.view.pages.villaview

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Villa
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.model.Review
import com.example.villaapps.navigation.DestinasiNavigasi
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.customwidget.CostumeTopAppBar
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.DetailDaftarVillaUiState
import com.example.villaapps.ui.view.viewmodel.villaviewmodel.DetailDaftarVillaViewModel

object DestinasiDetailVilla: DestinasiNavigasi {
    override val route = "detail_Villa"
    override val titleRes = "Detail Villa"
    const val IDVILLA = "idVilla"
    val routeWithArgs = "$route/{$IDVILLA}"
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Data", color = Color.Red) },
        text = { Text("Apakah Anda Yakin Ingin Menghapus Data Ini?") },
        modifier = modifier,
        confirmButton = {
            TextButton(
                onClick = onDeleteConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDeleteCancel
            ) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
fun ItemDetailVilla(
    modifier: Modifier = Modifier,
    daftarVilla: DaftarVilla
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color(0xFFF0F0F0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Villa,
                contentDescription = "Villa Image",
                modifier = Modifier.size(120.dp),
                tint = Color.Gray
            )
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = daftarVilla.namaVilla,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth()
            )

            AssistChip(
                onClick = {},
                label = {
                    Text(
                        "${daftarVilla.kamarTersedia} Kamar Tersedia",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Hotel,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color(0xFFF0F0F0),
                    labelColor = Color.Black
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = daftarVilla.alamat,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ReviewCard(
    review: Review,
    namaPelanggan: String,
    modifier: Modifier = Modifier,
    onDeleteClick: (Review) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Default.RateReview,
                    contentDescription = "Review",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Gray
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = namaPelanggan,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Grade,
                            contentDescription = "Phone",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = review.nilai,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
                IconButton(
                    onClick = { onDeleteClick(review) },
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            Color(0xFFFF5252).copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Review",
                        tint = Color(0xFFFF5252)
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewLayout(
    review: List<Review>,
    namaPelanggans: List<Review>,
    modifier: Modifier = Modifier,
    onDetailClick: (Review) -> Unit,
    onDeleteClick: (Review) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(review) { reviewList ->
            ReviewCard(
                review = reviewList,
                namaPelanggan = (namaPelanggans[reviewList.idReservasi] ?: "Unknown Customer").toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(reviewList) },
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun BodyDetailVilla(
    modifier: Modifier = Modifier,
    detailDaftarVillaUiState: DetailDaftarVillaUiState,
    onDeleteClick: () -> Unit = { },
    onReservasiClick: () -> Unit,
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    when (detailDaftarVillaUiState) {
        is DetailDaftarVillaUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF2196F3),
                    strokeWidth = 3.dp
                )
            }
        }
        is DetailDaftarVillaUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
            ) {
                ItemDetailVilla(
                    daftarVilla = detailDaftarVillaUiState.daftarVilla,
                    modifier = Modifier
                )

                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5252)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Hapus Villa",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onReservasiClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Reservasi Villa",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
//                ReviewLayout(
//                    review = detailDaftarVillaUiState.reviews,
//                    namaPelanggans = detailDaftarVillaUiState.reviews,
//                    modifier = Modifier,
//                    onDetailClick = { /* Handle detail click */ },
//                    onDeleteClick = { /* Handle delete click */ }
//                )

                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false }
                    )
                }
            }
        }
        is DetailDaftarVillaUiState.Error -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data Tidak Ditemukan",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFFF5252)
                )
            }
        }
    }
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailVillaView(
    idVilla: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailDaftarVillaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit = { },
    onReservasiClick: () -> Unit,
    onDeleteClick: () -> Unit = { }
) {

    LaunchedEffect(idVilla) {
        viewModel.getDaftarVillaWithReviews()
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Detail Villa",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idVilla.toString()) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFF2196F3)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Villa", tint = Color.White)
            }
        }
    ) { innerPadding ->
        BodyDetailVilla(
            modifier = modifier.padding(innerPadding),
            detailDaftarVillaUiState = viewModel.detailDaftarVillaUiState,
            onReservasiClick = onReservasiClick,
            onDeleteClick = {
                viewModel.deleteDaftarVilla(idVilla)
                onDeleteClick()
            }
        )
    }
}