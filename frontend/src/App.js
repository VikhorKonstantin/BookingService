import React from "react"
import PropTypes from "prop-types"
import "./pages/page.css"
import { Redirect, Route } from "react-router-dom"
import LoginPage from "./pages/LoginPage"
import SignupPage from "./pages/SignupPage"
import CheckAuthRoute from "./routes/ChechAuthRoute"

function App({ location }) {
    return (
        <div className="App">
            <Route exact location={location} path="/">
                <Redirect to="/login" />
            </Route>
            <CheckAuthRoute
                shouldBeAuthenticated={false}
                location={location}
                path="/login"
                exact
                component={(props) => <LoginPage {...props} />}
            />
            <CheckAuthRoute
                shouldBeAuthenticated={false}
                location={location}
                path="/signup"
                exact
                component={(props) => <SignupPage {...props} />}
            />
        </div>
    )
}

App.propTypes = {
    location: PropTypes.shape({
        pathname: PropTypes.string.isRequired,
    }).isRequired,
}

export default App
