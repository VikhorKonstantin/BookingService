import React, { useState } from "react"
import PropTypes from "prop-types"
import { Card, Form, Button } from "react-bootstrap"
import RoomItemList from "./RoomItemList"

export default function RoomsPanel({ rooms, onSave }) {
    const [type, setType] = useState({
        type: { value: "", shouldValidate: false },
    })
    const onChange = (e) => {
        setType({ value: e.target.value, shouldValidate: true })
        e.preventDefault()
    }
    const onSubmit = (e) => {
        onSave({ type: type.value })
        e.preventDefault()
    }
    const disabled = isBlank(type.value)
    const flexCenter = { display: "flex", justifyContent: "center" }
    return (
        <div>
            <Card style={{ width: "25rem" }}>
                <Card.Body>
                    <Card.Title style={flexCenter}>Rooms</Card.Title>
                    <Card.Text style={flexCenter}>
                        <RoomItemList rooms={rooms} />
                    </Card.Text>
                    <Form onSubmit={onSubmit}>
                        <Form.Group>
                            <Form.Label>Type</Form.Label>
                            <Form.Control
                                id="type"
                                name="type"
                                placeholder={"Type"}
                                value={type.value}
                                onChange={onChange}
                            />
                        </Form.Group>
                        <Button
                            variant="primary"
                            type="submut"
                            disabled={disabled}>
                            Add room
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </div>
    )
}

RoomsPanel.propTypes = {
    rooms: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.number.isRequired,
            type: PropTypes.string.isRequired,
        })
    ).isRequired,
    onSave: PropTypes.func.isRequired,
}

function isBlank(str) {
    return !str || /^\s*$/.test(str)
}
