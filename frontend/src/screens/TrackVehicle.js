// import React, { useState } from 'react';
// import { Pages } from "../Pages.js"
// import Header from "./components/Header";
//
// function TrackMyVehicle(props) {
//     const [selectedVehicle, setSelectedVehicle] = useState('');
//
//     const handleSendSOS = () => {
//         alert('SOS sent for ' + selectedVehicle);
//     };
//
//     const handleVehicleChange = (event) => {
//         setSelectedVehicle(event.target.value);
//     };
//
//     const vehicles = ['Car', 'Bike', 'Truck'];
//
//     return (
//         <div>
//             <Header app ={props.app}/>
//             <h1>Track My Vehicle</h1>
//             <select value={selectedVehicle} onChange={handleVehicleChange}>
//                 <option value="">Select a vehicle</option>
//                 {vehicles.map(vehicle => (
//                     <option key={vehicle} value={vehicle}>{vehicle}</option>
//                 ))}
//             </select>
//
//             {selectedVehicle && (
//                 <iframe
//                     title="vehicleLocation"
//                     src={`https://maps.google.com/maps?q=${selectedVehicle}&z=15&output=embed`}
//                     width="100%"
//                     height="400"
//                     style={{ border: '0' }}
//                     allowFullScreen=""
//                     loading="lazy"
//                 ></iframe>
//             )}
//             <br></br>
//             <button >Send SOS</button><br></br>
//             <button onClick={() => props.app.changePage(Pages.DashboardUI)}>Go Back to Dashboard</button>
//         </div>
//     );
// }
//
// export default TrackMyVehicle;
//

import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Alert, Modal } from 'react-bootstrap';
import { MapContainer, TileLayer, Marker, Popup, useMap } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import Header from "./components/Header";

const userIcon = new L.Icon({
    iconUrl: '../images/user.png',
    iconSize: [30, 30],
    iconAnchor: [15, 30],
    popupAnchor: [0, -30]
});

const carIcon = new L.Icon({
    iconUrl: '../images/car.png',
    iconSize: [30, 30],
    iconAnchor: [15, 30],
    popupAnchor: [0, -30]
});

const UpdateMap = ({ userLocation, vehicleLocation }) => {
    const map = useMap();

    if (userLocation) {
        map.setView(userLocation, map.getZoom());
    }

    if (vehicleLocation) {
        const marker = L.marker(vehicleLocation, { icon: carIcon }).addTo(map);
        marker.bindPopup('Your car is here');
    }

    return null;
};

const TrackMyVehicle = (props) => {
    const [userLocation, setUserLocation] = useState(null);
    const [vehicleLocation, setVehicleLocation] = useState(null);
    const [isTracking, setIsTracking] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [error, setError] = useState('');

    useEffect(() => {
        navigator.geolocation.getCurrentPosition(
            position => {
                setUserLocation([position.coords.latitude, position.coords.longitude]);
            },
            () => {
                setError('Unable to retrieve your location');
            }
        );
    }, []);

    const fetchVehicleLocation = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/v1/features/get-device-location');
            const data = await response.json();
            console.log(data)
            if (response.ok) {
                setVehicleLocation([data.latitude, data.longitude]);
            } else {
                throw new Error(data.message || 'Error fetching vehicle location');
            }
        } catch (err) {
            setError(err.message);
        }
    };

    const handleFindMyCar = () => {
        fetchVehicleLocation();
    };

    const handleStartTracking = () => {
        setIsTracking(current => !current);
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    return (
<>
        <Header app={props.app} />

        <Container>
            {error && <Alert variant="danger">{error}</Alert>}
            <Row className="my-4">
                <Col>
                    <h2 className="text-center">Theft Tracking System</h2>
                </Col>
            </Row>
            <Row className="justify-content-center my-3">
                <Col md={8}>
                    <Button variant="primary" block onClick={handleFindMyCar}>
                        Find My Car
                    </Button>
                </Col>
            </Row>
            <Row className="justify-content-center my-3">
                <Col md={8}>
                    <Button variant={isTracking ? "danger" : "info"} block onClick={handleStartTracking}>
                        {isTracking ? 'Stop Tracking' : 'Start Tracking'}
                    </Button>
                </Col>
            </Row>
            <Row>
                <Col>
                    <MapContainer center={userLocation || [51.505, -0.09]} zoom={13} style={{ height: '400px' }}>
                        <TileLayer
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        />
                        {userLocation && (
                            <Marker position={userLocation} icon={userIcon}>
                                <Popup>You are here</Popup>
                            </Marker>
                        )}
                        <UpdateMap userLocation={userLocation} vehicleLocation={vehicleLocation} />
                    </MapContainer>
                </Col>
            </Row>
            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Tracking Status</Modal.Title>
                </Modal.Header>
                <Modal.Body>Tracking has begun</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
        </>
    );
};

export default TrackMyVehicle;

