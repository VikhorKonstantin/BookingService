import axios from "axios"

export const api = axios.create({
    baseURL: `http://localhost:8080/api/`,
})

export default {
    user: {
        login: (credentials) =>
            api
                .post("/auth/token?grant_type=password", {
                    name: credentials.email,
                    password: credentials.password,
                })
                .then((res) => res.data),
        signup: (userData) =>
            api
                .post("/auth/signup", {
                    name: userData.email,
                    password: userData.password,
                    role: userData.role,
                })
                .then((res) => res.data),
    },
}
