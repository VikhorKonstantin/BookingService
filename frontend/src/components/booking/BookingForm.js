import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import { Form, Button, Container, Card } from "react-bootstrap"
import DateTime from "react-datetime"
import "react-datetime/css/react-datetime.css"
import LoadingSpinner from "../commons/LoadingSpinner"
import RoomItemList from "../room/RoomItemList"
import { fetchFreeRooms } from "../../actions/data"
import selectAllRooms from "../../selectors/rooms"
import userSelector from "../../selectors/user"
import FormattedAlert from "../commons/messages/FormattedAlert"

var renderers = {
    renderDay: function (props, currentDate, selectedDate) {
        return <td {...props}>{currentDate.date()}</td>
    },
    renderMonth: function (props, month, year, selectedDate) {
        return <td {...props}>{month}</td>
    },
    renderYear: function (props, year, selectedDate) {
        return <td {...props}>{year % 100}</td>
    },
}

class BookingForm extends Component {
    state = {
        step: 1,
        from: null,
        to: null,
        isLoading: false,
        chosenRoom: null,
        eventDescr: "",
    }

    onChange = (e) => {
        this.setState({ eventDescr: e.target.value })
    }

    onDateTimeChange = (moment) => {
        console.log(moment)
        const { step, from } = this.state
        if (step === 1) {
            this.setState({ step: 2, from: moment })
        } else {
            this.setState({ step: 3, to: moment, isLoading: true })
            this.props
                .fetchFreeRooms(from, moment)
                .then(() => this.setState({ isLoading: false }))
                .catch((err) =>
                    this.setState({ errors: err, isLoading: false })
                )
        }
    }

    chooseRoom = (room) => {
        this.setState({ step: 4, chosenRoom: room })
    }

    onSubmit = (e) => {
        this.props.onSubmit({
            roomId: this.state.chosenRoom.id,
            userId: this.props.user.id,
        })
    }

    render() {
        const { step, chosenRoom, eventDescr } = this.state
        const flexCenter = {
            display: "flex",
            justifyContent: "center",
            alignContent: "center",
        }
        const disabled = step !== 4
        const rooms = this.props.rooms
        return (
            <Form onSubmit={this.onSubmit}>
                {step < 3 ? (
                    <Form.Group>
                        <Card style={{ width: "30rem" }}>
                            <Card.Body>
                                <Card.Title style={flexCenter}>
                                    Please, select {step === 1 ? "FROM" : "TO"}{" "}
                                    date and time
                                </Card.Title>
                                <Card.Text style={flexCenter}>
                                    <DateTime
                                        input={false}
                                        renderDay={renderers.renderDay}
                                        renderMonth={renderers.renderMonth}
                                        renderYear={renderers.renderYear}
                                        onChange={this.onDateTimeChange}
                                    />
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </Form.Group>
                ) : !this.state.isLoading ? (
                    <Form.Group style={flexCenter}>
                        {rooms.length !== 0 ? (
                            <RoomItemList
                                chooseRoom={chosenRoom}
                                onItemClick={this.chooseRoom}
                                rooms={rooms}
                            />
                        ) : (
                            <FormattedAlert
                                header={"Ooops"}
                                message={
                                    "Sorry, no rooms available in the given range"
                                }
                            />
                        )}
                    </Form.Group>
                ) : (
                    <LoadingSpinner />
                )}
                <Form.Group>
                    <Form.Label>Event description</Form.Label>
                    <Form.Control
                        id="edr"
                        name="edr"
                        placeholder={"Describe the event, please"}
                        value={eventDescr}
                        onChange={this.onChange}
                    />
                </Form.Group>
                <Container style={flexCenter}>
                    <Button variant="primary" type="submut" disabled={disabled}>
                        Book
                    </Button>
                </Container>
            </Form>
        )
    }
}

BookingForm.propTypes = {
    rooms: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.number.isRequired,
            type: PropTypes.string.isRequired,
        })
    ).isRequired,
    onSubmit: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => {
    return {
        rooms: selectAllRooms(state),
        user: userSelector(state),
    }
}

export default connect(mapStateToProps, { fetchFreeRooms })(BookingForm)
