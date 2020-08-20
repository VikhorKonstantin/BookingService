import React, { Component } from "react"
import PropTypes from "prop-types"
import NameHeader from "../components/commons/NameHeader"
import AuthPanel from "../components/authentication/AuthPanel"
import SignupForm from "../components/authentication/SignupForm"
import { signup } from "../actions/auth"
import { connect } from "react-redux"
import Footer from "../components/commons/Footer"
import FormattedAlert from "../components/commons/messages/FormattedAlert"
import LoadingSpinner from "../components/commons/LoadingSpinner"
import HeadNavBar from "../components/commons/HeadNavBar"

class SignupPage extends Component {
    state = {
        isLoading: false,
        errors: {},
    }
    submit = (data) => {
        this.setState({ isLoading: true })
        this.props
            .signup(data)
            .then(() => this.props.history.push("/login"))
            .catch((err) => {
                this.setState({ errors: err.response.data, isLoading: false })
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
                        <SignupForm submit={this.submit} />
                    </AuthPanel>
                </div>
                <Footer />
            </div>
        ) : (
            <LoadingSpinner />
        )
    }
}

SignupPage.propTypes = {
    history: PropTypes.shape({
        push: PropTypes.func.isRequired,
    }).isRequired,
    signup: PropTypes.func.isRequired,
}

export default connect(null, { signup })(SignupPage)
