package com.example.matka

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Registered users: phone -> Pair(name, mpin)
            val users = remember { mutableStateMapOf<String, Pair<String, String>>() }
            var currentScreen by remember { mutableStateOf("login") } // login, signup, forgot, home
            var loggedInLabel by remember { mutableStateOf("") }

            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFFF5C451),
                    background = Color(0xFF090E1A),
                    surface = Color(0xFF121A2E)
                )
            ) {
                when (currentScreen) {
                    "login" -> LoginScreen(
                        onLoginClick = { mobile, mpin ->
                            if (mobile.length != 10) {
                                "Please enter a valid 10-digit mobile number."
                            } else if (mpin.length != 4) {
                                "Please enter a valid 4-digit MPIN."
                            } else {
                                val user = users[mobile]
                                if (user == null) {
                                    "Mobile not registered. Please Sign-Up first."
                                } else if (user.second != mpin) {
                                    "Incorrect MPIN. Please try again."
                                } else {
                                    loggedInLabel = "${user.first} • $mobile"
                                    currentScreen = "home"
                                    null
                                }
                            }
                        },
                        onForgotClick = { currentScreen = "forgot" },
                        onSignUpClick = { currentScreen = "signup" }
                    )

                    "signup" -> SignUpScreen(
                        onBack = { currentScreen = "login" },
                        onRegister = { name, mobile, mpin ->
                            if (users.containsKey(mobile)) {
                                "This mobile number is already registered. Please Login."
                            } else {
                                users[mobile] = name to mpin
                                loggedInLabel = "$name • $mobile"
                                Toast.makeText(this, "Account created! Welcome $name", Toast.LENGTH_SHORT).show()
                                currentScreen = "home"
                                null
                            }
                        }
                    )

                    "forgot" -> ForgotMpinScreen(
                        onBack = { currentScreen = "login" },
                        onResetMpin = { mobile, newMpin ->
                            val existing = users[mobile]
                            if (existing == null) {
                                "Mobile number not registered."
                            } else {
                                users[mobile] = existing.first to newMpin
                                Toast.makeText(this, "MPIN updated! Please Login.", Toast.LENGTH_SHORT).show()
                                currentScreen = "login"
                                null
                            }
                        }
                    )

                    "home" -> MainHomeShell(
                        userPhone = loggedInLabel,
                        onLogout = { currentScreen = "login" }
                    )
                }
            }
        }
    }
}

@Composable
fun MatkaBrandLogo(size: Dp = 110.dp) {
    val emeraldGrad = Brush.linearGradient(listOf(Color(0xFF065F46), Color(0xFF022C22)))
    val goldGrad = Brush.linearGradient(listOf(Color(0xFFFDE68A), Color(0xFFF59E0B), Color(0xFFB45309)))
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.23f)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = this.size.width
            val h = this.size.height
            drawRoundRect(brush = emeraldGrad, cornerRadius = CornerRadius(w * 0.23f, h * 0.23f))
            drawRoundRect(
                brush = goldGrad,
                topLeft = Offset(w * 0.03f, h * 0.03f),
                size = Size(w * 0.94f, h * 0.94f),
                cornerRadius = CornerRadius(w * 0.21f, h * 0.21f),
                style = Stroke(width = w * 0.04f)
            )
            val crown = Path().apply {
                moveTo(w * 0.28f, h * 0.34f)
                lineTo(w * 0.375f, h * 0.22f)
                lineTo(w * 0.50f, h * 0.33f)
                lineTo(w * 0.625f, h * 0.22f)
                lineTo(w * 0.72f, h * 0.34f)
                lineTo(w * 0.67f, h * 0.44f)
                lineTo(w * 0.33f, h * 0.44f)
                close()
            }
            drawPath(crown, brush = goldGrad)
            drawOval(brush = goldGrad, topLeft = Offset(w * 0.28f, h * 0.50f), size = Size(w * 0.44f, h * 0.34f))
            val collar = Path().apply {
                moveTo(w * 0.34f, h * 0.50f)
                lineTo(w * 0.66f, h * 0.50f)
                lineTo(w * 0.61f, h * 0.56f)
                lineTo(w * 0.39f, h * 0.56f)
                close()
            }
            drawPath(collar, color = Color(0xFF064E3B))
        }
        Text(
            text = "MG",
            color = Color(0xFF022C22),
            fontWeight = FontWeight.ExtraBold,
            fontSize = (size.value * 0.19f).sp,
            modifier = Modifier.offset(y = size * 0.18f)
        )
    }
}

