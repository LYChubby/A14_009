package com.example.villaapps.ui.view.pages.Villa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.villaapps.navigation.DestinasiNavigasi

object DestinasiVilla : DestinasiNavigasi {
    override val route = "daftar_villa"
    override val titleRes = "Daftar Villa"
}

@Composable
fun DaftarVillaView (
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Daftar Villa")
    }
}