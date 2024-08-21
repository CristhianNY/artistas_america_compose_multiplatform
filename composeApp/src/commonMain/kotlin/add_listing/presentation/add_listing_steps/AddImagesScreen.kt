package add_listing.presentation.add_listing_steps

import Strings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch
import navigation.add_listing.AddImageComponent
import navigation.add_listing.AddImageEvent
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import support.ImagePicker

@Composable
fun AddImagesScreen(component: AddImageComponent) {

    val imagePicker: ImagePicker = koinInject()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty title as in the design */ },
                navigationIcon = {
                    IconButton(onClick = { component.onEvent(AddImageEvent.GoBack) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color.White,
                elevation = 0.dp
            )
        },
        content = {
            BoxWithConstraints {
                val isSmallScreen = maxWidth < 1200.dp
                val horizontalPadding = if (isSmallScreen) 0.dp else 400.dp

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()), // Añadir scroll vertical
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = Strings.PROMOTIONAL_IMAGE_DESC,
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = "We’re almost done!",
                        style = MaterialTheme.typography.h6,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "What photo would you like to represent your act or service?",
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Your profile photo shows up prominently in search results, at the top of your profile page, and in communication with the client. Make it a good one.",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    // Aquí va el componente para subir la imagen
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val imageBytes = imagePicker.pickImage()
                                if (imageBytes != null) {
                                    // Lógica para subir la imagen al servidor
                                    // Aquí podrías llamar a una función de tu ViewModel o similar
                                } else {
                                    // Manejar el caso en que el usuario no seleccionó ninguna imagen
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = horizontalPadding)
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Text(text = "Click to upload an image")
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { component.onEvent(AddImageEvent.GotoDescriptionScreen) },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = horizontalPadding)
                    ) {
                        Text(text = "Continue")
                    }
                }
            }
        }
    )
}
