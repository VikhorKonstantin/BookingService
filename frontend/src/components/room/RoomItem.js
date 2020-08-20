import React from "react"
import PropTypes from "prop-types"
import "./room.css"

export default function RoomItem({ id, type }) {
    return (
        <li className="room-list-item" key={id}>
            <span className="room-list-item-type">{type}</span>
        </li>
    )
}

RoomItem.propTypes = {
    id: PropTypes.number.isRequired,
    type: PropTypes.string.isRequired,
}
