import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Form, Card } from 'react-bootstrap';
import { Pages } from "../Pages.js"
import ConnectionManager from '../services/ConnectionManager';
import Header from "./components/Header";

function AutoMobSearch(props) {
    const [currentLocation, setCurrentLocation] = useState(null);
    const [autoShops, setAutoShops] = useState([]);
    const [varCap, setVarCap] = useState("");

    useEffect(() => {
        props.app.getCurrentLocation().then(location => {
            setCurrentLocation(location);
        }).catch(exp => {
            alert(exp.message || "Failed to get location information");
            props.app.changePage(Pages.DashboardUI);
        });
    }, [props.app]);

    const handleGoBack = () => {
        props.app.goBack();
    }

    const handleClick = async () => {
        setVarCap("Loading...");
        setAutoShops([]);
        const limit = document.getElementById('limit').value;
        const radius = document.getElementById('radius').value;

        let connection = new ConnectionManager();
        const resp = await connection.getNearbyAutoShops(currentLocation.latitude, currentLocation.longitude, radius, limit);
        const response = JSON.parse(resp);

        if (!response) {
            setVarCap("");
            alert("Please check your Spring Boot service is running");
            return;
        }

        if (response.error) {
            setVarCap("");
            setAutoShops([]);
            alert(`Error occurred: ${response.message}`);
        } else if (response.places && Array.isArray(response.places)) {
            setVarCap("Automobile Shops");
            setAutoShops(response.places);
        } else {
            setVarCap("");
            setAutoShops([]);
            alert("Invalid response");
        }
    };

    return (
        <>
            <Header app={props.app} />
            <Container>
                <Row>
                    <Col>
                        <h2 className="text-center">Finding spare parts for your vehicle!</h2>
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
                        <Button variant="primary" onClick={handleClick}>Show Me Spare Part Shops</Button>
                    </Col>
                </Row>

                <Row className="my-3">
                    {autoShops.length ? autoShops.map((shop, index) => (
                        <Col key={index} sm={12} md={6} lg={4} className="mb-3">
                            <Card>
                                <Card.Body>
                                    <Card.Title>{shop.displayName.text}</Card.Title>
                                    <Card.Text>{shop.internationalPhoneNumber}</Card.Text>
                                    <Card.Text>{shop.formattedAddress}</Card.Text>
                                    <Card.Text>Open Now? {shop.currentOpeningHours.openNow.toString()}</Card.Text>
                                    <Card.Text>Rating: {shop.rating}</Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                    )) : <Col><p className="text-center">{varCap}</p></Col>}
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

export default AutoMobSearch;
