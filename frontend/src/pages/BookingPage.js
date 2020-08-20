import React from "react"
import { useDispatch } from "react-redux"
import PropTypes from "prop-types"
import NameHeader from "../components/commons/NameHeader"
import Footer from "../components/commons/Footer"
import HeadNavBar from "../components/commons/HeadNavBar"
import BookingForm from "../components/booking/BookingForm"

const BookingPage = ({ history }) => {
    const bookRoom = (booking) => {}
    return (
        <div>
            <div className="fixed_nav">
                <HeadNavBar history={history} />
            </div>
            <NameHeader />
            <div
                style={{ display: "flex", justifyContent: "center" }}
                className="main_content">
                <BookingForm />
            </div>
            <Footer />
        </div>
    )
}

BookingPage.propTypes = {
    history: PropTypes.shape({
        push: PropTypes.func.isRequired,
    }).isRequired,
}

export default BookingPage
