package com.example.villaapps.ui.view.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.villaapps.navigation.DestinasiNavigasi

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Villa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    onDaftarVillaClick: () -> Unit,
    onDaftarPelangganClick: () -> Unit,
    onDaftarReviewClick: () -> Unit,
    onDaftarReservasiClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onDaftarVillaClick() },
            shape = MaterialTheme.shapes.medium,
//            elevation = 12.dp,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Daftar Villa",
//                    style = MaterialTheme.typography.h6.copy(
//                        color = MaterialTheme.colors.onSurface,
//                        fontWeight = FontWeight.Bold
//                    )
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onDaftarPelangganClick() },
            shape = MaterialTheme.shapes.medium,
//            elevation = 12.dp,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Daftar Pelanggan",
//                    style = MaterialTheme.typography.h6.copy(
//                        color = MaterialTheme.colors.onSurface,
//                        fontWeight = FontWeight.Bold
//                    )
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onDaftarReservasiClick() },
            shape = MaterialTheme.shapes.medium,
//            elevation = 12.dp,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Daftar Reservasi",
//                    style = MaterialTheme.typography.h6.copy(
//                        color = MaterialTheme.colors.onSurface,
//                        fontWeight = FontWeight.Bold
//                    )
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onDaftarReviewClick() },
            shape = MaterialTheme.shapes.medium,
//            elevation = 12.dp,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Daftar Review",
//                    style = MaterialTheme.typography.h6.copy(
//                        color = MaterialTheme.colors.onSurface,
//                        fontWeight = FontWeight.Bold
//                    )
                )
            }
        }
    }
}