package com.unsadanpbt.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var editando by remember { mutableStateOf(false) }
    var indiceEditando by remember { mutableStateOf(-1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "TO DO LIST",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // TextField reutilizado para nueva tarea O edición
        TextField(
            value = texto,
            onValueChange = { texto = it },
            label = {
                Text(
                    if (editando) "Editar tarea" else "Nueva tarea"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (texto.isNotBlank()) {
                    if (editando) {
                        // Guardar edición
                        val nuevaLista = lista.toMutableList()
                        nuevaLista[indiceEditando] = texto
                        lista = nuevaLista
                    } else {
                        // Agregar nueva tarea
                        lista = lista + texto
                    }
                    // Resetear estados
                    texto = ""
                    editando = false
                    indiceEditando = -1
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (editando) "Guardar" else "Agregar Tarea"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {
            itemsIndexed(lista) { index, tarea ->
                TareaItem(
                    tarea = tarea,
                    indice = index,
                    editando = editando,
                    indiceEditando = indiceEditando,
                    onIniciarEdicion = {
                        texto = tarea
                        editando = true
                        indiceEditando = index
                    },
                    onEliminar = {
                        lista = lista.toMutableList().apply { removeAt(index) }
                    }
                )
            }
        }
    }
}

@Composable
fun TareaItem(
    tarea: String,
    indice: Int,
    editando: Boolean,
    indiceEditando: Int,
    onIniciarEdicion: () -> Unit,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        // Columna 1 - Texto de la tarea
        Text(
            text = tarea,
            modifier = Modifier
                .weight(0.7f)
                .padding(end = 8.dp),
            maxLines = 2
        )

        // Columna 2 - Botón Editar
        Button(
            onClick = {
                // Solo permite editar si no está editando otra
                if (!editando || indiceEditando == indice) {
                    onIniciarEdicion()
                }
            },
            modifier = Modifier
                .weight(0.15f)
                .height(40.dp),
            shape = RectangleShape,
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                // Uso de emojis para evitar agregar imagenes customizadas
                text = if (editando && indiceEditando == indice) "📝" else "✏",
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Columna 3 - Botón Eliminar
        Button(
            onClick = { onEliminar() },
            modifier = Modifier
                .weight(0.15f)
                .height(40.dp),
            shape = RectangleShape,
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            // Uso de emojis para evitar agregar imagenes customizadas
            Text("❌", fontSize = 12.sp)
        }
    }
}