import React, { useState } from 'react';

function TrackMyVehicle(props) {
    const [selectedVehicle, setSelectedVehicle] = useState('');

    const handleSendSOS = () => {
        alert('SOS sent for ' + selectedVehicle);
    };

    const handleVehicleChange = (event) => {
        setSelectedVehicle(event.target.value);
    };

    const vehicles = ['Car', 'Bike', 'Truck'];

    return (
        <div>
            <h1>Track My Vehicle</h1>
            <select value={selectedVehicle} onChange={handleVehicleChange}>
                <option value="">Select a vehicle</option>
                {vehicles.map(vehicle => (
                    <option key={vehicle} value={vehicle}>{vehicle}</option>
                ))}
            </select>

            {selectedVehicle && (
                <iframe
                    title="vehicleLocation"
                    src={`https://maps.google.com/maps?q=${selectedVehicle}&z=15&output=embed`}
                    width="100%"
                    height="400"
                    style={{ border: '0' }}
                    allowFullScreen=""
                    loading="lazy"
                ></iframe>
            )}
            <button >Send SOS</button>
            <button onClick={() => props.app.changePage('Dashboard')}>Go Back to Dashboard</button>
        </div>
    );
}

export default TrackMyVehicle;
