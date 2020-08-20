import React from 'react'
import PropTypes from 'prop-types';
import Container from 'react-bootstrap/Container'
import Card from 'react-bootstrap/Card'
import welcome from './welcome.png'
import { flexRowCenter, formItem, imgItem } from './AuthComponentsStyles.js'


function AuthPanel(props) {
    return (
        <Container style={flexRowCenter}>
            <div style={formItem}>
                {props.children}
            </div>
            <div style={imgItem}>
                <Card>
                    <Card.Img  src={welcome} />
                </Card>
            </div>
        </Container>
    )
}

AuthPanel.propTypes = {
    children: PropTypes.oneOfType([
        PropTypes.arrayOf(PropTypes.node),
        PropTypes.node
    ]).isRequired
}

export default AuthPanel;