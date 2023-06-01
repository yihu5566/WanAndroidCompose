package com.ldf.wanandroidcompose.ui.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.ldf.wanandroidcompose.ui.theme.AppThemeState
import com.ldf.wanandroidcompose.ui.theme.ColorPallet

/**
 * @Author : dongfang
 * @Created Time : 2023-05-31  17:29
 * @Description:
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    appThemeState: MutableState<AppThemeState>,
    chooseColorBottomModalState: ModalBottomSheetState
) {

    Text(text = "我是首页")
}

@Composable
fun PalletMenu(
    modifier: Modifier,
    onPalletChange: (ColorPallet) -> Unit
) {
}