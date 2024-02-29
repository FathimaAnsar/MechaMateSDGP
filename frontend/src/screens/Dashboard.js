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

      <div id="AutoMob section">
        <h2>AutoMob Search</h2>

      <button>Search</button>

      </div>


      <button onClick={handleGoBack}>Go Back</button>



    </>
  )
}

export default Dashboard
