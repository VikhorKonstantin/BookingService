import React from 'react'
import Spinner from 'react-bootstrap/Spinner'

const LoadingSpinner = () => {
    return (
        <div style={{
            position: 'fixed',
            top: '30%',
            left: '50%'
        }}>
            <Spinner animation='border' role='status'>
                <span className='sr-only'>Loading...</span>
            </Spinner>
        </div>
    )
}

export default LoadingSpinner