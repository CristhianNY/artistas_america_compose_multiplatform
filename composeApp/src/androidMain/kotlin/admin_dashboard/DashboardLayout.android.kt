package admin_dashboard


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import navigation.dashboard.DashboardComponent
import org.artistasamerica.artistas.R

@Composable
actual fun DashboardLayout(component: DashboardComponent) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DashboardDrawerContent(scope, drawerState)
        },
        content = {
            MainContent(scope, drawerState)
        }
    )
}

@Composable
fun MainContent(scope: CoroutineScope, drawerState: DrawerState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        DashboardTopAppBar(scope, drawerState)
        Spacer(modifier = Modifier.height(16.dp))
        // Aquí puedes agregar más contenido principal del Dashboard
    }
}

@Composable
fun DashboardTopAppBar(scope: CoroutineScope, drawerState: DrawerState) {
    TopAppBar(
        backgroundColor = Color.White,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with menu icon resource
                    contentDescription = "Menu",
                    modifier = Modifier.size(24.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your logo resource
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Dashboard",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconsAndUserMenu()
        }
    }
}

@Composable
fun IconsAndUserMenu() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter= painterResource(id = R.drawable.ic_launcher_background), // Replace with favorite icon resource
            contentDescription = "Favorite",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with messages icon resource
            contentDescription = "Messages",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Cristian",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with arrow down resource
            contentDescription = "Arrow Down",
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
fun DashboardDrawerContent(scope: CoroutineScope, drawerState: DrawerState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Dashboard",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = "Inbox (88)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = "Reviews (26)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = "Edit PromoKit",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = "Calendar",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = "Tools",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = "Account",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
        Text(
            text = "Help",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable { /* Handle navigation */ }
        )
    }
}
