import React from "react"
import PropTypes from "prop-types"
import "./room.css"

export default function RoomItem({ id, type, onItemClick, isChosen = false }) {
    return (
        <li
            style={isChosen ? { backgroundColor: "rgb(126, 121, 121)" } : null}
            className="room-list-item"
            key={id}
            onClick={onItemClick({ id, type })}>
            <span className="room-list-item-type">{type}</span>
        </li>
    )
}

RoomItem.propTypes = {
    id: PropTypes.number.isRequired,
    type: PropTypes.string.isRequired,
}
