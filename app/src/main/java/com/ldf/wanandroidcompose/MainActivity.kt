package com.ldf.wanandroidcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.ldf.wanandroidcompose.ui.home.PalletMenu
import com.ldf.wanandroidcompose.ui.theme.AppThemeState
import com.ldf.wanandroidcompose.ui.theme.ColorPallet
import com.ldf.wanandroidcompose.ui.theme.SystemUiController
import com.ldf.wanandroidcompose.ui.theme.WanAndroidComposeTheme
import com.ldf.wanandroidcompose.ui.utils.TestTags
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainAppContent(appTheme)
            }

        }
    }
}

@Composable
fun BaseView(
    appThemeState: AppThemeState,
    systemUiController: SystemUiController?,
    content: @Composable () -> Unit
) {
    WanAndroidComposeTheme(
        darkTheme = appThemeState.darkTheme,
        colorPallet = appThemeState.pallet
    ) {
        systemUiController?.setStatusBarColor(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            darkIcons = appThemeState.darkTheme
        )
        content()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainAppContent(appThemeState: MutableState<AppThemeState>) {
    val homeScreenState = rememberSaveable { mutableStateOf(BottomNavType.HOME) }
    val bottomNavBarContentDescription = stringResource(id = R.string.a11y_bottom_navigation_bar)
    val chooseColorBottomModalState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = chooseColorBottomModalState,
        sheetContent = {
            //Modal used only when user use talkback for the sake of accessibility
            PalletMenu(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) { newPalletSelected ->
                appThemeState.value = appThemeState.value.copy(pallet = newPalletSelected)
                coroutineScope.launch {
                    chooseColorBottomModalState.hide()
                }
            }
        }) {
        //设备配置
        val config = LocalConfiguration.current
        val orientation = config.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Column {
                HomeScreenContent(
                    homeScreen = homeScreenState.value,
                    appThemeState = appThemeState,
                    chooseColorBottomModalState = chooseColorBottomModalState,
                    modifier = Modifier.weight(1f)
                )
                BottomNavigationContent(
                    modifier = Modifier
                        .semantics { contentDescription = bottomNavBarContentDescription }
                        .testTag(TestTags.BOTTOM_NAV_TEST_TAG),
                    homeScreenState = homeScreenState
                )
            }
        } else {
            Row(modifier = Modifier.fillMaxSize()) {
                NavigationRailContent(
                    modifier = Modifier
                        .semantics { contentDescription = bottomNavBarContentDescription }
                        .testTag(TestTags.BOTTOM_NAV_TEST_TAG),
                    homeScreenState = homeScreenState
                )
                HomeScreenContent(
                    homeScreen = homeScreenState.value,
                    appThemeState = appThemeState,
                    chooseColorBottomModalState = chooseColorBottomModalState,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }


}

@Composable
fun NavigationRailContent(modifier: Modifier, homeScreenState: MutableState<BottomNavType>) {

}

@Composable
fun BottomNavigationContent(modifier: Modifier, homeScreenState: MutableState<BottomNavType>) {

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    homeScreen: BottomNavType,
    appThemeState: Any,
    chooseColorBottomModalState: ModalBottomSheetState,
    modifier: Modifier
) {
    TODO("Not yet implemented")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val appThemeState = remember { mutableStateOf(AppThemeState(false, ColorPallet.GREEN)) }
    BaseView(appThemeState.value, null) {
        MainAppContent(appThemeState)
    }
}