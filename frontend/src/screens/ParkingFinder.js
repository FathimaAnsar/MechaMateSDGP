import React from 'react';

function ParkingFinder(props) {
    return (
        <div>
            <h1>Parking Finder</h1>
            <p>Find parking spaces near you.</p>
            <button onClick={() => props.app.changePage('ShowParkingInfo')}>Show Parking Information</button>
            <button onClick={() => props.app.changePage('Dashboard')}>Go Back to Dashboard</button>
        </div>
    );
}

export default ParkingFinder;

