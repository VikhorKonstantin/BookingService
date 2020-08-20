import React from "react"
import PropTypes from "prop-types"

const InlineError = ({ text }) => (
    <span style={{ color: "#dc3545" }}>{text}</span>
)

InlineError.prototype = {
    text: PropTypes.string.isRequierd,
}

export default InlineError
