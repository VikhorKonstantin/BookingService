import { ROOMS_FETCHED, ROOM_SAVED } from "../types/types"

export default function user(state = [], action) {
    switch (action.type) {
        case ROOMS_FETCHED:
            return action.data
        case ROOM_SAVED:
            return [...state, action.data]
        default:
            return state
    }
}
