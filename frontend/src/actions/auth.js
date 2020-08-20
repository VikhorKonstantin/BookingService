import { USER_LOGGED_IN, USER_LOGGED_OUT } from "../types/types"
import api from "../api/api"
import { setAuthorizationHeader } from "../utils/RequestUtils"

export const userLoggedIn = (user) => ({
    type: USER_LOGGED_IN,
    user,
})

export const userLoggedOut = () => ({
    type: USER_LOGGED_OUT,
})

export const login = (credentials) => (dispatch) =>
    api.user.login(credentials).then((user) => {
        setAuthorizationHeader(user.access_token)
        localStorage.access_token = user.access_token
        localStorage.refresh_token = user.refresh_token
        dispatch(userLoggedIn(user))
    })

export const signup = (credentials) => () => api.user.signup(credentials)

export const logout = () => (dispatch) => {
    localStorage.removeItem("access_token")
    localStorage.removeItem("refresh_token")
    localStorage.removeItem("cart_ids")
    localStorage.removeItem("searchInfo")
    setAuthorizationHeader()
    dispatch(userLoggedOut())
}
