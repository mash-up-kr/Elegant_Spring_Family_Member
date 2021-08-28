package mashup.backend.spring.member.domain.oauth

interface OAuthUser {
    val id: String
    val name: String?
    val email: String?
    val profileImageUrl: String?
    val details: Map<String, Any>
}