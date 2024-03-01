import React, { useState } from "react";

import { Pages } from "../Pages.js"
import ConnectionManager from "../services/ConnectionManager.js"
import Header from "./components/Header.js";
import { main } from "../MechaMate.js";


function Dashboard(props) {
  const [dropdownStates, setDropdownStates] = useState({});
  
  let firstName = "";

  let userProfile = props.app.getUserProfile();

 // console.log(userProfile);
  
  if (userProfile !== null) {
    // firstName = userProfile.firstName;
   
 //   console.log(firstName); 
  } else {
    console.log('No object found in local storage with the specified key.');
  }

  const toggleDropdown = (id) => {
    setDropdownStates(prevState => ({
      ...prevState,
      [id]: !prevState[id]
    }));
  }



  const handleGoBack = () => { props.app.goBack(); }
  const handleClick = (page) => { props.app.changePage(page); }

  
  return (

    <>
        <button onClick={handleGoBack}>Go Back</button>
        
    <Header app ={props.app}/>
      <div>

        <h1>Hello { (props.app.getUserProfile() == null ? "<User>" : props.app.getUserProfile().firstName) },Welcome to mechamate!</h1>

      </div>

      <div id="myVehicle">
        <h2>My vehicles</h2>
        <ul>
          <li>
            <button onClick={() => toggleDropdown('desc1')}>Vehicle 1</button>
            <div style={{ display: dropdownStates['desc1'] ? 'block' : 'none' }} className="dropdown-content">
              Description for Vehicle 1.
            </div>
          </li>
          <li>
            <button onClick={() => toggleDropdown('desc2')}>Vehicle 2</button>
            <div style={{ display: dropdownStates['desc2'] ? 'block' : 'none' }} className="dropdown-content">
              Description for Vehicle 2.
            </div>
          </li>

        </ul>
      </div>

      <div id="AutoMobSection">
        <h2>AutoMob Search</h2>
        <button onClick={() =>{props.app.changePage(Pages.AutoMobSearchUI)}}>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Search
        </button>
      </div>

      <div id="PredictiveMaintenance">
        <h2>Predictive Maintenance</h2>
        <button onClick={() =>{props.app.changePage(Pages.PredictMaintenanceUI)}}>
          <span style={{ marginRight: '5px' }}>🛠️</span> {/* Material Icon */}
          Open Predictive Maintenance
        </button>
      </div>

      <div id="Track my vehicle">
        <h2>Track my Vehicle</h2>
        <button onClick={() =>{props.app.changePage(Pages.TrackVehicleUI)}}>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Open To Track my vehicle
        </button>
      </div>
      
      <div id="emergency assistence">
        <h2>Emergency assistence</h2>
        <button onClick={() => handleClick(Pages.EmergencyAssistUI)}>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Open Emergency assistence
        </button>
      </div>

      <div id="manage my documents">
        <h2>Manage vehicle documents</h2>
        <button onClick={() =>{props.app.changePage(Pages.ManageDocumentsUI)}}>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Open vehicle documents/sercice records
        </button>
      </div>

      <div id="parking finder">
        <h2>Parking Finder</h2>
        <button onClick={() =>{props.app.changePage(Pages.ParkingFinderUI)}}>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Open to Find a parking place
        </button>
      </div>
      






    </>
  )
}

export default Dashboard
