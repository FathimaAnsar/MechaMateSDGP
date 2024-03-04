import React, { useState, useEffect } from 'react';
import { Pages } from "../Pages.js"
import ConnectionManager from '../services/ConnectionManager';
import Header from "./components/Header";
function AutoMobSearch(props) {
    const [currentLocation, setCurrentLocation] = useState(null);
    const [autoShops, setAutoShops] = useState("");
    const [varCap, setVarCap] = useState("");


    const handleGoBack = () => { props.app.goBack(); }

    const handleClick = async (e) => {
        setVarCap("Loading...");
        setAutoShops("");
        const limit = document.getElementById('limit').value;
        const radius = document.getElementById('radius').value;

        // Validate inputs
        // If failed to validate, return here

        let connection = new ConnectionManager();

        const resp = await connection.getNearbyAutoShops(currentLocation.latitude, currentLocation.longitude, radius, limit);
        const response = JSON.parse(resp);

        if(!response) {
            setVarCap("");
            alert("Please check your springboot localhost is running");
            return;
        }

        
        if (response.error) {
            setVarCap("");
            setAutoShops("");
            alert("Error occured: " + response.message + "\n" + response.help);
        } else if(response.places && Array.isArray(response.places)) {
            let s = "<hr></hr>";
            for(let i = 0; i < response.places.length; i++) {
                let place = response.places[i];
                s += "<h6>" + place.displayName.text + "</h6>";
                s += "<p>" + place.internationalPhoneNumber + "</p>";
                s += "<p>" + place.formattedAddress + "</p>";
                s += "<p>Open Now? " + place.currentOpeningHours.openNow + "</p>";
                s += "<p>Rating: " + place.rating + "</p>";
                s += "<button>Show details</button>";
                s += "<hr></hr>";
            }
            if(s.length < 10) s = "No automobile shops found";
            setVarCap("Automobiles Shops");
            setAutoShops(s);
        } else {
            setVarCap("");
            setAutoShops("");
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

    return (
        <>
            <Header app ={props.app}/>
        <div>
            <h2>Finding spare parts for your vehicle!</h2>

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
        <h2></h2>
        <button onClick={handleClick}>Show Me Spare part shops</button>
        <h3>{varCap}</h3>
        
        <div dangerouslySetInnerHTML={{ __html: autoShops }} />
        <button onClick={handleGoBack}>Go Back</button>

        </div>
    </>
    );
}

export default AutoMobSearch;
