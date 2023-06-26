package com.ldf.wanandroidcompose.ui.widget

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

/**
 * @Author : dongfang
 * @Created Time : 2023-06-26  13:44
 * @Description:
 */

@Composable
fun SimpleAlertDialog(
    title: String,
    contextStr: String = "",
    dismissStr: String = "取消",
    confirmStr: String = "确定",
    textCompose: @Composable (() -> Unit)? = null,
    confirmClick: () -> Unit = {},
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(title)
        },
        text = {
            if (textCompose == null)
                Text(contextStr)
            else textCompose()
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissStr)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                confirmClick()
                onDismiss()
            }) {
                Text(confirmStr)
            }
        })
}