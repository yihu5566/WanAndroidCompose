package com.ldf.wanandroidcompose.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.statusBarsHeight
import com.ldf.wanandroidcompose.R
import com.ldf.wanandroidcompose.ui.widget.lottie.LottieWorkingLoadingView

/**
 * @Author : dongfang
 * @Created Time : 2023-06-21  15:51
 * @Description:
 */
@Composable
fun LoginScreen(navHostController: NavHostController) {

    Scaffold(backgroundColor = MaterialTheme.colors.primaryVariant) { paddingValues ->
        var userName by remember { mutableStateOf(TextFieldValue("")) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var hasError by remember { mutableStateOf(false) }
        val userNameInteractionState = remember { MutableInteractionSource() }
        val passwordInteractionState = remember { MutableInteractionSource() }

        var passwordVisualTransformation by remember {
            mutableStateOf<VisualTransformation>(
                PasswordVisualTransformation()
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .statusBarsHeight()
                        .fillMaxWidth()
                )
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item {
                Text(
                    "关闭",
                    modifier = Modifier.clickable { navHostController.navigateUp() },
                    style = TextStyle(
                        Color.White,
                        fontSize = 16.sp
                    )
                )
            }
            item { LottieWorkingLoadingView(context = LocalContext.current) }
            item {
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = userName,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.mipmap.ic_wenzhang),
                            contentDescription = ""
                        )
                    },
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
//                    colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(),
                    label = { Text(text = "用户名") },
                    isError = hasError,
                    placeholder = { Text(text = "请输入用户名") },
                    onValueChange = {
                        userName = it
                    },
                    interactionSource = userNameInteractionState,
                )

            }
            item {
                OutlinedTextField(
                    value = password,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.mipmap.ic_wenzhang),
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.mipmap.ic_shezhi),
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                passwordVisualTransformation =
                                    if (passwordVisualTransformation != VisualTransformation.None) {
                                        VisualTransformation.None
                                    } else {
                                        PasswordVisualTransformation()
                                    }
                            })
                    },
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
//                    colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(),
                    label = { Text(text = "密码") },
                    isError = hasError,
                    placeholder = { Text(text = "请输入密码") },
                    onValueChange = {
                        password = it
                    },
                    interactionSource = passwordInteractionState,
                    visualTransformation = passwordVisualTransformation,

                    )

            }
        }
    }
}

//是否为空
fun invalidInput(email: String, password: String) =
    email.isBlank() || password.isBlank()
