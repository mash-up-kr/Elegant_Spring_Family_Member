package mashup.backend.spring.member.infrastructure.naver

import com.fasterxml.jackson.annotation.JsonProperty
import mashup.backend.spring.member.domain.oauth.OAuthAccessToken
import mashup.backend.spring.member.domain.oauth.OAuthUser

// response

data class NaverAccessTokenResponse(
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("refresh_token")
        val refreshToken: String,
        @JsonProperty("token_type")
        val tokenType: String,
        @JsonProperty("expires_in")
        val expiresIn: String
)

/*
naver user response example
---
{
  "resultcode": "00",
  "message": "success",
  "response": {
    "email": "openapi@naver.com",
    "nickname": "OpenAPI",
    "profile_image": "https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif",
    "age": "40-49",
    "gender": "F",
    "id": "32742776",
    "name": "오픈 API",
    "birthday": "10-01"
  }
}
---
 */
data class NaverUserResponse(
        val message: String,
        val resultcode: String,
        @JsonProperty("response")
        val response: NaverUserDetailResponse
)
data class NaverUserDetailResponse(
        @JsonProperty("profile_image")
        val profileImage: String?,
        val email: String,
        val id: String,
        val name: String,
)

// data

data class NaverAccessToken(
        override val accessToken: String,
        val refershToken: String,
        val expireIn: Long
) : OAuthAccessToken

data class NaverUser(
        override val id: String,
        override val name: String?,
        override val email: String?,
        override val profileImageUrl: String?,
        override val details: Map<String, Any>
) : OAuthUser