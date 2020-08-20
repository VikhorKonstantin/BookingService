import React from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { Route, Redirect } from "react-router-dom"
import userSelector from "../selectors/user"

const CheckAuthRoute = ({
    shouldBeAuthenticated,
    user,
    component: Component,
    ...rest
}) => (
    <Route
        {...rest}
        render={(props) =>
            checkAllowed(shouldBeAuthenticated, user) ? (
                <Component {...props} />
            ) : (
                <Redirect to="/login" />
            )
        }
    />
)

CheckAuthRoute.propTypes = {
    component: PropTypes.func.isRequired,
    admin: PropTypes.bool,
    shouldBeAuthenticated: PropTypes.bool.isRequired,
}

function checkAllowed(shouldBeAuthenticated, user) {
    if (shouldBeAuthenticated === true) {
        return !!user.role && !!user.email
    } else {
        return true
    }
}

function mapStateToProps(state) {
    return {
        user: userSelector(state),
    }
}

export default connect(mapStateToProps)(CheckAuthRoute)
