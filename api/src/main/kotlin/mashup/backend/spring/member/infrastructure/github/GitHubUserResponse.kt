package mashup.backend.spring.member.infrastructure.github

import com.fasterxml.jackson.annotation.JsonProperty

/**
"avatar_url": "https://avatars.githubusercontent.com/u/88278320?v=4",
"bio": null,
"blog": "",
"company": null,
"created_at": "2021-08-01T13:38:54Z",
"email": null,
"events_url": "https://api.github.com/users/mashup-11th-spring-member/events{/privacy}",
"followers": 0,
"followers_url": "https://api.github.com/users/mashup-11th-spring-member/followers",
"following": 0,
"following_url": "https://api.github.com/users/mashup-11th-spring-member/following{/other_user}",
"gists_url": "https://api.github.com/users/mashup-11th-spring-member/gists{/gist_id}",
"gravatar_id": "",
"hireable": null,
"html_url": "https://github.com/mashup-11th-spring-member",
"id": 88278320,
"location": null,
"login": "mashup-11th-spring-member",
"name": null,
"node_id": "MDQ6VXNlcjg4Mjc4MzIw",
"organizations_url": "https://api.github.com/users/mashup-11th-spring-member/orgs",
"public_gists": 0,
"public_repos": 0,
"received_events_url": "https://api.github.com/users/mashup-11th-spring-member/received_events",
"repos_url": "https://api.github.com/users/mashup-11th-spring-member/repos",
"site_admin": false,
"starred_url": "https://api.github.com/users/mashup-11th-spring-member/starred{/owner}{/repo}",
"subscriptions_url": "https://api.github.com/users/mashup-11th-spring-member/subscriptions",
"twitter_username": null,
"type": "User",
"updated_at": "2021-08-01T13:38:54Z",
"url": "https://api.github.com/users/mashup-11th-spring-member"
 */
data class GitHubUserResponse(
        @JsonProperty("avatar_url")
        val avatarUrl: String, // "https://avatars.githubusercontent.com/u/88278320?v=4"
        val email: String?,
        val id: String,
        val login: String,
)