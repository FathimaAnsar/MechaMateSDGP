// SearchAutomobileShops.js
import React from 'react';

function SearchAutomobileShops(props) {
    return (
        <div>
            <h1>Search for Automobile Shops</h1>
            <input type="text" placeholder="Enter shop name or location" />
            <button onClick={() => props.app.changePage('ShowShopsDetails')}>Search</button>
            <button onClick={() => props.app.changePage('Dashboard')}>Go Back to Dashboard</button>
        </div>
    );
}

export default SearchAutomobileShops;
