import React from "react"
import Container from "react-bootstrap/Container"

function NameHeader() {
    const headerStyle = {
        justifyContent: "center",
        display: "flex",
        margin: "5rem 10rem 10rem 5rem",
    }
    return (
        <Container>
            <h1 style={headerStyle}>Vikhor test task</h1>
        </Container>
    )
}

export default NameHeader
