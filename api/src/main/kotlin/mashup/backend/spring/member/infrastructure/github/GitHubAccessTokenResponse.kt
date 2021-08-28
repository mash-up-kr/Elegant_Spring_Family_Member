package mashup.backend.spring.member.infrastructure.github

import com.fasterxml.jackson.annotation.JsonProperty

data class GitHubAccessTokenResponse(
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("token_type")
        val tokenType: String,
        val scope: String
)