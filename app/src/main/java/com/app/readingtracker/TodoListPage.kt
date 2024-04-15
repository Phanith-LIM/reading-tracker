package com.app.readingtracker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TodoListPage(viewModel: TodoViewModel) {
    val todoList by viewModel.todoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxHeight().padding(8.dp),
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                modifier = Modifier.weight(1f),
                onValueChange = {
                    inputText = it
                }
            )
            Button(
                modifier = Modifier.padding(horizontal = 8.dp).height(IntrinsicSize.Max),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2596be)),
                shape = RoundedCornerShape(4.dp),
                onClick = {
                    viewModel.addTodo(inputText)
                    inputText = ""
                }
            ) {
                Text(text = "Add")
            }
        }
        todoList?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it) { index: Int, item: Todo ->
                        TodoItem(item, onDelete = {
                            viewModel.deleteTodo(id = item.id)
                        })
                    }
                }
            )
        }?: Text(
            modifier = Modifier.fillMaxWidth(),
            text = "No items yet",
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}

@Composable
fun TodoItem(item: Todo, onDelete: ()-> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
//            .background(Color(0xFF2596be).copy(alpha = 0.5f))
            .background(Color(0xFF2596be))
            .padding(16.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column (modifier = Modifier.weight(1f)) {
                Text(
                    text = SimpleDateFormat("HH:mm:aa, dd/mm", Locale.ENGLISH).format(item.createAt),
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = item.title,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500
                )
            }
            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_delete_24),
                    contentDescription = "Delete",
                    tint = Color.White,
                )
            }
        }
    }
}
