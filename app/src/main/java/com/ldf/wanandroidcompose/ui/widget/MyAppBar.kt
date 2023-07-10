package com.ldf.wanandroidcompose.ui.widget

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @Author : dongfang
 * @Created Time : 2023-06-09  16:28
 * @Description:
 */

@Composable
fun AppBar(
    title: String = "",
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = null,
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        elevation = elevation,
        modifier = Modifier.height(54.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                //左边图标
                if (leftIcon == null)
                    Spacer(modifier = Modifier.size(10.dp))
                else
                    Icon(leftIcon, contentDescription = null, modifier = Modifier
                        .clickable {
                            onLeftClick()
                        }
                        .padding(start = 10.dp), tint = MaterialTheme.colors.background)

                //右边图标
                rightIcon?.let {
                    Icon(rightIcon, contentDescription = null,
                        Modifier
                            .clickable {
                                onRightClick()
                            }
                            .padding(end = 10.dp), tint = MaterialTheme.colors.background)
                }
            }
            //标题文字
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = MaterialTheme.colors.background
            )
        }

    }
}

/**
 * 带输入框
 */
@Composable
fun AppBar(
    textFieldValue: TextFieldValue,
    onValueChange: (changeValue: TextFieldValue) -> Unit,
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = null,
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        elevation = elevation,
        modifier = Modifier.height(54.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                //左边图标
                if (leftIcon == null)
                    Spacer(modifier = Modifier.size(10.dp))
                else
                    Icon(leftIcon, contentDescription = null, modifier = Modifier
                        .clickable {
                            onLeftClick()
                        }
                        .padding(start = 10.dp), tint = MaterialTheme.colors.background)

                //右边图标
                rightIcon?.let {
                    Icon(rightIcon, contentDescription = null,
                        Modifier
                            .clickable {
                                onRightClick()
                            }
                            .padding(end = 10.dp), tint = MaterialTheme.colors.background)
                }
            }
            //输入框
            TextField(
                value = textFieldValue,
                onValueChange = { changeValue ->
                    onValueChange(changeValue)
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.primary,
                    cursorColor = MaterialTheme.colors.background,
                    //当输入框处于/不处于焦点时，底部指示器的颜色
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primary
                ),

                label = { Text(text = "请输入内容", color = Color.Gray) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onRightClick()
                    }
                )

            )
        }

    }
}


