import React from 'react';
import ConnectionManager from '../../services/ConnectionManager';
import { Pages } from "../../Pages.js";

function Header(props) {

  const toggleDropdown = () => {
    var dropdown = document.querySelector('.dropdown');
    dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
  };

 
  const handleDropdownItemClick = (action) => {
   
    console.log("Clicked:", action);
    
    var dropdown = document.querySelector('.dropdown');
    dropdown.style.display = 'none';
  };

  const handleSignout = () => {
    const connection = new ConnectionManager();
//    const resp = connection.signout();
    const response = JSON.parse(resp);
    if(response.error) {
        alert("Error occured: " + response.message + "\n" + response.help);
    } else if(response.status) {
        alert("Success: " + response.message + "\n" + response.info);
        props.app.reset();
        props.app.changePage(Pages.SignInUI);
    } else {
        alert("Error: Unknown");
    }


  }

  return (
    <>
      <div id="main-header">
        <div id="main-logo">Logo Mechamate</div>

        <input type="checkbox" id="notify-checkbox"></input>
        <label htmlFor="notify-checkbox">Notifications</label>

        <input type="checkbox" id="menu-checkbox" onChange={toggleDropdown}></input>
        <label htmlFor="menu-checkbox">Menu button</label>
      </div>

      {/* Dropdown Menu */}
      <div className="dropdown">
        <button className="dropdown-item" onClick={() => handleDropdownItemClick('Settings')}>Settings</button>
        <button className="dropdown-item" onClick={() => handleDropdownItemClick('About App')}>About App</button>
        <button className="dropdown-item" onClick={handleSignout}>Sign Out</button>
      </div>
    </>
  );
}

export default Header;
