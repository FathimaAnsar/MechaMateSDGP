import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button, Form, Alert } from 'react-bootstrap';
import ConnectionManager from '../services/ConnectionManager';
import { Pages } from "../Pages";
import Header from "./components/Header"; // If you have a Header component

function ParkingFinder(props) {
    const [currentLocation, setCurrentLocation] = useState(null);
    const [parkingSpaces, setParkingSpaces] = useState([]);
    const [varCap, setVarCap] = useState("");
    const [loading, setLoading] = useState(false);
    const [alertInfo, setAlertInfo] = useState({ show: false, message: '' });

    useEffect(() => {
        props.app.getCurrentLocation().then(location => {
            setCurrentLocation(location);
        }).catch(exp => {
            displayAlert(exp.message || "Failed to get location information");
            props.app.changePage(Pages.DashboardUI);
        });
    }, [props.app]);

    const displayAlert = (message) => {
        setAlertInfo({ show: true, message });
        setTimeout(() => setAlertInfo({ show: false, message: '' }), 5000);
    };

    const fetchNearbyParking = async () => {
        setLoading(true);
        const limit = document.getElementById('limit').value;
        const radius = document.getElementById('radius').value;

        // Include validation for limit and radius as needed

        let connection = new ConnectionManager();

        try {
            const resp = await connection.getNearbyParking(currentLocation.latitude, currentLocation.longitude, radius, limit);
            const response = JSON.parse(resp);
            console.log(response)

            if (response.error) {
                displayAlert(`Error occurred: ${response.message}`);
            } else {
                setVarCap("Parking Spaces");
                setParkingSpaces(response.places);
            }
        } catch (error) {
            displayAlert("Please check your Spring Boot service is running");
        } finally {
            setLoading(false);
        }
    };

    const handleGoBack = () => {
        props.app.goBack();
    };

    return (
        <>
            <Header app={props.app}/> {/* If you have a Header component */}
            <Container>
                <Row className="mt-3">
                    <Col>
                        <h2>Finding a parking place!</h2>
                        <h4>You are here!</h4>
                        <iframe
                            title="map"
                            src={`https://maps.google.com/maps?q=${currentLocation ? currentLocation.latitude : 6.899752439069889},${currentLocation ? currentLocation.longitude : 79.85362275600751}&z=15&output=embed`}
                            width="100%"
                            height="300"
                            frameBorder="0"
                            style={{ border: 0 }}
                            allowFullScreen=""
                        ></iframe>
                    </Col>
                </Row>

                <Row className="my-3">
                    <Col xs={12} md={6}>
                        <Form.Group controlId="limit">
                            <Form.Label>Number of Results</Form.Label>
                            <Form.Control as="select" defaultValue="5">
                                <option value="5">Show only 5 results</option>
                                <option value="10">Show only 10 results</option>
                                <option value="15">Show only 15 results</option>
                                <option value="20">Show only 20 results</option>
                                <option value="25">Show only 25 results</option>
                            </Form.Control>
                        </Form.Group>
                    </Col>
                    <Col xs={12} md={6}>
                        <Form.Group controlId="radius">
                            <Form.Label>Search Radius</Form.Label>
                            <Form.Control as="select" defaultValue="500">
                                <option value="500">Within 500 meters</option>
                                <option value="1000">Within 1 kilometer</option>
                                <option value="2000">Within 2 kilometers</option>
                                <option value="5000">Within 5 kilometers</option>
                                <option value="10000">Within 10 kilometers</option>
                            </Form.Control>
                        </Form.Group>
                    </Col>
                </Row>

                <Row className="my-3">
                    <Col>
                        <Button variant="primary" onClick={fetchNearbyParking} disabled={loading}>
                            {loading ? 'Loading...' : 'Show Me Nearby Parking Spaces'}
                        </Button>
                    </Col>
                </Row>

                {alertInfo.show && (
                    <Row className="my-3">
                        <Col>
                            <Alert variant="danger" onClose={() => setAlertInfo({ show: false, message: '' })} dismissible>
                                {alertInfo.message}
                            </Alert>
                        </Col>
                    </Row>
                )}

                <Row className="my-3">
                    {parkingSpaces.map((parking, index) => (
                        <Col key={index} sm={12} md={6} lg={4} className="mb-3">
                            <Card>
                                <Card.Body>
                                    <Card.Title>{parking.displayName.text}</Card.Title>
                                    <Card.Text>
                                        {parking.internationalPhoneNumber}
                                        {parking.formattedAddress}
                                    </Card.Text>
                                    <Button variant="primary" onClick={() => props.app.setCurrentParking(parking)}>
                                        Show Details
                                    </Button>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>

                <Row className="my-3">
                    <Col>
                        <Button variant="secondary" onClick={handleGoBack}>Go Back</Button>
                    </Col>
                </Row>
            </Container>
        </>
    );
}

export default ParkingFinder;

