package admin_dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import navigation.DashboardComponent
import org.jetbrains.compose.resources.painterResource

@Composable
actual fun DashboardLayout(component: DashboardComponent) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        DashboardTopAppBar()
        Spacer(modifier = Modifier.height(16.dp))
        DashboardTabs()
    }
}

@Composable
fun DashboardTopAppBar() {
    TopAppBar(
        backgroundColor = Color.White,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LogoAndMenu()
            IconsAndUserMenu()
        }
    }
}

@Composable
fun LogoAndMenu() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform), // Replace with your logo resource
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Menu",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            painter = painterResource(Res.drawable.compose_multiplatform), // Replace with arrow down resource
            contentDescription = "Arrow Down",
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun IconsAndUserMenu() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(Res.drawable.compose_multiplatform), // Replace with favorite icon resource
            contentDescription = "Favorite",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            painter = painterResource(Res.drawable.compose_multiplatform), // Replace with messages icon resource
            contentDescription = "Messages",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Cristian",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            painter = painterResource(Res.drawable.compose_multiplatform), // Replace with arrow down resource
            contentDescription = "Arrow Down",
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun DashboardTabs() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TabItem(text = "Dashboard", isSelected = true)
        TabItem(text = "Inbox (88)")
        TabItem(text = "Reviews (26)")
        TabItem(text = "Edit PromoKit")
        TabItem(text = "Calendar")
        TabItem(text = "Tools")
        TabItem(text = "Account")
        TabItem(text = "Help")
    }
}

@Composable
fun TabItem(text: String, isSelected: Boolean = false) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = if (isSelected) Color.Blue else Color.Gray
    )
}
