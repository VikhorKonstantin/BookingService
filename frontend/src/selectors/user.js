import decode from "jwt-decode"

const extractRole = (user) => {
    if (!user.access_token) return "GUEST"
    const roles = decode(user.access_token).roles
    if (roles.find((t) => t === "ROLE_ADMIN")) return "ADMIN"
    return "USER"
}

const extractEmail = (user) => {
    if (!user.access_token) return ""
    return decode(user.access_token).sub
}

export default function userSelector(state) {
    const user = state.user
    return {
        ...user,
        role: extractRole(user),
        email: extractEmail(user),
    }
}
