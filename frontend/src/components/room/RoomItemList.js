import React from "react"
import PropTypes from "prop-types"
import RoomItem from "./RoomItem"

export default function RoomItemList({ rooms }) {
    const items = rooms.map((room) => <RoomItem key={room.id} {...room} />)
    return <ul className="room-list">{items}</ul>
}

RoomItemList.propTypes = {
    rooms: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.number.isRequired,
            type: PropTypes.string.isRequired,
        })
    ).isRequired,
}
