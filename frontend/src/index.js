import React from "react"
import ReactDOM from "react-dom"
import { BrowserRouter, Route } from "react-router-dom"
import { Provider } from "react-redux"
import { createStore, applyMiddleware } from "redux"
import thunk from "redux-thunk"
import { composeWithDevTools } from "redux-devtools-extension"
import rootReducer from "./rootReducer"
import "./index.css"
import "bootstrap/dist/css/bootstrap.min.css"
import App from "./App"
import { setAuthorizationHeader } from "./utils/RequestUtils"
import { userLoggedIn } from "./actions/auth"

const store = createStore(
    rootReducer,
    composeWithDevTools(applyMiddleware(thunk))
)
if (localStorage.access_token) {
    const user = {
        access_token: localStorage.access_token,
        refresh_token: localStorage.refresh_token,
    }
    setAuthorizationHeader(localStorage.access_token)
    store.dispatch(userLoggedIn(user))
}
ReactDOM.render(
    <BrowserRouter>
        <Provider store={store}>
            <Route component={App} />
        </Provider>
    </BrowserRouter>,
    document.getElementById("root")
)
