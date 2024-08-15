package add_listing.presentation.add_listing_steps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.compose_multiplatform
import navigation.add_listing.AddFinalDetailsComponent
import navigation.add_listing.AddFinalDetailsEvent
import org.jetbrains.compose.resources.painterResource

@Composable
fun FinalDetailsScreen(component: AddFinalDetailsComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty title as in the design */ },
                navigationIcon = {
                    IconButton(onClick = { component.onEvent(AddFinalDetailsEvent.GoBack) }) {
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
                        text = "You’re at the finish line – we just need a few more details.",
                        style = MaterialTheme.typography.h6,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "None of this information will be revealed to clients – it’s simply here for us to better serve you.",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    var phoneNumber by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Primary phone number") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    var selectedOption by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = selectedOption,
                        onValueChange = { selectedOption = it },
                        label = { Text("How did you hear about GigSalad? (Optional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "All lead, message, and booking notifications will be sent to cristhian@nationalrelieftelecom.com. Would you also like to receive these alerts via text message?",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = true,
                                onClick = { /* Lógica para manejar la selección */ }
                            )
                            Text(text = "Yes, text me at the number above.")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = false,
                                onClick = { /* Lógica para manejar la selección */ }
                            )
                            Text(text = "Yes, but I want to use a different number for texts.")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = false,
                                onClick = { /* Lógica para manejar la selección */ }
                            )
                            Text(text = "No, I only want email notifications.")
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { component.onEvent(AddFinalDetailsEvent.GoToRequestReviewsScreen) },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = horizontalPadding)
                    ) {
                        Text(text = "Continue")
                    }
                }
            }
        }
    )
}
