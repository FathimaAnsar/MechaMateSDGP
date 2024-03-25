import React, {useState, useEffect} from "react";
import {Accordion, Container, Row, Col, Button, Form, Card} from "react-bootstrap";
import ConnectionManager from "../services/ConnectionManager";
import Header from "./components/Header";
import CustomAlert from "./components/CustomAlert";
import {ClipLoader} from "react-spinners";

function EmergencyAssist(props) {
    const [vehicles, setVehicles] = useState([]);
    const [selectedVehicleRegNo, setSelectedVehicleRegNo] = useState("");
    const [vehicleInfo, setVehicleInfo] = useState(null);
    const [hospitals, setHospitals] = useState([]);
    const [loading, setLoading] = useState(false);
    const [alertInfo, setAlertInfo] = useState({show: false, error: {heading: '', message: ''}});
    const [dropdownActive, setDropdownActive] = useState(false);
    const [showHospitals, setShowHospitals] = useState(false);

    useEffect(() => {
        const fetchVehicles = async () => {
            setLoading(true);
            try {
                const vehicleList = await props.app.getVehicleList();
                if (!vehicleList) throw new Error("Received no data");
                setVehicles(vehicleList);
            } catch (error) {
                setAlertInfo({
                    show: true,
                    error: {heading: 'Loading Error', message: error.message || 'Failed to load vehicles.'}
                });
            } finally {
                setLoading(false);
            }
        };

        fetchVehicles();
    }, [props.app]);

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
            if (!response) throw new Error("Received no data");
            const vehicleLocationData = JSON.parse(response);

            console.log(vehicleLocationData);

            if (!vehicleLocationData.vehicleRegNo) throw new Error("Vehicle data is incomplete.");
            setVehicleInfo(vehicleLocationData);

            await showNearbyNearbyHospitals(vehicleLocationData.latitude, vehicleLocationData.longitude);
        } catch (error) {
            setAlertInfo({
                show: true,
                error: {heading: 'Tracking Error', message: error.message || 'Could not track the vehicle.'}
            });
        } finally {
            setLoading(false);
        }
    };


    const showNearbyNearbyHospitals = async (latitude, longitude) => {
        if (!latitude || !longitude) {
            setAlertInfo({
                show: true,
                error: {
                    heading: 'Location Error',
                    message: 'No location data available to find police stations.'
                }
            });
            return;
        }

        setLoading(true);
        let connection2 = new ConnectionManager();
        try {
            const responseHospitals = await connection2.getNearbyHospitals(latitude, longitude, 10000, 10);
            if (!responseHospitals) throw new Error("Received no data");
            const responseHospitalsData = JSON.parse(responseHospitals);
            if (!responseHospitalsData.places) throw new Error("No police stations data.");
            setHospitals(responseHospitalsData.places);
        } catch (error) {
            setAlertInfo({
                show: true,
                error: {heading: 'Error', message: error.message || 'Failed to retrieve police stations.'}
            });
        } finally {
            setLoading(false);
        }
    };
    const contactHospital = () => {
        const hospitalWithPhone = hospitals.find(hospital => hospital.phone);
        console.log(hospitalWithPhone)
        if (hospitalWithPhone) {
            console.log(hospitalWithPhone)
            window.location.href = `tel:${hospitalWithPhone.nationalPhoneNumber}`;
        } else {
            setAlertInfo({
                show: true,
                error: {
                    heading: 'Contact Error',
                    message: 'No hospital with a contact number found.'
                }
            });
        }
    };

    const handleCloseAlert = () => setAlertInfo({show: false, error: {heading: '', message: ''}});

    const toggleHospitals = () => {
        setShowHospitals(!showHospitals);
    };

    return (
        <>
            <Header app={props.app}/>
            <Container id="tmvContainer">
                <Row id="tmvTitleRow" className="mt-3">
                    <Col id="tmvTitleCol" className="text-center">
                        <h2 id="tmvTitle">Emergency Assistance</h2>
                        <Form.Group controlId="vehicleSelect">
                            <Form.Label>Select Your Vehicle</Form.Label>
                            <Form.Select
                                size="lg"
                                style={{marginTop:"25px"}}
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
                            </Form.Select>
                        </Form.Group>
                        <Button
                            style={{ margin: '40px' }}
                            id="tmvTrackNowButton"
                            variant="outline-primary"
                            onClick={trackVehicle}
                            disabled={loading || vehicles.length === 0}
                        >
                            Find Me
                        </Button>
                    </Col>
                </Row>

                {loading && (
                    <Row id="tmvLoadingRow" className="mt-3 justify-content-center">
                        <Col className="text-center">
                            <ClipLoader color="#007bff" loading={loading} size={150}/>
                        </Col>
                    </Row>
                )}

                {!loading && vehicleInfo && vehicleInfo.vehicleRegNo && (
                    <>
                        <Row id="tmvMapRow" className="mt-3">
                            <Col sm={12}>
                                <Card>
                                    <Card.Body id="tmvMapCardBody">
                                        <iframe
                                            id="tmvMapContainer"
                                            title="vehicleLocationMap"
                                            src={`https://maps.google.com/maps?q=${vehicleInfo.latitude},${vehicleInfo.longitude}&z=15&output=embed`}
                                            frameBorder="0"
                                            width="100%"
                                            height="300"
                                            style={{border: 0}}
                                            allowFullScreen
                                        ></iframe>
                                    </Card.Body>
                                </Card>
                            </Col>
                        </Row>
                        <Row id="tmvContactPoliceRow" className="mt-3">
                            <Col className="text-center">
                                <Button
                                    id="tmvContactPoliceButton"
                                    variant="outline-danger"
                                    onClick={contactHospital}
                                    disabled={!hospitals.some(station => station.nationalPhoneNumber)}
                                >
                                    Contact Nearest Hospital
                                </Button>
                            </Col>
                        </Row>
                        <Row id="tmvTogglePoliceStationsRow" className="mt-3">
                            <Col className="text-center">
                                <Button
                                    style={{margin:'25px'}}
                                    variant="outline-info"
                                    id="tmvTogglePoliceStationsButton"
                                    className="show-police-stations-button"
                                    onClick={toggleHospitals}
                                    disabled={!vehicleInfo || loading}
                                >
                                    {showHospitals ? 'Hide Nearest hospitals' : 'Show Nearest hospitals'}
                                </Button>
                            </Col>
                        </Row>

                        {showHospitals && (
                            <Accordion id="tmvPoliceStationsAccordion" defaultActiveKey="" className="mt-3">
                                {hospitals.length > 0 ? (
                                    hospitals.map((hospital, index) => (
                                        <Accordion.Item eventKey={String(index)} key={index}>
                                            <Accordion.Header>{hospital.displayName.text || 'Unknown Station'}</Accordion.Header>
                                            <Accordion.Body>
                                                <p>Address: {hospital.formattedAddress || 'Address not available'}</p>
                                                <p>Phone: {
                                                    hospital.nationalPhoneNumber
                                                        ? <a href={`tel:${hospital.nationalPhoneNumber}`}>{hospital.nationalPhoneNumber}</a>
                                                        : 'Phone number not available'
                                                }</p>
                                            </Accordion.Body>

                                        </Accordion.Item>
                                    ))
                                ) : (
                                    <p className="text-center">No Hospitals found.</p>
                                )}
                            </Accordion>
                        )}
                    </>
                )}
            </Container>
            {alertInfo.show && (
                <CustomAlert
                    show={alertInfo.show}
                    handleClose={handleCloseAlert}
                    error={alertInfo.error}
                />
            )}
        </>
    );
}

export default EmergencyAssist;
