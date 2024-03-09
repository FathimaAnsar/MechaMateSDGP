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
// import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
// import L from 'leaflet';
// import 'leaflet/dist/leaflet.css';


const userIcon = new L.Icon({
    iconUrl: 'path/to/user-icon.png',
    iconSize: [30, 30],
    iconAnchor: [15, 30],
    popupAnchor: [0, -30]
});

const carIcon = new L.Icon({
    iconUrl: 'path/to/car-icon.png',
    iconSize: [30, 30],
    iconAnchor: [15, 30],
    popupAnchor: [0, -30]
});

const centerPosition = [51.505, -0.09];

const TheftTracking = () => {
    const [userLocation, setUserLocation] = useState(null);
    const [vehicleLocation, setVehicleLocation] = useState(null);
    const [isTracking, setIsTracking] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!navigator.geolocation) {
            setError('Geolocation is not supported by your browser');
        } else {
            navigator.geolocation.getCurrentPosition(
                position => {
                    setUserLocation([position.coords.latitude, position.coords.longitude]);
                },
                () => {
                    setError('Unable to retrieve your location');
                }
            );
        }
    }, []);

    const fetchVehicleLocation = () => {
        setVehicleLocation(centerPosition);
    };

    const handleFindMyCar = () => {
        fetchVehicleLocation();
    };

    const handleStartTracking = () => {
        setIsTracking(current => !current);
        setShowModal(true);
    };

    const handleCloseModal = () => setShowModal(false);

    return (
        <></>
    );
};

export default TheftTracking;
