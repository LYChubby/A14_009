package com.example.villaapps.ui.view.pages.villaview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.villaapps.model.DaftarVilla
import com.example.villaapps.navigation.DestinasiNavigasi

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