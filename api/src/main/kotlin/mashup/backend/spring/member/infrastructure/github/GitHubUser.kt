package mashup.backend.spring.member.infrastructure.github

import mashup.backend.spring.member.domain.oauth.OAuthUser

data class GitHubUser(
        override val id: String,
        override val name: String?,
        override val email: String?,
        override val profileImageUrl: String?,
        override val details: Map<String, Any>
) : OAuthUser