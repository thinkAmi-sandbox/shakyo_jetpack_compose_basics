package com.example.basiccodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basiccodelab.ui.theme.BasicCodeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicCodeLabTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    // 様々な画面を表示するロジックを追加し、状態をホイストする

    // by は毎回 .value を入力する手間を省くためのプロパティデリゲート
    // rememberはコンポーザブルがComposition内で保持されている場合のみ機能する
    // 画面回転させるとアクティビティ全体が再起動され、すべての状態が失われる
    // 回転などでも状態を保持するには、 rememberSaveable を使う
    var shouldShowOnbording by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnbording) {
        // イベントが発生したときに通知するためのコールバックを渡す
        // 状態ではなく関数を渡すことで、コンポーサブルを再利用しやすく、
        // かつ、他のコンポーザブルによって変更されないよう、状態を保護する
        OnboardingScreen(onContinueClicked = { shouldShowOnbording = false })
    } else {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),

                // クリックしたらコールバック関数を実行する
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it"}) {
    // LazyColumn を使って、スクロール可能にして画面に見えているぶんだけ描画する
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        // ここの items は androidx.compose.foundation.lazy.items
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview(){
    BasicCodeLabTheme {
        // onContinueClickedに空のラムダ式を渡すことで、なにもしないという挙動になる
        OnboardingScreen(onContinueClicked = {})
    }
}


@Composable
private fun Greeting(name: String) {
    // 状態保存のために remember を使って値を保持する
    // 内部状態 expanded はプライベート変数とみなすこともできる
    val expanded = remember { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))) {
                Text(text = "Hello, ")
                // 番号のテキストに、スタイルをあてる
                Text(text = name, style = MaterialTheme.typography.h4
                        // 番号を極太にする
                    .copy(fontWeight = FontWeight.ExtraBold))
            }
            OutlinedButton(
                // 値ではなく関数を受け取る onClick
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun DefaultPreview() {
    BasicCodeLabTheme {
        MyApp()
    }
}
