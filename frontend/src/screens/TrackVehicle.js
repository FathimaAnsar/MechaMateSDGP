import React, { useState, useEffect } from "react";
import { Container, Row, Col, Button, Form, Card } from "react-bootstrap";
import ConnectionManager from "../services/ConnectionManager";
import CustomAlert from "./components/CustomAlert";

function TrackMyVehicle(props) {
    const [vehicles, setVehicles] = useState([]);
    const [selectedVehicle, setSelectedVehicle] = useState("");
    const [vehicleInfo, setVehicleInfo] = useState(null);
    const [loading, setLoading] = useState(false);
    const [alertInfo, setAlertInfo] = useState({ show: false, error: { heading: '', message: '' } });

    useEffect(() => {
        const vehicleList = props.app.getVehicleList();
        if (vehicleList) {
            setVehicles(vehicleList);
        }
    }, [props.app]);

    const trackVehicle = async () => {
        if (!selectedVehicle) {
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
            const vehicleLocationData = await connection.getVehicleLocation(selectedVehicle);
            setVehicleInfo(vehicleLocationData);
        } catch (error) {
            setAlertInfo({
                show: true,
                error: { heading: 'Tracking Error', message: error.message || "Could not track the vehicle." }
            });
        } finally {
            setLoading(false);
        }
    };

    const handleCloseAlert = () => setAlertInfo({ show: false, error: { heading: '', message: '' } });

    return (
        <>
            <Container>
                <Row className="mt-3">
                    <Col className="text-center">
                        <h2 className="mb-4">Track My Vehicle</h2>
                        <Form.Group controlId="vehicleSelect">
                            <Form.Label>Select Your Vehicle</Form.Label>
                            <Form.Control as="select" value={selectedVehicle} onChange={e => setSelectedVehicle(e.target.value)}>
                                <option value="">Select a vehicle</option>
                                {vehicles.map((vehicle, index) => (
                                    <option key={index} value={vehicle.vehicleRegNo}>
                                        {vehicle.vehicleRegNo}
                                    </option>
                                ))}
                            </Form.Control>
                        </Form.Group>
                        <Button variant="primary" onClick={trackVehicle} disabled={loading}>
                            Track Now
                        </Button>
                    </Col>
                </Row>

                {vehicleInfo && (
                    <Row className="mt-3">
                        <Col sm={12}>
                            <Card>
                                <Card.Body>
                                    <iframe
                                        title="vehicleLocationMap"
                                        src={vehicleInfo.mapUrl}
                                        width="100%"
                                        height="300"
                                        frameBorder="0"
                                        style={{ border: 0 }}
                                        allowFullScreen
                                    ></iframe>
                                    <div className="mt-2">
                                        <p>Location Date Time: {vehicleInfo.locationDateTime}</p>
                                        <p>Device Online: {vehicleInfo.deviceOnline ? "Yes" : "No"}</p>
                                        <p>Engine Running: {vehicleInfo.engineRunning ? "Yes" : "No"}</p>
                                    </div>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
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

export default TrackMyVehicle;
