import { api } from "../api/api"

export const setAuthorizationHeader = (token = null) => {
    if (token) {
        api.defaults.headers.common["Authorization"] = `Bearer ${token}`
    } else {
        delete api.defaults.headers.common["Authorization"]
    }
}
