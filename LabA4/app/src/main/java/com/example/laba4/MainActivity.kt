package com.example.laba4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.laba4.ui.theme.LabA4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabA4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BandScreen()
                }
            }
        }
    }
}

data class BandData(
    val id: Int,
    val name: String,
    val genre: String
)

@Composable
fun BandList(
    bandList: List<BandData>,
    onRemove: (BandData) -> Unit,
    onEdit: (BandData) -> Unit
) {
    LazyColumn {
        items(bandList) { band ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${band.id}. ${band.name} - ${band.genre}")
                IconButton(onClick = { onRemove(band) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
                IconButton(onClick = { onEdit(band) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    }
}

class BandViewModel : ViewModel() {
    private val _bandList = mutableStateListOf<BandData>()
    val bandList: SnapshotStateList<BandData> get() = _bandList

    init {
        _bandList.addAll(
            listOf(
                BandData(1, "Imagine dragons", "Pop"),
                BandData(2, "Billie Eilish", "Pop"),
            )
        )
    }

    fun addBand(band: BandData) {
        _bandList.add(band)
    }

    fun removeBand(band: BandData) {
        _bandList.remove(band)
    }

    fun updateBand(updatedBand: BandData) {
        val index = _bandList.indexOfFirst { it.id == updatedBand.id }
        if (index >= 0) {
            _bandList[index] = updatedBand
        }
    }
}

@Composable
fun BandScreen(viewModel: BandViewModel = viewModel()) {
    var editingBand by remember { mutableStateOf<BandData?>(null) }

    Column {
        BandList(
            bandList = viewModel.bandList,
            onRemove = { viewModel.removeBand(it) },
            onEdit = { editingBand = it }
        )
        AddBandButton(viewModel, editingBand, onEditComplete = { editingBand = null })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBandButton(
    viewModel: BandViewModel,
    editingBand: BandData?,
    onEditComplete: () -> Unit
) {
    var id by remember { mutableStateOf(viewModel.bandList.size + 1) }
    var name by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }

    LaunchedEffect(editingBand) {
        if (editingBand != null) {
            id = editingBand.id
            name = editingBand.name
            genre = editingBand.genre
        }
    }

    Column {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Band name") }
        )
        TextField(
            value = genre,
            onValueChange = { genre = it },
            label = { Text("Genre") }
        )
        Button(onClick = {
            if (name.isNotEmpty() && genre.isNotEmpty()) {
                val newBand = BandData(id, name, genre)
                if (editingBand == null) {
                    viewModel.addBand(newBand)
                    id++
                } else {
                    viewModel.updateBand(newBand)
                    onEditComplete()
                }
                name = ""
                genre = ""
            }
        }) {
            Text(if (editingBand == null) "Add band info" else "Update band info")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabA4Theme {
        BandScreen()
    }
}
