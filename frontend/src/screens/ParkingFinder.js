import React, { useState, useEffect, useRef } from "react";
import { Container, Row, Col, Button, Form, Card } from "react-bootstrap";
import ConnectionManager from "../services/ConnectionManager";
import Header from "./components/Header";
import CustomAlert from "./components/CustomAlert";
import { useNavigate } from "react-router-dom";
import { ClipLoader } from "react-spinners";

function ParkingFinder(props) {
    const [currentLocation, setCurrentLocation] = useState(null);
    const [parkingSpaces, setParkingSpaces] = useState([]);
    const [loading, setLoading] = useState(false);
    const [alertInfo, setAlertInfo] = useState({ show: false, error: { heading: '', message: '' } });
    const [selectedMapUri, setSelectedMapUri] = useState("");
    const [expandedInfo, setExpandedInfo] = useState({});
    const navigate = useNavigate();
    const mapRef = useRef(null);
    const [locationHeaderText, setLocationHeaderText] = useState("You are here!");
    useEffect(() => {
        props.app.getCurrentLocation().then(location => {
            setCurrentLocation(location);
            setLoading(false);
        }).catch(error => {
            setLoading(false);
            setAlertInfo({
                show: true,
                error: {
                    heading: 'Location Required',
                    message: 'Please turn on location services to find parking spaces.'
                }
            });
        });
    }, [props.app, navigate]);

    const handleCloseAlert = () => setAlertInfo({ show: false, error: { heading: '', message: '' } });
    if (loading) {
        return (
            <div className="sweet-loading d-flex justify-content-center align-items-center" style={{ height: "100vh" }}>
                <ClipLoader color="#007bff" loading={loading} size={150} />
            </div>
        );
    }

    const fetchNearbyParking = async () => {
        if (!currentLocation) {
            setAlertInfo({
                show: true,
                error: {
                    heading: 'Location Required',
                    message: 'Location data is needed to fetch parking spaces.'
                }
            });
            return;
        }

        setLoading(true);
        const limitSelect = document.getElementById("limit");
        const radiusSelect = document.getElementById("radius");
        const limit = limitSelect.options[limitSelect.selectedIndex].value;
        const radius = radiusSelect.options[radiusSelect.selectedIndex].value;

        let connection = new ConnectionManager();

        try {
            const resp = await connection.getNearbyParking(
                currentLocation.latitude,
                currentLocation.longitude,
                radius,
                limit
            );
            const response = JSON.parse(resp);
            console.log(response)
            if (response.error) {
                setAlertInfo({
                    show: true,
                    error: { heading: 'Parking Error', message: response.message },
                });
            } else {
                setParkingSpaces(response.places);
            }
        } catch (error) {
            setAlertInfo({
                show: true,
                error: { heading: 'Service Error', message: error.message || "Server error" }
            });
        } finally {
            setLoading(false);
        }
        setLocationHeaderText("You are here!");
    };

    const showOnMap = (location, isCurrentLocation = false) => {
        if (!currentLocation || !location) {
            setAlertInfo({
                show: true,
                error: {
                    heading: 'Location Required',
                    message: 'Location data is needed to show on the map.'
                }
            });
            return;
        }
        setSelectedMapUri(`https://maps.google.com/maps?q=${location.latitude},${location.longitude}&z=15&output=embed`);
        mapRef.current.scrollIntoView({ behavior: 'smooth', block: 'start' });

        if (isCurrentLocation) {
            setLocationHeaderText("You are here!");
        } else {
            setLocationHeaderText("Your destination is here!");
        }
    };


    const getDirections = (endLocation) => {
        if (!currentLocation || !endLocation) {
            setAlertInfo({
                show: true,
                error: {
                    heading: 'Location Data Missing',
                    message: 'Both origin and destination locations are required for directions.'
                }
            });
            return;
        }
        window.open(`https://www.google.com/maps/dir/?api=1&origin=${currentLocation.latitude},${currentLocation.longitude}&destination=${endLocation.latitude},${endLocation.longitude}&travelmode=driving`, "_blank");
    };

    const toggleDetails = (index) => {
        setExpandedInfo(prevState => ({ ...prevState, [index]: !prevState[index] }));
    };

    return (
        <>
            <Header app={props.app} />
            <Container>
                <Row className="mt-3">
                    <Col className="text-center">
                        <h2 className="mb-4">Finding a parking place!</h2>
                        <h4 className="text-secondary mb-3">{locationHeaderText}</h4>
                        {loading ? (
                            <div className="sweet-loading d-flex justify-content-center align-items-center" style={{ height: "300px" }}>
                                <ClipLoader color="#007bff" size={150} />
                            </div>
                        ) : (
                            <div className="map-container mb-4" ref={mapRef}>
                                <iframe
                                    title="map"
                                    src={selectedMapUri || `https://maps.google.com/maps?q=${currentLocation ? `${currentLocation.latitude},${currentLocation.longitude}` : ''}&z=15&output=embed`}
                                    width="100%"
                                    height="300"
                                    frameBorder="0"
                                    style={{ border: 0 }}
                                    allowFullScreen
                                ></iframe>
                            </div>
                        )}
                    </Col>
                </Row>

                {!loading && (
                    <>
                        <Row className="my-3">
                            <Col xs={12} md={6}>
                                <Form.Group controlId="limit">
                                    <Form.Label>Number of Results</Form.Label>
                                    <Form.Control as="select" defaultValue="5">
                                        <option value="5">Show only 5 results</option>
                                        <option value="10">Show only 10 results</option>
                                        <option value="15">Show only 15 results</option>
                                        <option value="20">Show only 20 results</option>
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
                                <Button
                                    variant="primary"
                                    onClick={fetchNearbyParking}
                                    disabled={!currentLocation}
                                >
                                    Show Me Nearby Parking Spaces
                                </Button>
                            </Col>
                        </Row>

                        {alertInfo.show && (
                            <CustomAlert
                                show={alertInfo.show}
                                handleClose={handleCloseAlert}
                                error={alertInfo.error}
                            />
                        )}

                        <Row className="my-3">
                            {parkingSpaces.map((parking, index) => (
                                <Col key={index} sm={12} md={6} lg={4} className="mb-3">
                                    <Card>
                                        <Card.Body>
                                            <Card.Title>{parking.displayName.text}</Card.Title>
                                            <div className={`address-details ${expandedInfo[index] ? 'show' : 'hide'}`}>
                                                <Card.Text>{parking.formattedAddress}</Card.Text>
                                            </div>
                                            <div className="button-group">
                                                <Button
                                                    variant="outline-primary"
                                                    onClick={() => toggleDetails(index)}
                                                    className="mr-2"
                                                >
                                                    {expandedInfo[index] ? "Hide Details" : "Show Details"}
                                                </Button>
                                                <Button
                                                    variant="outline-primary"
                                                    onClick={() => showOnMap(parking.location)}
                                                    className="mr-2"
                                                >
                                                    View on Map
                                                </Button>
                                                <Button
                                                    variant="outline-primary"
                                                    onClick={() => getDirections(parking.location)}
                                                >
                                                    Get Directions
                                                </Button>
                                            </div>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            ))}

                        </Row>
                    </>
                )}
            </Container>
        </>
    );

}

export default ParkingFinder;
