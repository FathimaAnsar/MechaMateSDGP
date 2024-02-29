import React, { useState } from "react";

import { Pages } from "../Pages.js"
import ConnectionManager from "../ConnectionManager.js"
import Header from "./Header.js";


function Dashboard(props) {
  const [dropdownStates, setDropdownStates] = useState({});

  const toggleDropdown = (id) => {
    setDropdownStates(prevState => ({
      ...prevState,
      [id]: !prevState[id]
    }));
  }


  const handleGoBack = () => { props.app.goBack(); }
  return (

    <>
      <Header></Header>
      <div>

        <h1>Hello Peter,Welcome to mechamate!</h1>

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
        <button>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Search
        </button>
      </div>

      <div id="PredictiveMaintenance">
        <h2>Predictive Maintenance</h2>
        <button>
          <span style={{ marginRight: '5px' }}>🛠️</span> {/* Material Icon */}
          Open Predictive Maintenance
        </button>
      </div>

      <div id="Track my vehicle">
        <h2>Track my Vehicle</h2>
        <button>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Open To Track my vehicle
        </button>
      </div>
      
      <div id="emergency assistence">
        <h2>Emergency assistence</h2>
        <button>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Open Emergency assistence
        </button>
      </div>

      <div id="manage my documents">
        <h2>Manage vehicle documents</h2>
        <button>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Open vehicle documents/sercice records
        </button>
      </div>

      <div id="parking finder">
        <h2>Parking Finder</h2>
        <button>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Open to Find a parking place
        </button>
      </div>
      



      <button onClick={handleGoBack}>Go Back</button>



    </>
  )
}

export default Dashboard