@Composable
fun LoginScreen(
    onLoginClick: (mobile: String, mpin: String) -> String?,
    onForgotClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val bg = Color(0xFF090E1A)
    val surface = Color(0xFF121A2E)
    val inputBg = Color(0xFF0E1526)
    val border = Color(0xFF283659)
    val gold = Color(0xFFF5C451)
    val crimson = Color(0xFFF43F5E)
    val textPrimary = Color(0xFFF8FAFC)
    val textSecondary = Color(0xFF94A3B8)

    var mobile by remember { mutableStateOf("") }
    var mpin by remember { mutableStateOf("") }
    var showPin by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(bg, surface)))
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(28.dp))
        MatkaBrandLogo(size = 112.dp)
        Spacer(Modifier.height(14.dp))
        Text("MATKA GAMING APP", color = gold, fontWeight = FontWeight.ExtraBold, fontSize = 21.sp, letterSpacing = 1.4.sp)
        Text("Sign in to continue playing", color = textSecondary, fontSize = 13.sp)

        Spacer(Modifier.height(34.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Enter Mobile", color = textPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = mobile,
                onValueChange = { if (it.length <= 10) mobile = it.filter(Char::isDigit); errorMsg = null },
                placeholder = { Text("10-digit mobile number", color = textSecondary) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
                    focusedBorderColor = gold,
                    unfocusedBorderColor = border,
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(18.dp))
            Text("Enter MPin", color = textPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = mpin,
                onValueChange = { if (it.length <= 4) mpin = it.filter(Char::isDigit); errorMsg = null },
                placeholder = { Text("4-digit MPIN", color = textSecondary) },
                singleLine = true,
                visualTransformation = if (showPin) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                trailingIcon = {
                    TextButton(onClick = { showPin = !showPin }) {
                        Text(if (showPin) "Hide" else "Show", color = gold, fontSize = 12.sp)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
                    focusedBorderColor = gold,
                    unfocusedBorderColor = border,
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (errorMsg != null) {
            Spacer(Modifier.height(12.dp))
            Text(errorMsg!!, color = crimson, fontSize = 13.sp, textAlign = TextAlign.Center)
        }

        Spacer(Modifier.height(26.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.horizontalGradient(listOf(Color(0xFFEAB308), Color(0xFFD97706))))
                .clickable { errorMsg = onLoginClick(mobile, mpin) },
            contentAlignment = Alignment.Center
        ) {
            Text("LOGIN", color = Color(0xFF0A0F1E), fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        Spacer(Modifier.height(20.dp))
        Text("Forgot mpin ?", color = textPrimary, fontSize = 15.sp, modifier = Modifier.clickable { onForgotClick() }.padding(6.dp))
        Spacer(Modifier.height(8.dp))
        Text("Sign-Up", color = gold, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onSignUpClick() }.padding(6.dp))
    }
}

@Composable
fun SignUpScreen(
    onBack: () -> Unit,
    onRegister: (name: String, mobile: String, mpin: String) -> String?
) {
    val bg = Color(0xFF090E1A)
    val surface = Color(0xFF121A2E)
    val inputBg = Color(0xFF0E1526)
    val border = Color(0xFF283659)
    val gold = Color(0xFFF5C451)
    val crimson = Color(0xFFF43F5E)
    val textPrimary = Color(0xFFF8FAFC)
    val textSecondary = Color(0xFF94A3B8)

    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var mpin by remember { mutableStateOf("") }
    var referral by remember { mutableStateOf("") }
    var otpSent by remember { mutableStateOf(false) }
    var otp by remember { mutableStateOf("") }
    var showPin by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(bg, surface)))
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(22.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Text("←", color = textPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            Text("Create MATKA GAMING APP Account", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp)
        }

        Spacer(Modifier.height(18.dp))
        Text("Full Name", color = textSecondary, fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it; errorMsg = null },
            placeholder = { Text("Enter your full name", color = textSecondary) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                focusedBorderColor = gold, unfocusedBorderColor = border,
                focusedTextColor = textPrimary, unfocusedTextColor = textPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(14.dp))
        Text("Mobile Number", color = textSecondary, fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = mobile,
            onValueChange = { if (it.length <= 10) mobile = it.filter(Char::isDigit); errorMsg = null },
            placeholder = { Text("10-digit mobile number", color = textSecondary) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                focusedBorderColor = gold, unfocusedBorderColor = border,
                focusedTextColor = textPrimary, unfocusedTextColor = textPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(14.dp))
        Text("Set 4-Digit MPIN", color = textSecondary, fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = mpin,
            onValueChange = { if (it.length <= 4) mpin = it.filter(Char::isDigit); errorMsg = null },
            placeholder = { Text("4-digit PIN", color = textSecondary) },
            singleLine = true,
            visualTransformation = if (showPin) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            trailingIcon = {
                TextButton(onClick = { showPin = !showPin }) {
                    Text(if (showPin) "Hide" else "Show", color = gold, fontSize = 12.sp)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                focusedBorderColor = gold, unfocusedBorderColor = border,
                focusedTextColor = textPrimary, unfocusedTextColor = textPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(14.dp))
        Text("Referral Code (Optional)", color = textSecondary, fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = referral,
            onValueChange = { referral = it.uppercase() },
            placeholder = { Text("Optional referral code", color = textSecondary) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                focusedBorderColor = gold, unfocusedBorderColor = border,
                focusedTextColor = textPrimary, unfocusedTextColor = textPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        if (otpSent) {
            Spacer(Modifier.height(14.dp))
            Text("Enter OTP (Use 1234)", color = gold, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = otp,
                onValueChange = { if (it.length <= 4) otp = it.filter(Char::isDigit); errorMsg = null },
                placeholder = { Text("4-digit OTP (1234)", color = textSecondary) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                    focusedBorderColor = gold, unfocusedBorderColor = border,
                    focusedTextColor = textPrimary, unfocusedTextColor = textPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (errorMsg != null) {
            Spacer(Modifier.height(12.dp))
            Text(errorMsg!!, color = crimson, fontSize = 13.sp)
        }

        Spacer(Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.horizontalGradient(listOf(Color(0xFFEAB308), Color(0xFFD97706))))
                .clickable {
                    if (!otpSent) {
                        if (name.trim().length < 2 || mobile.length != 10 || mpin.length != 4) {
                            errorMsg = "Please fill Name, 10-digit Mobile, and 4-digit MPIN."
                        } else {
                            otpSent = true
                            errorMsg = null
                        }
                    } else if (otp != "1234") {
                        errorMsg = "Invalid OTP. Enter 1234."
                    } else {
                        errorMsg = onRegister(name.trim(), mobile, mpin)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(if (!otpSent) "SEND OTP" else "VERIFY & SIGN UP", color = Color(0xFF0A0F1E), fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

@Composable
fun ForgotMpinScreen(
    onBack: () -> Unit,
    onResetMpin: (mobile: String, newMpin: String) -> String?
) {
    val bg = Color(0xFF090E1A)
    val surface = Color(0xFF121A2E)
    val inputBg = Color(0xFF0E1526)
    val border = Color(0xFF283659)
    val gold = Color(0xFFF5C451)
    val crimson = Color(0xFFF43F5E)
    val textPrimary = Color(0xFFF8FAFC)
    val textSecondary = Color(0xFF94A3B8)

    var mobile by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var newMpin by remember { mutableStateOf("") }
    var otpSent by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(bg, surface)))
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(22.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Text("←", color = textPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            Text("Reset MPIN", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
        Spacer(Modifier.height(18.dp))

        Text("Registered Mobile Number", color = textSecondary, fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = mobile,
            onValueChange = { if (it.length <= 10) mobile = it.filter(Char::isDigit); errorMsg = null },
            placeholder = { Text("10-digit mobile number", color = textSecondary) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                focusedBorderColor = gold, unfocusedBorderColor = border,
                focusedTextColor = textPrimary, unfocusedTextColor = textPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        if (otpSent) {
            Spacer(Modifier.height(14.dp))
            Text("OTP (Use 1234)", color = gold, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = otp,
                onValueChange = { if (it.length <= 4) otp = it.filter(Char::isDigit); errorMsg = null },
                placeholder = { Text("1234", color = textSecondary) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                    focusedBorderColor = gold, unfocusedBorderColor = border,
                    focusedTextColor = textPrimary, unfocusedTextColor = textPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(14.dp))
            Text("Set New 4-Digit MPIN", color = textSecondary, fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = newMpin,
                onValueChange = { if (it.length <= 4) newMpin = it.filter(Char::isDigit); errorMsg = null },
                placeholder = { Text("New 4-digit MPIN", color = textSecondary) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                    focusedBorderColor = gold, unfocusedBorderColor = border,
                    focusedTextColor = textPrimary, unfocusedTextColor = textPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (errorMsg != null) {
            Spacer(Modifier.height(12.dp))
            Text(errorMsg!!, color = crimson, fontSize = 13.sp)
        }

        Spacer(Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.horizontalGradient(listOf(Color(0xFFEAB308), Color(0xFFD97706))))
                .clickable {
                    if (!otpSent) {
                        if (mobile.length != 10) errorMsg = "Enter a valid 10-digit mobile number."
                        else {
                            otpSent = true
                            errorMsg = null
                        }
                    } else if (otp != "1234") {
                        errorMsg = "Invalid OTP. Enter 1234."
                    } else if (newMpin.length != 4) {
                        errorMsg = "New MPIN must be 4 digits."
                    } else {
                        errorMsg = onResetMpin(mobile, newMpin)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(if (!otpSent) "SEND OTP" else "UPDATE MPIN", color = Color(0xFF0A0F1E), fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}