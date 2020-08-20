import { combineReducers } from "redux"
import user from "./reducers/user"
import rooms from "./reducers/rooms"

export default combineReducers({
    user: user,
    rooms: rooms,
})
