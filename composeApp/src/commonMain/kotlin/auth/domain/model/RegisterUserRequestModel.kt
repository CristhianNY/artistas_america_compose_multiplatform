package auth.domain.model

data class RegisterUserRequestModel(val name: String, val lastName:String, val email: String, val password: String)