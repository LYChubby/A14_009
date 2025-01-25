package com.example.villaapps.ui.view.pages.reviewview

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villaapps.navigation.DestinasiNavigasi
import com.example.villaapps.ui.customwidget.CostumeTopAppBar
import com.example.villaapps.ui.customwidget.DynamicSelectedView
import com.example.villaapps.ui.view.viewmodel.PenyediaViewModel
import com.example.villaapps.ui.view.viewmodel.reviewviewmodel.InsertReviewUiEvent
import com.example.villaapps.ui.view.viewmodel.reviewviewmodel.InsertReviewUiState
import com.example.villaapps.ui.view.viewmodel.reviewviewmodel.InsertReviewViewModel
import kotlinx.coroutines.launch

object DestinasiInsertReview: DestinasiNavigasi {
    override val route = "Item_Review"
    override val titleRes = "Tambah Review"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputReview(
    insertReviewUiEvent: InsertReviewUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertReviewUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DynamicSelectedView(
            selectedValue = insertReviewUiEvent.nilai,
            options = listOf("Sangat Puas", "Puas", "Biasa", "Tidak Puas", "Sangat Tidak Puas"),
            label = "Nilai Kepuasan",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Grade,
                    contentDescription = null,
                    tint = Color(0xFFFF9800)
                )
            },
            onValueChangedEvent = { selectedValue ->
                onValueChange(insertReviewUiEvent.copy(nilai = selectedValue))
            },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = insertReviewUiEvent.komentar,
            onValueChange = { onValueChange(insertReviewUiEvent.copy(komentar = it)) },
            label = { Text("Komentar") },
            enabled = enabled,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Comment,
                    contentDescription = "Komentar",
                    tint = Color(0xFFFF9800)
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

        if (enabled) {
            Text(
                text = "Isi Semua Data !!!",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            color = Color(0xFFEEEEEE),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Composable
fun EntryBodyReview(
    insertReviewUiState: InsertReviewUiState,
    onReviewValueChange: (InsertReviewUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(26.dp)
    ) {
        FormInputReview(
            insertReviewUiEvent = insertReviewUiState.insertReviewUiEvent,
            onValueChange = onReviewValueChange,
            modifier = Modifier.fillMaxWidth()
        )
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
                text = "Simpan Review",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryReviewScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertReviewViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertReview.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) {
            innerPadding ->
        EntryBodyReview(
            insertReviewUiState = viewModel.insertReviewuiState,
            onReviewValueChange = viewModel::updateInsertReviewUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertReview()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}