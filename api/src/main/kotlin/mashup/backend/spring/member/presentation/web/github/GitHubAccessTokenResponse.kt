package mashup.backend.spring.member.presentation.web.github

import com.fasterxml.jackson.annotation.JsonProperty

data class GitHubAccessTokenResponse(
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("token_type")
        val tokenType: String,
        val scope: String
)