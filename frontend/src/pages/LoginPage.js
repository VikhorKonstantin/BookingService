import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import { login } from "../actions/auth"
import NameHeader from "../components/commons/NameHeader"
import AuthPanel from "../components/authentication/AuthPanel"
import LoginForm from "../components/authentication/LoginForm"
import Footer from "../components/commons/Footer"
import LoadingSpinner from "../components/commons/LoadingSpinner"
import FormattedAlert from "../components/commons/messages/FormattedAlert"
import HeadNavBar from "../components/commons/HeadNavBar"

class LoginPage extends Component {
    state = {
        isLoading: false,
        errors: {},
    }
    submit = (data) => {
        this.setState({ isLoading: true })
        this.props
            .login(data)
            .then(() => this.props.history.push("/booking"))
            .catch((err) => {
                this.setState({ errors: err, isLoading: false })
            })
    }

    render() {
        return !this.state.isLoading ? (
            <div>
                <div className="fixed_nav">
                    <HeadNavBar history={this.props.history} />
                </div>
                <NameHeader />
                <div className="main_content">
                    <AuthPanel>
                        {this.state.errors.message && (
                            <FormattedAlert
                                header="Authentication error"
                                message="Try again, please"
                            />
                        )}
                        <LoginForm submit={this.submit} />
                    </AuthPanel>
                </div>
                <Footer />
            </div>
        ) : (
            <LoadingSpinner />
        )
    }
}

LoginPage.propTypes = {
    history: PropTypes.shape({
        push: PropTypes.func.isRequired,
    }).isRequired,
    login: PropTypes.func.isRequired,
}

export default connect(null, { login })(LoginPage)
