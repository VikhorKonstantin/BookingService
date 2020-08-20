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
    rooms: {
        getAll: () => api.get("/rooms").then((res) => res.data),
        getFreeInRange: (from, to) =>
            api.get(`/rooms?from=${from}&to=${to}`).then((res) => res.data),
        saveRoom: (data) =>
            api
                .post("/rooms", {
                    type: data.type,
                })
                .then((res) => res.data),
    },
    bookings: {
        bookRoom: (data, user) =>
            api
                .post("/rooms", {
                    roomId: data.roomId,
                    userId: user.id,
                    start: data.from,
                    end: data.to,
                    eventDescription: data.description,
                })
                .then((res) => res.data),
    },
}
