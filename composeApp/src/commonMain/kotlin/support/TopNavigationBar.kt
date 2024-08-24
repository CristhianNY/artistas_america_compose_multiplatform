package support

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.arrow_back
import com.arkivanov.decompose.ComponentContext
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopNavigationBar(
    isLoggedIn: Boolean,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    component: ComponentContext,
    onBackClick: () -> Unit, // Añadido para manejar el clic en back
    showBackButton: Boolean = false, // Añadido para controlar la visibilidad del botón back
    showAddOption: Boolean = true,
    showDashboardOption: Boolean = true,
    showLogoutOption: Boolean = true,
    showLoginOption: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) } // Estado para controlar el menú desplegable

    TopAppBar(
        backgroundColor = Color.White,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        BoxWithConstraints {
            val isSmallScreen = maxWidth < 600.dp

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (showBackButton) {
                        IconButton(onClick = onBackClick) {
                            Image(
                                painterResource(Res.drawable.arrow_back),
                                contentDescription = "Back",
                                modifier = Modifier.size(20.dp).clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = "App Title",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {

                    TextButton(onClick = { expanded = !expanded }) { // Botón de menú desplegable
                        Text(
                            text = "Menu",
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        if (showAddOption) {
                            DropdownMenuItem(onClick = {
                                expanded = false
                                when (component) {
                                    is navigation.home.HomeComponent -> component.onEvent(navigation.home.HomeEvent.GoToJoin)
                                    is navigation.lading.LandingComponent -> component.onEvent(navigation.lading.LandingEvent.GoToServiceActorNameScreen)
                                }
                            }) {
                                Text(if (isSmallScreen) "Add" else "List your Services")
                            }
                        }

                        if (isLoggedIn && showDashboardOption) {
                            DropdownMenuItem(onClick = {
                                expanded = false
                                when (component) {
                                    is navigation.home.HomeComponent -> component.onEvent(navigation.home.HomeEvent.GoToDashboard)
                                    is navigation.lading.LandingComponent -> component.onEvent(navigation.lading.LandingEvent.GoToDashboard)
                                }
                            }) {
                                Text("Dashboard")
                            }
                        }

                        if (isLoggedIn && showLogoutOption) {
                            DropdownMenuItem(onClick = {
                                expanded = false
                                onLogoutClick()
                            }) {
                                Text("Log Out")
                            }
                        } else if (!isLoggedIn && showLoginOption) {
                            DropdownMenuItem(onClick = {
                                expanded = false
                                onLoginClick()
                            }) {
                                Text("Log In")
                            }
                        }
                    }
                }
            }
        }
    }
}
