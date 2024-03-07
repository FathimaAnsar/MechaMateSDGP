import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Form, Card, Alert } from 'react-bootstrap';
import { Pages } from "../Pages.js"
import ConnectionManager from '../services/ConnectionManager';
import Header from "./components/Header";

function AutoMobSearch(props) {
    const [currentLocation, setCurrentLocation] = useState(null);
    const [autoShops, setAutoShops] = useState([]);
    const [varCap, setVarCap] = useState("");
    const [selectedMapUri, setSelectedMapUri] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        props.app.getCurrentLocation().then(location => {
            console.log("Current location obtained: ", location);
            setCurrentLocation(location);
            setSelectedMapUri(`https://maps.google.com/maps?q=${location.latitude},${location.longitude}&z=15&output=embed`);
        }).catch(exp => {
            console.error("Failed to get location: ", exp);
            setError(exp.message || "Failed to get location information");
            props.app.changePage(Pages.DashboardUI);
        });
    }, [props.app]);

    const handleGoBack = () => {
        props.app.goBack();
    }

    const showOnMap = (location) => {
        if (location && location.latitude && location.longitude) {
            const mapsUrl = `https://maps.google.com/maps?q=${location.latitude},${location.longitude}&z=15&output=embed`;
            setSelectedMapUri(mapsUrl);
        } else {
            console.error("Invalid location data for map: ", location);
            setError("Invalid location data for map.");
        }
    };

    const handleClick = async () => {
        setError("");
        setVarCap("Loading...");
        setAutoShops([]);
        const limit = document.getElementById('limit').value;
        const radius = document.getElementById('radius').value;

        // Input validation
        if (!limit || !radius) {
            console.error("Invalid input: Limit or radius is missing.");
            setError("Please select both a limit and a radius.");
            return;
        }

        if (!currentLocation) {
            console.error("No current location available.");
            setError("Current location is not available.");
            return;
        }

        let connection = new ConnectionManager();
        try {
            const resp = await connection.getNearbyAutoShops(currentLocation.latitude, currentLocation.longitude, radius, limit);
            const response = JSON.parse(resp);

            if (response.error) {
                console.error("Error from server: ", response.message);
                setError(`Error occurred: ${response.message}`);
                setVarCap("");
                setAutoShops([]);
            } else if (response.places && Array.isArray(response.places)) {
                console.log("Auto shops retrieved: ", response.places);
                setVarCap("Automobile Shops");
                setAutoShops(response.places);
            } else {
                console.error("Invalid response format: ", response);
                setError("Invalid response format.");
                setVarCap("");
                setAutoShops([]);
            }
        } catch (error) {
            console.error("Exception when calling getNearbyAutoShops: ", error);
            setError("An error occurred while trying to fetch auto shops. Please check your network connection and try again.");
            setVarCap("");
            setAutoShops([]);
        }
    };

    return (
        <>
            <Header app={props.app} />
            <Container>
                {error && <Alert variant="danger">{error}</Alert>}

                <Row>
                    <Col>
                        <h2 className="text-center">Finding Spare Parts for Your Vehicle!</h2>
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
                                {/*/!*<option value="25">Show only 25 results</option>*!/ committed due to   Internal API connection failed*/}
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

                <Row className="mt-3">
                    <Col>
                        <h4 className="text-center mb-3">Selected Location</h4>
                        <div className="map-container mb-4">
                            <iframe
                                title="map"
                                src={selectedMapUri}
                                width="100%"
                                height="300"
                                frameBorder="0"
                                style={{border: 0}}
                                allowFullScreen=""
                            ></iframe>
                        </div>
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
                                    <Card.Text>Open Now? {shop.currentOpeningHours?.openNow.toString()}</Card.Text>
                                    <Card.Text>Rating: {shop.rating}</Card.Text>
                                    {shop.location && (
                                        <Button variant="primary" onClick={() => showOnMap(shop.location)}>View on Map</Button>
                                    )}
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
