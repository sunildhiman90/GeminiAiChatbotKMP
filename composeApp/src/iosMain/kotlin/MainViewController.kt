import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    AppContent(
        viewModel = HomeViewModel(),
        byteArrayFactory = ByteArrayFactory()
    )
}
