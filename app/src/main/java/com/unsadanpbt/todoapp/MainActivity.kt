package com.unsadanpbt.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unsadanpbt.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    ToDoListComposable(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ListaDocentes(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
@Composable
fun InputTexto() {
    var texto by remember { mutableStateOf("") }

    Column {
        TextField(
            value = texto,
            onValueChange = {texto = it}
        )

        Text("Escribiste: $texto")
    }
}
@Composable
fun ListaDinamica() {
    var lista by remember { mutableStateOf(listOf<String>()) }

    Column {
        Button(onClick = {
            lista = lista + "Nuevo item"
        }) {
            Text("Agregar")
        }
        LazyColumn {
            items(lista) {
                Text(it)
            }
        }
    }
}

@Composable
fun ToDoListComposable(modifier: Modifier = Modifier) {
    var lista by remember { mutableStateOf(listOf<String>()) }
    var texto by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)

            .padding(16.dp)
    ) {
        Text("TO DO LIST")

        TextField(
            value = texto,
            onValueChange = { texto = it }
        )

        Button(
            onClick = {
                lista = lista + texto
                texto = "" // opcional: limpiar input
            }
        ) {
            Text("Agregar Tarea")
        }

        LazyColumn {
            items(lista) {
                Text(it)
            }
        }
    }
}

/*@Composable
fun ToDoListComposable() {
    var lista by remember { mutableStateOf(listOf<String>()) }
    var texto by remember { mutableStateOf("") }

    Column {
        Text("TO DO LIST")
        TextField(
            value = texto,
            onValueChange = {texto = it}
        )

        //Text("Escribiste: $texto")

        Button(onClick = {
            lista = lista + texto
        }) {
            Text("Agregar Tarea")
        }

        LazyColumn {
            items(lista) {
                Text(it)
            }
        }
    }
}*/
