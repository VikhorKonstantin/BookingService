import React from "react"

const Footer = () => {
    return (
        <footer
            style={{
                position: "fixed",
                bottom: "0",
                width: "100%",
                zIndex: "9999",
            }}
        >
            <div
                style={{
                    display: "flex",
                    justifyContent: "center",
                    backgroundColor: "#007bff",
                }}
            >
                <span style={{ color: "white" }}>Vikhor test task ©2020</span>
            </div>
        </footer>
    )
}

export default Footer
