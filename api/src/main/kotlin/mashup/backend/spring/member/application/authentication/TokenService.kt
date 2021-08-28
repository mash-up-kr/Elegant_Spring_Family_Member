package mashup.backend.spring.member.application.authentication

interface TokenService<T> {
    fun encode(memberId: T): String
    fun decode(token: String?): T?
}