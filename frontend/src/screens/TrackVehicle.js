import React, { useState, useEffect } from "react";
import { Accordion, Container, Row, Col, Button, Form, Card } from "react-bootstrap";
import ConnectionManager from "../services/ConnectionManager";
import Header from "./components/Header";
import CustomAlert from "./components/CustomAlert";
import { ClipLoader } from "react-spinners";
// import "./styles/TrackVehicle.css";

function TrackVehicle(props) {
    const [vehicles, setVehicles] = useState([]);
    const [selectedVehicleRegNo, setSelectedVehicleRegNo] = useState("");
    const [vehicleInfo, setVehicleInfo] = useState(null);
    const [policeStations, setPoliceStations] = useState([]);
    const [loading, setLoading] = useState(false);
    const [alertInfo, setAlertInfo] = useState({ show: false, error: { heading: '', message: '' } });
    const [dropdownActive, setDropdownActive] = useState(false);
    const [showPoliceStations, setShowPoliceStations] = useState(false);

    useEffect(() => {
        const fetchVehicles = async () => {
            setLoading(true);
            try {
                const vehicleList = await props.app.getVehicleList();
                setVehicles(vehicleList || []);
            } catch (error) {
                setAlertInfo({
                    show: true,
                    error: { heading: 'Loading Error', message: 'Failed to load vehicles.' }
                });
            } finally {
                setLoading(false);
            }
        };

        fetchVehicles();
    }, [props.app]);

    const togglePoliceStations = async () => {
        setShowPoliceStations(!showPoliceStations);
        if (!showPoliceStations && !policeStations.length) {
            await showNearbyPoliceStations();
        }
    };

    const trackVehicle = async () => {
        if (!selectedVehicleRegNo) {
            setAlertInfo({
                show: true,
                error: {
                    heading: 'Vehicle Selection Required',
                    message: 'Please select a vehicle to track.'
                }
            });
            return;
        }

        setLoading(true);
        let connection = new ConnectionManager();
        try {
            const response = await connection.getVehicleLocation(selectedVehicleRegNo);
            const vehicleLocationData = JSON.parse(response);
            if (vehicleLocationData && vehicleLocationData.vehicleRegNo) {
                setVehicleInfo(vehicleLocationData);
            } else {
                setAlertInfo({
                    show: true,
                    error: { heading: 'Tracking Error', message: "Could not find vehicle data." }
                });
            }
        } catch (error) {
            setAlertInfo({
                show: true,
                error: { heading: 'Tracking Error', message: error.message || "Could not track the vehicle." }
            });
        } finally {
            setLoading(false);
        }
    };

    const showNearbyPoliceStations = async () => {
        if (!vehicleInfo) {
            setAlertInfo({
                show: true,
                error: {
                    heading: 'Vehicle Location Required',
                    message: 'Please track your vehicle to find the nearest police stations.'
                }
            });
            return;
        }

        setLoading(true);
        let connection2 = new ConnectionManager();
        try {
            const responsePoliceStations = await connection2.getNearbyPoliceStations(
                vehicleInfo.latitude,
                vehicleInfo.longitude,
                10000,
                10
            );
            const responsePoliceStationsData = JSON.parse(responsePoliceStations);
            setPoliceStations(responsePoliceStationsData.places);
        } catch (error) {
            setAlertInfo({
                show: true,
                error: { heading: 'Error', message: 'Failed to retrieve police stations.' }
            });
        } finally {
            setLoading(false);
        }
    };

    const contactPolice = () => {
        window.location.href = "tel:+911";
    };

    const handleCloseAlert = () => setAlertInfo({ show: false, error: { heading: '', message: '' } });


    return (
        <>
            <Header app={props.app} />
            <Container id="trackVehicleContainer">
                <Row className="mt-3">
                    <Col className="text-center">
                        <h2 className="mb-4">Track My Vehicle</h2>
                        <Form.Group controlId="vehicleSelect">
                            <Form.Label>Select Your Vehicle</Form.Label>
                            <Form.Control
                                as="select"
                                value={selectedVehicleRegNo}
                                onChange={e => setSelectedVehicleRegNo(e.target.value)}
                                onFocus={() => setDropdownActive(true)}
                                onBlur={() => setDropdownActive(false)}
                            >
                                <option value="">Select a vehicle</option>
                                {vehicles.map((vehicle, index) => (
                                    <option key={index} value={vehicle.registrationNumber}>
                                        {vehicle.registrationNumber} - {vehicle.vehicleMake} {vehicle.vehicleModel}
                                    </option>
                                ))}
                            </Form.Control>
                        </Form.Group>
                        <Button
                            variant="primary"
                            onClick={trackVehicle}
                            disabled={loading || vehicles.length === 0}
                            className={dropdownActive ? "mt-3" : ""}
                        >
                            Track Now
                        </Button>
                    </Col>
                </Row>

                {loading && (
                    <Row className="mt-3 justify-content-center">
                        <Col className="text-center">
                            <ClipLoader color="#007bff" loading={loading} size={150} />
                        </Col>
                    </Row>
                )}

                {!loading && vehicleInfo && vehicleInfo.vehicleRegNo && (
                    <>
                        <Row className="mt-3">
                            <Col sm={12}>
                                <Card>
                                    <Card.Body>
                                        <iframe
                                            className="map-container"
                                            title="vehicleLocationMap"
                                            src={`https://maps.google.com/maps?q=${vehicleInfo.latitude},${vehicleInfo.longitude}&z=15&output=embed`}
                                            frameBorder="0"
                                            allowFullScreen
                                        ></iframe>
                                        <div className="mt-2">
                                            <p>Location Date Time: {vehicleInfo.locationDateTime || 'Not available'}</p>
                                            <p>Device Online: {vehicleInfo.deviceOnline ? "Yes" : "No"}</p>
                                            <p>Engine Running: {vehicleInfo.engineRunning ? "Yes" : "No"}</p>
                                        </div>
                                    </Card.Body>
                                </Card>
                            </Col>
                        </Row>
                        <Row className="mt-3">
                            <Col className="text-center">
                                <Button
                                    className="contact-police-button"
                                    onClick={contactPolice}
                                >
                                    Did you lose your vehicle? Click here to contact police immediately.
                                </Button>
                            </Col>
                        </Row>
                        <Row className="mt-3">
                            <Col className="text-center">
                                <Button
                                    className="show-police-stations-button"
                                    onClick={togglePoliceStations}
                                    disabled={loading}
                                >
                                    {showPoliceStations ? 'Hide nearest police stations' : 'Show nearest police stations'}
                                </Button>
                            </Col>
                        </Row>

                        {showPoliceStations && policeStations.length > 0 && (
                            <Accordion defaultActiveKey="0" className="mt-3">
                                {policeStations.map((station, index) => (
                                    <Accordion.Item eventKey={String(index)} key={station.displayName.text}>
                                        <Accordion.Header>{station.displayName.text}</Accordion.Header>
                                        <Accordion.Body>
                                            <p>Address: {station.formattedAddress?.text || 'Not available'}</p>
                                            <p>Phone: {
                                                station.internationalPhoneNumber?.text ||
                                                station.nationalPhoneNumber?.text ||
                                                'Not available'
                                            }</p>
                                        </Accordion.Body>
                                    </Accordion.Item>
                                ))}
                            </Accordion>
                            )}
                    </>
                )}

                {alertInfo.show && (
                    <CustomAlert
                        show={alertInfo.show}
                        handleClose={handleCloseAlert}
                        error={alertInfo.error}
                    />
                )}
            </Container>
        </>
    );
}

export default TrackVehicle;
