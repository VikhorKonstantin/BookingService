import React, { Component } from "react"
import PropTypes from "prop-types"
import Form from "react-bootstrap/Form"
import Validator from "validator"
import InlineError from "../commons/messages/InlineError"
import { Button, Container } from "react-bootstrap"
import { Link } from "react-router-dom"
import { flexRowSpaceBetween } from "./AuthComponentsStyles"

class LoginForm extends Component {
    state = {
        data: {
            email: { value: "", shouldValidate: false },
            password: { value: "", shouldValidate: false },
        },
        disabled: true,
        errors: {},
    }

    onChange = (e) => {
        const changed = {
            ...this.state.data,
            [e.target.name]: { value: e.target.value, shouldValidate: true },
        }
        this.setState({
            data: changed,
        })
        const errors = this.validate(changed)
        this.setState({
            disabled: Object.keys(errors).length !== 0,
            errors: errors,
        })
    }

    onSubmit = (e) => {
        const data = this.state.data
        const errors = this.validate(data)
        this.setState({ errors })
        if (Object.keys(errors).length === 0) {
            this.props.submit({
                email: data.email.value,
                password: data.password.value,
            })
        }
        e.preventDefault()
    }

    validate = (data) => {
        const errors = {}
        const { email, password } = data
        if (email.shouldValidate && !Validator.isEmail(email.value))
            errors.email = "Invalid email"
        if (email.shouldValidate && email.value.length > 30)
            errors.email = "Email should be not longer than 30"
        if (password.shouldValidate && isBlank(password.value))
            errors.password = "Password should be non-blanck"
        if (password.shouldValidate && password.value.length < 4)
            errors.password = "Password length should be at least 4"
        return errors
    }

    render() {
        const { data, errors, disabled } = this.state
        return (
            <Form onSubmit={this.onSubmit}>
                <Form.Group>
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        type="email"
                        id="email"
                        name="email"
                        placeholder="Email"
                        value={data.email.value}
                        onChange={this.onChange}
                    />
                    {errors.email && <InlineError text={errors.email} />}
                </Form.Group>
                <Form.Group>
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        id="password"
                        name="password"
                        placeholder={"Password"}
                        value={data.password.value}
                        onChange={this.onChange}
                    />
                    {errors.password && <InlineError text={errors.password} />}
                </Form.Group>
                <Container style={flexRowSpaceBetween}>
                    <Button variant="primary" type="submut" disabled={disabled}>
                        Log in
                    </Button>
                    <Link to="/certificates" className="btn btn-primary">
                        Cancel
                    </Link>
                </Container>
            </Form>
        )
    }
}

function isBlank(str) {
    return !str || /^\s*$/.test(str)
}

LoginForm.propTypes = {
    submit: PropTypes.func.isRequired,
}

export default LoginForm
