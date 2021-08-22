package mashup.backend.spring.member.infrastructure.spring.mvc

import mashup.backend.spring.member.application.authentication.TokenService
import mashup.backend.spring.member.presentation.Authenticated
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ConditionalOnWebApplication
@Component
class AuthenticationInterceptor(
    private val jwtService: TokenService<Long>
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (needsAuthentication(handler)) {
            val memberId = authenticate(request)
            request.setAttribute(MEMBER_ID, memberId)
        }
        return true
    }

    /**
     * 인증이 필요한 요청인지 판단
     */
    private fun needsAuthentication(handler: Any): Boolean {
        return if (handler is HandlerMethod) {
            handler.getMethodAnnotation(Authenticated::class.java) != null
                    || handler.beanType.getAnnotation(Authenticated::class.java) != null
        } else {
            false
        }
    }

    /**
     * 인증
     */
    private fun authenticate(request: HttpServletRequest): Long {
        val accessToken = resolveAccessToken(request) ?: throw RuntimeException("AccessToken not found")
        return jwtService.decode(accessToken) ?: throw RuntimeException("AccessToken is not valid")
    }

    /**
     * Authorization: token {AccessToken}
     */
    private fun resolveAccessToken(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_NAME)
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return null
        }
        val matchResult = REGEX_AUTHORIZATION_HEADER.find(authorizationHeader) ?: return null
        return matchResult.groups[1]?.value
    }

    companion object {
        private const val MEMBER_ID = "memberId"
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
        private val REGEX_AUTHORIZATION_HEADER = Regex("^token (.*)$")
    }
}