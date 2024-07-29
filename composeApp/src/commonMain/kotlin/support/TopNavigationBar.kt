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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.compose_multiplatform
import com.arkivanov.decompose.ComponentContext
import navigation.home.HomeComponent
import navigation.home.HomeEvent
import navigation.lading.LandingComponent
import navigation.lading.LandingEvent
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopNavigationBar(
    isLoggedIn: Boolean,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    component: ComponentContext
) {
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
                    Image(
                        painterResource(Res.drawable.compose_multiplatform), null,
                        modifier = Modifier.size(40.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Menu",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = "Arrow Down",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = "Favorite",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    if (!isLoggedIn) {
                        TextButton(onClick = onLoginClick) {
                            Text(
                                text = "Log In",
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        TextButton(onClick = {
                            when (component) {
                                is HomeComponent -> component.onEvent(HomeEvent.GoToDashboard)
                                is LandingComponent -> component.onEvent(LandingEvent.GoToDashboard)
                            }

                        }) {
                            Text(
                                text = "Dashboard",
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }

                        TextButton(onClick = onLogoutClick) {
                            Text(
                                text = "Log Out",
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF)),
                        onClick = {
                            when (component) {
                                is HomeComponent -> component.onEvent(HomeEvent.GoToJoin)
                            }
                        }) {
                        Text(
                            text = if (isSmallScreen) "Add" else "List your Services",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}