import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import kotlinx.coroutines.launch


@Composable
fun AppContent(viewModel: HomeViewModel, byteArrayFactory: ByteArrayFactory) {

    val appUiState = viewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    HomeScreen(uiState = appUiState.value, byteArrayFactory) { inputText, selectedItems ->

        coroutineScope.launch {
            viewModel.questioning(userInput = inputText, selectedImages = selectedItems)
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState.Loading,
    byteArrayFactory: ByteArrayFactory,
    onSendClicked: (String, List<ByteArray>) -> Unit
) {

    var userQues by rememberSaveable {
        mutableStateOf("")
    }

    val imagesList = remember {
        mutableStateListOf<ByteArray>()
    }


    val coroutineScope = rememberCoroutineScope()
    var showFilePicker by remember { mutableStateOf(false) }

    val fileTypes = listOf("jpg", "png")
    FilePicker(show = showFilePicker, fileExtensions = fileTypes) { mpFile ->
        showFilePicker = false
        coroutineScope.launch {
            if (mpFile != null) {
                val byteArray = byteArrayFactory.getByteArray(mpFile)
                if (byteArray != null) {
                    imagesList.add(byteArray)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gemini AI Chatbot KMP") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            Column {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    //Add image Icon
                    IconButton(onClick = {
                        showFilePicker = true
                    }, modifier = Modifier.padding(4.dp)) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add Image"
                        )
                    }

                    //Input Field
                    OutlinedTextField(
                        value = userQues,
                        onValueChange = {
                            userQues = it
                        },
                        label = { Text(text = "User Input") },
                        placeholder = { Text(text = "Upload Image and ask question") },
                        modifier = Modifier.fillMaxWidth(0.83f)
                    )

                    //Send Button
                    IconButton(
                        onClick = {
                            if (userQues.isNotBlank()) {
                                onSendClicked(userQues, imagesList)
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send"
                        )
                    }
                }

                AnimatedVisibility(visible = imagesList.isNotEmpty()) {

                    Card(modifier = Modifier.fillMaxWidth()) {
                        LazyRow(modifier = Modifier.padding(8.dp)) {
                            items(imagesList) { imageUri ->
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AsyncImage(
                                        model = imageUri,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .requiredSize(50.dp)
                                    )
                                    TextButton(onClick = { imagesList.remove(imageUri) }) {
                                        Text(text = "Remove")
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (uiState) {
                is HomeUiState.Initial -> {}
                is HomeUiState.Loading -> {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is HomeUiState.Success -> {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(), shape = MaterialTheme.shapes.large
                    ) {
                        Text(text = uiState.outputText, modifier = Modifier.padding(16.dp))
                    }
                }

                is HomeUiState.Error -> {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(text = uiState.error)
                    }
                }
            }

        }

    }

}