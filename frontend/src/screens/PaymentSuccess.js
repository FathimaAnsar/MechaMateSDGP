import React from 'react';
import { Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

function PaymentSuccess() {
    const navigate = useNavigate();

    const handleGoHome = () => {
        navigate('/');
    };

    return (
        <div>
            <h1>Payment Successful!</h1>
            <p>Your payment was processed successfully. Thank you for your purchase.</p>
            <Button onClick={handleGoHome}>Go to Dashboard</Button>
        </div>
    );
}

export default PaymentSuccess;
