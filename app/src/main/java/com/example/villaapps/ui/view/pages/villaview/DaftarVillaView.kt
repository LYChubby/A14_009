package com.example.villaapps.ui.view.pages.villaview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

