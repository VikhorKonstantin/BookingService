import React, { useState } from "react"
import Alert from "react-bootstrap/Alert"
import PropTypes from "prop-types"

const FormattedAlert = ({ header, message, variant = "danger" }) => {
    const [show, setShow] = useState(true)
    return (
        <div style={{ display: "flex", justifyContent: "center" }}>
            {show && (
                <Alert
                    variant={variant}
                    onClose={() => setShow(false)}
                    dismissible
                >
                    <Alert.Heading>{header}</Alert.Heading>
                    <p>{message} </p>
                </Alert>
            )}
        </div>
    )
}

FormattedAlert.prototype = {
    header: PropTypes.string.isRequierd,
    message: PropTypes.string.isRequierd,
    variant: PropTypes.string,
}

export default FormattedAlert
