package app.game.login

data class LoginUiState(
    val username: String? = null,
    val password: String? = null,
    val storedUsername: String? = null,
    val storedPassword:String? = null,
)