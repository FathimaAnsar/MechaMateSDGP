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
import { Pages } from "../Pages.js"
import ConnectionManager from '../services/ConnectionManager';
import Header from "./components/Header";

function ParkingFinder(props) {
    const [currentLocation, setCurrentLocation] = useState(null);
    const [parkingSpaces, setParkingSpaces] = useState("");
    const [varCap, setVarCap] = useState("");




    const handleClick = async (e) => {
        setVarCap("Loading...");
        setParkingSpaces("");
        const limit = document.getElementById('limit').value;
        const radius = document.getElementById('radius').value;

        // Validate inputs
        // If failed to validate, return here

        let connection = new ConnectionManager();

        const resp = await connection.getNearbyParking(currentLocation.latitude, currentLocation.longitude, radius, limit);
        const response = JSON.parse(resp);

        if(!response) {
            setVarCap("");
            alert("Please check your springboot localhost is running");
            return;
        }

        
        if (response.error) {
            setVarCap("");
            setParkingSpaces("");
            alert("Error occured: " + response.message + "\n" + response.help);
        } else if(response.places && Array.isArray(response.places)) {
            let s = "<hr></hr>";
            for(let i = 0; i < response.places.length; i++) {
                let place = response.places[i];
                s += "<h6>" + place.displayName.text + "</h6>";
                s += "<p>" + place.internationalPhoneNumber + "</p>";
                s += "<p>" + place.formattedAddress + "</p>";
                s += "<button>Show details</button>";
                s += "<hr></hr>";
            }
            if(s.length < 10) s = "No parking found";
            setVarCap("Parking Spaces");
            setParkingSpaces(s);
        } else {
            setVarCap("");
            setParkingSpaces("");
            alert("Invalid response");
        }       
    };




    useEffect(() => {
        props.app.getCurrentLocation().then( location => {
            setCurrentLocation(location);
        }).catch(exp => {
            if(exp.message) {
                alert(exp.message);
            } else {
                alert("Failed to get location information");
            }
            props.app.changePage(Pages.DashboardUI);            
        });
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
    const handleGoBack = () => { props.app.goBack(); }

    return (
        <>
        <Header app ={props.app}/>
    

        <div>
            <h2>Finding a parking place!</h2>
            <h4>You are here!</h4>
            <iframe
                title="map"
                src={`https://maps.google.com/maps?q=${(currentLocation == null ? 6.899752439069889:currentLocation.latitude)},${(currentLocation == null ? 79.85362275600751:currentLocation.longitude)}&z=18&output=embed`}
                width="100%"
                height="300px"
                style={{border: 0}}
                allowFullScreen=""
                aria-hidden="false"
                tabIndex="0"
            ></iframe>

        <select id="limit">
        <option value="5">Show only 5 results</option>
        <option value="10">Show only 10 results</option>
        <option value="15">Show only 15 results</option>
        <option value="20">Show only 20 results</option>
        <option value="25">Show only 25 results</option>
        </select>
        <select id="radius">
        <option value="500">Within 500 meters</option>
        <option value="1000">Within 1 kilometer</option>
        <option value="2000">Within 2 kilometers</option>
        <option value="5000">Within 5 kilometers</option>
        <option value="10000">Within 10 kilometers</option>
        </select>

        <button onClick={handleClick}>Show Me Nearby Parking Spaces</button>
        <h3>{varCap}</h3>
        
        <div dangerouslySetInnerHTML={{ __html: parkingSpaces }} />
        <button onClick={handleGoBack}>Go Back</button>

        </div>
    </>
    // <div>
    //     <h1>Nearby Parking Spaces</h1>
    //     {currentLocation && (
    //         <ul>
    //             {parkingSpaces.map(parking => (
    //                 <li key={parking.id} onClick={() => handleParkingSelect(parking)}>
    //                     {parking.name} - {parking.distance}m
    //                 </li>
    //             ))}
    //         </ul>
    //     )}
    // </div>
    );
}

export default ParkingFinder;
