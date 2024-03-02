// import React from 'react';
//
// function ShowParkingInfo(props) {
//     return (
//         <div>
//             <h1>Parking Information</h1>
//             <p>Details about available parking spaces.</p>
//             <div id="mapContainer" style={{ height: '400px', width: '100%', backgroundColor: '#e0e0e0' }}>
//                 //placeholder for map
//                 <p style={{ textAlign: 'center', paddingTop: '180px' }}>Map will be displayed here.</p>
//             </div>
//             <button onClick={() => props.app.changePage('ParkingFinder')}>Go Back to Parking Finder</button>
//         </div>
//     );
// }
//
// export default ShowParkingInfo;
// ShowParkingInfo.js
import React from 'react';

function ShowParkingInfo(props) {
    const { currentParking, userLocation } = props.app;

    return (
        <div>
            <h1>Parking Information</h1>
            <p>{currentParking.name}</p>
            <iframe
                title="map"
                src={`https://maps.google.com/maps?q=${currentParking.location.lat},${currentParking.location.lng}&z=15&output=embed`}
                width="100%"
                height="400"
                frameBorder="0"
                style={{ border: 0 }}
                allowFullScreen=""
                aria-hidden="false"
                tabIndex="0"
            ></iframe>
        </div>
    );
}

export default ShowParkingInfo;

