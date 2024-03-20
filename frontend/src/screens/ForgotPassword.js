import React from "react";
import { Pages } from "../Pages.js"
import ConnectionManager from "../services/ConnectionManager.js"
import { Container, Row, Col, Button, Form } from 'react-bootstrap';


function ForgotPassword(props) {

    const handleGoBack = () => {props.app.goBack();}
    const handleSubmit = async (e) => {
        e.preventDefault();

        const email = document.getElementById('email').value;

        // Validate inputs
        // If failed to validate, return here

        let connection = new ConnectionManager();

        const resp = await connection.requestPasswordRecovery(email);
        const response = JSON.parse(resp);

        if(response.error) {
            alert("Error occured: " + response.message + "\n" + response.help);
        }else if(response.status) {
            alert("Success: " + response.message + "\n" + response.info);
            props.app.changePage(Pages.DashboardUI);
        } else {
            alert("Error: Unknown");
        }
    }
    return(
        <Container className="mt-5">
            <Row className="justify-content-md-center">
                <Col md={6}>

                    <h1 className="text-center mb-3">Password Recovery</h1>
                    <p>Please enter your email address below:</p>

                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="email">
                            <Form.Control type="email" placeholder="Enter email" required />
                        </Form.Group>
                        <Button variant="primary" type="submit" className="mt-3">
                            Request Password Reset Link
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );


}



export default ForgotPassword;