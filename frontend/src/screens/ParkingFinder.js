// import React from 'react';
//
// function ParkingFinder(props) {
//     return (
//         <div>
//             <h1>Parking Finder</h1>
//             <p>Find parking spaces near you.</p>
//             <button onClick={() => props.app.changePage('ShowParkingInfo')}>Show Parking Information</button>
//             <button onClick={() => props.app.changePage('Dashboard')}>Go Back to Dashboard</button>
//         </div>
//     );
// }
//
// export default ParkingFinder;

// ParkingFinder.js
import React, { useState, useEffect } from 'react';

function ParkingFinder(props) {
    const [currentLocation, setCurrentLocation] = useState(null);
    const [parkingSpaces, setParkingSpaces] = useState([]);

    useEffect(() => {
        // get the current location of the user
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const { latitude, longitude } = position.coords;
                setCurrentLocation({ latitude, longitude });
                console.log(latitude+' '+longitude)
                // fetchNearbyParking(latitude, longitude);
            },
            (error) => {
                console.error(error);
                alert('Unable to retrieve your location.');
            }
        );
    }, []);

    const fetchNearbyParking = async (latitude, longitude) => {
        const response = await fetch(`https://localhost:8080=${latitude}&long=${longitude}`);
        const data = await response.json();
        setParkingSpaces(data);
    };

    const handleParkingSelect = (parking) => {
        props.app.setCurrentParking(parking);
        props.app.changePage('ShowParkingInfo');
    };

    return (
        <div>
            <h1>Nearby Parking Spaces</h1>
            {currentLocation && (
                <ul>
                    {parkingSpaces.map(parking => (
                        <li key={parking.id} onClick={() => handleParkingSelect(parking)}>
                            {parking.name} - {parking.distance}m
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default ParkingFinder;
