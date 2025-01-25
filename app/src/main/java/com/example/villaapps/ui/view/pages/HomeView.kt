package com.example.villaapps.ui.view.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    onDaftarReservasiClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Villa Reservation",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        HomeMenuItem(
            icon = Icons.Default.Hotel,
            title = "Daftar Villa",
            color = Color(0xFF2196F3),
            backgroundColor = Color(0xFFE3F2FD), // Mengatur warna background
            onClick = onDaftarVillaClick
        )

        HomeMenuItem(
            icon = Icons.Default.Person,
            title = "Daftar Pelanggan",
            color = Color(0xFF4CAF50),
            backgroundColor = Color(0xFFE8F5E9), // Mengatur warna background
            onClick = onDaftarPelangganClick
        )

        HomeMenuItem(
            icon = Icons.Default.Book,
            title = "Daftar Reservasi",
            color = Color(0xFF9C27B0),
            backgroundColor = Color(0xFFF3E5F5), // Mengatur warna background
            onClick = onDaftarReservasiClick
        )

        HomeMenuItem(
            icon = Icons.Default.Reviews,
            title = "Daftar Review",
            color = Color(0xFFFF9800),
            backgroundColor = Color(0xFFFFF3E0), // Mengatur warna background
            onClick = onDaftarReviewClick
        )
    }
}

@Composable
fun HomeMenuItem(
    icon: ImageVector,
    title: String,
    color: Color,
    backgroundColor: Color, // Pastikan backgroundColor ditambahkan sebagai parameter
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
            )
            Text(
                text = title,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }
    }
}