package mashup.backend.spring.member.infrastructure.github

import mashup.backend.spring.member.domain.oauth.OAuthAccessToken

data class GitHubAccessToken(
        override val accessToken: String
) : OAuthAccessToken