package mashup.backend.spring.member.domain

enum class MemberStatus {
    /**
     * 정회원
     */
    ACTIVE,

    /**
     * 휴면 회원
     */
    DORMANT,

    /**
     * 탈퇴 회원
     */
    WITHDRAWAL
}