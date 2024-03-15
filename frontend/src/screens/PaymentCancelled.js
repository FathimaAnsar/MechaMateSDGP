
import React from 'react';
import { Button } from 'react-bootstrap';
import { useHistory } from 'react-router-dom';

function PaymentCancelled() {
    const history = useHistory();

    const handleRetryPayment = () => {
        history.goBack();
    };

    return (
        <div>
            <h1>Payment Cancelled</h1>
            <p>You have cancelled your payment. If this was a mistake, you can attempt to pay again.</p>
            <Button onClick={handleRetryPayment}>Retry Payment</Button>
        </div>
    );
}

export default PaymentCancelled;
