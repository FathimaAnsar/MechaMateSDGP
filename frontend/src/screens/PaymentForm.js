import React, { useState } from 'react';
import { Form, Button, Col } from 'react-bootstrap';

function PaymentForm() {
    const [orderDetails, setOrderDetails] = useState({
        orderId: 'ItemNo12345',
        items: 'Door bell wireless',
        currency: 'LKR',
        amount: '1000',
        firstName: 'Saman',
        lastName: 'Perera',
        email: 'samanp@gmail.com',
        phone: '0771234567',
        address: 'No.1, Galle Road',
        city: 'Colombo',
        country: 'Sri Lanka',
    });

    const backendUrl = 'https://mechamate-413916.el.r.appspot.com/api/v1/super/process-payment';

    const handleSubmit = async (event) => {
        event.preventDefault();

        const response = await fetch(backendUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(orderDetails),
        });

        if (response.ok) {
            const { redirectUrl } = await response.json();
            window.location.href = redirectUrl;
        } else {
            console.error('Failed to process payment');
        }
    };

    const handleChange = (event) => {
        setOrderDetails({
            ...orderDetails,
            [event.target.name]: event.target.value,
        });
    };

    return (
        <Form onSubmit={handleSubmit}>
            <h3>Item Details</h3>
            <Form.Group as={Col} controlId="orderId">
                <Form.Label>Order ID</Form.Label>
                <Form.Control
                    type="text"
                    name="orderId"
                    value={orderDetails.orderId}
                    readOnly
                />
            </Form.Group>
            <Form.Group as={Col} controlId="items">
                <Form.Label>Items</Form.Label>
                <Form.Control
                    type="text"
                    name="items"
                    value={orderDetails.items}
                    onChange={handleChange}
                />
            </Form.Group>
            <Form.Group as={Col} controlId="currency">
                <Form.Label>Currency</Form.Label>
                <Form.Control
                    type="text"
                    name="currency"
                    value={orderDetails.currency}
                    readOnly
                />
            </Form.Group>
            <Form.Group as={Col} controlId="amount">
                <Form.Label>Amount</Form.Label>
                <Form.Control
                    type="text"
                    name="amount"
                    value={orderDetails.amount}
                    readOnly
                />
            </Form.Group>

            <h3>Customer Details</h3>
            <Form.Group as={Col} controlId="firstName">
                <Form.Label>First Name</Form.Label>
                <Form.Control
                    type="text"
                    name="firstName"
                    value={orderDetails.firstName}
                    onChange={handleChange}
                />
            </Form.Group>
            <Form.Group as={Col} controlId="lastName">
                <Form.Label>Last Name</Form.Label>
                <Form.Control
                    type="text"
                    name="lastName"
                    value={orderDetails.lastName}
                    onChange={handleChange}
                />
            </Form.Group>
            <Form.Group as={Col} controlId="email">
                <Form.Label>Email</Form.Label>
                <Form.Control
                    type="email"
                    name="email"
                    value={orderDetails.email}
                    onChange={handleChange}
                />
            </Form.Group>
            <Form.Group as={Col} controlId="phone">
                <Form.Label>Phone</Form.Label>
                <Form.Control
                    type="text"
                    name="phone"
                    value={orderDetails.phone}
                    onChange={handleChange}
                />
            </Form.Group>
            <Form.Group as={Col} controlId="address">
                <Form.Label>Address</Form.Label>
                <Form.Control
                    type="text"
                    name="address"
                    value={orderDetails.address}
                    onChange={handleChange}
                />
            </Form.Group>
            <Form.Group as={Col} controlId="city">
                <Form.Label>City</Form.Label>
                <Form.Control
                    type="text"
                    name="city"
                    value={orderDetails.city}
                    onChange={handleChange}
                />
            </Form.Group>
            <Form.Group as={Col} controlId="country">
                <Form.Label>Country</Form.Label>
                <Form.Control
                    type="text"
                    name="country"
                    value={orderDetails.country}
                    readOnly
                />
            </Form.Group>

            <Button variant="primary" type="submit">Pay Now</Button>
        </Form>
    );
}

export default PaymentForm;
