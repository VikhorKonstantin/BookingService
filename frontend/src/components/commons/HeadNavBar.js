import React, { Component } from "react"
import PropTypes from "prop-types"
import Navbar from "react-bootstrap/Navbar"
import Nav from "react-bootstrap/Nav"
import Container from "react-bootstrap/Container"
import { connect } from "react-redux"
import * as authActions from "../../actions/auth"
import { Button } from "react-bootstrap"
import userSelector from "../../selectors/user"

class HeadNavBar extends Component {
    state = {
        showModal: false,
        confirmAction: {},
    }

    handleLogOut = () => {
        this.props.logout()
    }

    handleLinkClick = (url) => {
        this.props.history.push(url)
    }

    render() {
        const { user } = this.props
        const isAuthenticated = user.role !== "GUEST"
        const links = isAuthenticated ? (
            <div>
                User's email:
                {user.email}
                <Button variant="btn btn-link" onClick={this.handleLogOut}>
                    Log out
                </Button>
            </div>
        ) : (
            <div>
                <Button
                    variant="btn btn-link"
                    onClick={() => this.handleLinkClick("/login")}>
                    {"Log in "}
                </Button>
                <Button
                    variant="btn btn-link"
                    onClick={() => this.handleLinkClick("/signup")}>
                    Sign up
                </Button>
            </div>
        )
        return (
            <Navbar bg="light" variant="light">
                <Container>
                    <Nav>
                        <Button
                            variant="btn btn-link"
                            onClick={() => this.handleLinkClick("/bookings")}>
                            Bookings
                        </Button>
                        <Button
                            variant="btn btn-link"
                            onClick={() => this.handleLinkClick("/rooms")}>
                            Rooms
                        </Button>
                    </Nav>
                    <Nav>{links}</Nav>
                </Container>
            </Navbar>
        )
    }
}

HeadNavBar.protoTypes = {
    user: PropTypes.shape({
        role: PropTypes.oneOf(["ADMIN", "USER", "GUEST"]),
        email: PropTypes.string.isRequired,
    }).isRequired,
    logout: PropTypes.func.isRequired,
    history: PropTypes.shape({
        push: PropTypes.func.isRequired,
    }).isRequired,
}

function mapStateToProps(state) {
    return {
        user: userSelector(state),
    }
}

export default connect(mapStateToProps, {
    logout: authActions.logout,
})(HeadNavBar)
