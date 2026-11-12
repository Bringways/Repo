package com.example.matka
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matka.ui.theme.MatkaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MatkaTheme {
                MatkaApp()
            }
        }
    }
}

@Composable
fun MatkaApp() {
    var currentScreen by remember { mutableStateOf("Login") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF101010)
    ) {
        when (currentScreen) {
            "Login" -> LoginScreen(
                onLogin = { currentScreen = "Home" },
                onSignup = { currentScreen = "Signup" }
            )

            "Signup" -> SignupScreen(
                onBack = { currentScreen = "Login" }
            )

            "Home" -> HomeScreen(
                onNavigate = { currentScreen = it }
            )

            else -> SimpleScreen(
                title = currentScreen,
                onBack = { currentScreen = "Home" }
            )
        }
    }
}

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onSignup: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
        painter = painterResource(id = R.drawable.matka_logo),
        contentDescription = "Matka Gaming App Logo",
        modifier = Modifier.size(200.dp)
    )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "MATKA GAMING APP",
            color = Color(0xFFD6B15E),
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it.filter { char -> char.isDigit() }.take(10)
            },
            label = { Text("Phone Number") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it.filter { char -> char.isDigit() }.take(4)
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (phoneNumber.length != 10) {
                    loginMessage = "Please enter a valid 10-digit mobile number"
                } else if (password.length != 4) {
                    loginMessage = "Password must be 4 digits"
                } else {
                    loginMessage = ""
                    onLogin()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD6B15E)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("LOGIN", color = Color.Black)
        }

        TextButton(onClick = onSignup) {
            Text("Create New Account", color = Color(0xFFD6B15E))
        }
        Text(
            text = loginMessage,
            color = Color.Red,
            modifier = Modifier.padding(top = 8.dp)
        )


    }
}

@Composable
fun SignupScreen(onBack: () -> Unit) {

    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var inviteCode by remember { mutableStateOf("") }
    var signupMessage by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(70.dp))

        Text(
            text = "MATKA GAMING APP",
            color = Color((0xFFD6B15E)),
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(60.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = mobile,
            onValueChange = {
                mobile = it.filter { char -> char.isDigit() }.take(10)
            },
            label = { Text("Enter Mobile") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it.filter { char -> char.isDigit() }.take(4)
            },
            label = { Text("Enter Password (4 digits)") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = inviteCode,
            onValueChange = { inviteCode = it },
            label = { Text("Invite Code (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                signupMessage = when {
                    name.isBlank() ->
                        "Please enter your name"

                    mobile.length != 10 ->
                        "Please enter a valid 10-digit mobile number"

                    password.length != 4 ->
                        "Password must be 4 digits"

                    else ->
                        "Signup details are valid!"
                }

            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD6B15E)
            )
        ) {
            Text("SIGN-UP", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(2.dp))

        TextButton(onClick = onBack) {
            Text("Login", color = Color(0xFFD6B15E))
        }

        Text(
            text = signupMessage,
            color = Color(0xFFD6B15E),
            modifier = Modifier.padding(top = 8.dp)
        )

    }
}



@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {

    val gold = Color(0xFFD6B15E)
    val dark = Color(0xFF101010)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(dark)
            .verticalScroll(rememberScrollState())
    ) {

        // TOP HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF252525))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "☰",
                color = gold,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter = painterResource(
                    id = R.drawable.matka_logo
                ),
                contentDescription = "Matka Logo",
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "MATKA GAMING APP",
                color = gold,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // ANNOUNCEMENT
        Text(
            text = "Welcome to Matka Gaming App",
            color = Color.Red,
            fontSize = 20.sp,
            modifier = Modifier.padding(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = gold
                )
            ) {
                Text("Deposit", color = Color.Black)
            }

            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = gold
                )
            ) {
                Text("Withdrawal", color = Color.Black)
            }
    }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF25D366)
                )
            ) {
                Text("WhatsApp", color = Color.White)
            }

            Button(
                onClick = { },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF229ED9)
                )
            ) {
                Text("Telegram", color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        val games = listOf(
            "SRIDEVI",
            "TIME BAZAR",
            "MADHUR DAY",
            "MILAN DAY",
            "RAJDHANI DAY",
            "SUPREME DAY",
            "KALYAN",
            "SRIDEVI NIGHT",
            "MADHUR NIGHT",
            "SUPREME NIGHT",
            "TEST",
            "MILAN NIGHT",
            "KALYAN NIGHT",
            "RAJDHANI NIGHT",
            "MAIN BAZAR"
        )

        games.forEach { game ->
            GameCard(gameName = game)
        }



// BOTTOM NAVIGATION BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF252525)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { onNavigate("My Bids") }) {
                Text("⚒\nMy Bids", color = Color.White)
            }

            TextButton(onClick = { onNavigate("Passbook") }) {
                Text("▤\nPassbook", color = Color.White)
            }

            TextButton(onClick = { onNavigate("Home") }) {
                Text("⌂\nHome", color = gold)
            }

            TextButton(onClick = { onNavigate("Funds") }) {
                Text("▣\nFunds", color = Color.White)
            }

            TextButton(onClick = { onNavigate("Support") }) {
                Text("☏\nSupport", color = Color.White)
            }
        }

    }
}

@Composable
fun GameCard(gameName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF252525)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = gameName,
                color = Color(0xFFD6B15E),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "***-**-***",
                color = Color(0xFFD6B15E),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Open Time: --:--",
                    color = Color.White
                )

                Text(
                    text = "Close Time: --:--",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Result information",
                color = Color(0xFFD6B15E),
                fontSize = 12.sp
            )
        }
    }
}
@Composable
fun InfoCard(message: String) {
    Text(
        text = message,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(Color(0xFF252525), RoundedCornerShape(8.dp))
            .padding(16.dp)
    )
}

@Composable
fun SimpleScreen(title: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title.uppercase(),
            color = Color(0xFFD6B15E),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "No published information available yet.",
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onBack) {
            Text("Back to Home")
        }
    }
}