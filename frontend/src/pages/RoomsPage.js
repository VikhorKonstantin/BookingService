import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import { fetchRooms, saveRoom } from "../actions/data"
import NameHeader from "../components/commons/NameHeader"
import Footer from "../components/commons/Footer"
import LoadingSpinner from "../components/commons/LoadingSpinner"
import FormattedAlert from "../components/commons/messages/FormattedAlert"
import HeadNavBar from "../components/commons/HeadNavBar"
import selectAllRooms from "../selectors/rooms"
import RoomsPanel from "../components/room/RoomsPanel"

class RoomsPage extends Component {
    state = {
        isLoading: false,
        errors: {},
    }

    onRoomSave = (room) => {
        this.setState({ isLoading: true })
        this.props
            .saveRoom(room)
            .then(() => this.setState({ isLoading: false }))
            .catch((err) => this.setState({ errors: err, isLoading: false }))
    }

    componentDidMount() {
        this.setState({ isLoading: true })
        this.props
            .fetchRooms()
            .then(() => this.setState({ isLoading: false }))
            .catch((err) => this.setState({ errors: err, isLoading: false }))
    }

    render() {
        const flexCenter = { display: "flex", justifyContent: "center" }
        return !this.state.isLoading ? (
            <div>
                <div className="fixed_nav">
                    <HeadNavBar history={this.props.history} />
                </div>
                <NameHeader />
                <div className="main_content" style={flexCenter}>
                    {this.state.errors.message && (
                        <FormattedAlert
                            header={"Error"}
                            message={this.state.errors.message}
                        />
                    )}
                    <RoomsPanel
                        onSave={this.onRoomSave}
                        style={flexCenter}
                        rooms={this.props.rooms}
                    />
                </div>
                <Footer />
            </div>
        ) : (
            <LoadingSpinner />
        )
    }
}

RoomsPage.propTypes = {
    rooms: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.number.isRequired,
            type: PropTypes.string.isRequired,
        })
    ).isRequired,
    fetchRooms: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => {
    return {
        rooms: selectAllRooms(state),
    }
}

export default connect(mapStateToProps, { fetchRooms, saveRoom })(RoomsPage)
