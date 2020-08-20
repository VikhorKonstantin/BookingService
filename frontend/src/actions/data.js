import api from "../api/api"
import { ROOMS_FETCHED, ROOM_SAVED } from "../types/types"

export const roomsFetched = (data) => ({
    type: ROOMS_FETCHED,
    data,
})

export const fetchRooms = () => (dispatch) =>
    api.rooms.getAll().then((data) => dispatch(roomsFetched(data)))

export const fetchFreeRooms = (from, to) => (dispatch) =>
    api.rooms
        .getFreeInRange(
            from.format("yyyy-MM-dd HH:mm:ss"),
            to.format("yyyy-MM-dd HH:mm:ss")
        )
        .then((data) => dispatch(roomsFetched(data)))

export const roomSaved = (data) => ({
    type: ROOM_SAVED,
    data,
})

export const saveRoom = (room) => (dispatch) =>
    api.rooms.saveRoom(room).then((data) => dispatch(roomSaved(data)))

export const bookRoom = (booking, user) => (dispatch) =>
    api.bookings
        .bookRoom(booking, user)
        .then((data) => dispatch(roomSaved(data)))
