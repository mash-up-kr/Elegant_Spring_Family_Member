package mashup.backend.spring.member.infrastructure.github

import com.fasterxml.jackson.annotation.JsonProperty

data class GitHubAccessTokenRequest(
        /**
         * The client ID you received from GitHub for your OAuth App. (Required.)
         */
        @JsonProperty("client_id")
        val clientId: String,
        /**
         * The client secret you received from GitHub for your OAuth App. (Required.)
         */
        @JsonProperty("client_secret")
        val clientSecret: String,
        /**
         * The code you received as a response to Step 1. (Required.)
         */
        val code: String,
        /**
         * The URL in your application where users are sent after authorization.
         */
        @JsonProperty("redirect_uri")
        val redirectUri: String? = null
)