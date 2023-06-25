package com.ldf.wanandroidcompose.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.LogUtils
import com.google.accompanist.insets.statusBarsHeight
import com.guru.fontawesomecomposelib.FaIcon
import com.ldf.wanandroidcompose.R
import com.ldf.wanandroidcompose.ui.widget.HorizontalDottedProgressBar
import com.ldf.wanandroidcompose.ui.widget.lottie.LottieWorkingLoadingView

/**
 * @Author : dongfang
 * @Created Time : 2023-06-21  15:51
 * @Description:
 */
@Composable
fun LoginScreen(navHostController: NavHostController) {

    Scaffold(backgroundColor = Color.White) { paddingValues ->
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
                        Color.Black,
                        fontSize = 16.sp
                    )
                )
            }
            item { LottieWorkingLoadingView(context = LocalContext.current) }
            item {
                Text(
                    text = "欢迎加入我们",
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
            //登录按钮
            item {
                var loading by remember { mutableStateOf(false) }
                val viewModel: LoginViewModel = viewModel()
                Button(
                    onClick = {
                        if (invalidInput(userName.text, password.text)) {
                            hasError = true
                            loading = false
                        } else {
                            loading = true
                            hasError = false
                            viewModel.userLogin(userName.text, password.text) {
                                LogUtils.d("登录成功")
                                loading = false
                                navHostController.navigateUp()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                ) {
                    if (loading) {
                        HorizontalDottedProgressBar()
                    } else {
                        Text(text = "登录")
                    }
                }
            }
            item {
                Box(modifier = Modifier.padding(vertical = 16.dp)) {
                    Spacer(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                    Text(
                        text = "三方登录",
                        color = Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(MaterialTheme.colors.background)
                            .padding(horizontal = 16.dp)
                    )
                }
            }
            item {
                OutlinedButton(
                    onClick = { }, modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(
                        painterResource(R.mipmap.ic_shezhi), "",
                        tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                    )
                    Text(
                        text = "登录微信",
                        style = MaterialTheme.typography.h4.copy(fontSize = 14.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                val primaryColor = MaterialTheme.colors.primary
                val annotatedString = remember {
                    AnnotatedString.Builder("没有账户，去注册？")
                        .apply {
                            addStyle(style = SpanStyle(color = primaryColor), 23, 31)
                        }
                }
                Text(
                    text = annotatedString.toAnnotatedString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable(onClick = {}),
                    textAlign = TextAlign.Center
                )
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

//是否为空
fun invalidInput(email: String, password: String) =
    email.isBlank() || password.isBlank()
