package add_listing.presentation.add_listing_steps

import Strings
import add_listing.presentation.LandingViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
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
import support.*

@Composable
fun AddImagesScreen(component: AddImageComponent) {

    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus) {
            // Aquí puedes manejar el estado de los permisos si es necesario
        }
    })

    val viewModel: LandingViewModel = koinInject()

    val selectedImage = viewModel.selectedImage.collectAsState().value

    var galleryPermissionRequested by remember { mutableStateOf(false) }

    val galleryPermissionGranted = permissionsManager.isPermissionGranted(PermissionType.GALLERY)



    // Si también deseas manejar la galería con un GalleryManager, puedes hacer algo similar
    val galleryManager = rememberGalleryManager { imageBytes ->
        if (imageBytes != null) {
            viewModel.updateSelectedImage(imageBytes)
        }
    }

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
                        .verticalScroll(rememberScrollState()),
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

                    if (selectedImage != null) {
                        val bitmap = selectedImage.toImageBitmap()
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap,
                                contentDescription = "Selected Image",
                                modifier = Modifier
                                    .padding(horizontal = horizontalPadding)
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        IconButton(onClick = { viewModel.removeSelectedImage() }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Image", tint = Color.Red)
                        }
                    } else {
                        // Lógica para la solicitud de permisos
                        when {
                            galleryPermissionRequested && !galleryPermissionGranted -> {
                                permissionsManager.askPermission(PermissionType.GALLERY)
                            }
                            else -> {

                                // Botón para seleccionar imagen de la galería
                                Button(
                                    onClick = {
                                        if (galleryPermissionGranted) {
                                            galleryManager.launch()  // Llamar a launch() en la instancia de GalleryManager
                                        } else {
                                            galleryPermissionRequested = true
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(horizontal = horizontalPadding)
                                        .fillMaxWidth()
                                        .height(200.dp)
                                ) {
                                    Text(text = "Select Image from Gallery")
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { component.onEvent(AddImageEvent.GotoDescriptionScreen) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding)
                    ) {
                        Text(text = "Continue")
                    }
                }
            }
        }
    )
}
