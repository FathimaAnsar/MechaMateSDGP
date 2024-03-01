import React from 'react';
import ConnectionManager from '../../services/ConnectionManager';
import { Pages } from "../../Pages.js";

function Header(props) {

  const toggleDropdown = () => {
    var dropdown = document.querySelector('.dropdown');
    dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
  };

 
  const handleDropdownItemClick = (page) => {
    var dropdown = document.querySelector('.dropdown');
    dropdown.style.display = 'none';
    props.app.changePage(page);
  };

  const handleSignout = () => {
      props.app.clearSessionCache();
    const connection = new ConnectionManager();
    connection.signout().then( resp => {
     const response = JSON.parse(resp);
    if(response.error) {
        props.app.changePage(Pages.SignInUI);
        // alert("Error occured: " + response.message + "\n" + response.help);
    } else if(response.status) {
        alert("Success: " + response.message + "\n" + response.info);
        props.app.changePage(Pages.SignInUI);
    } else {
        alert("Error: Unknown");
    }
  });

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
        <button className="dropdown-item" onClick={() => handleDropdownItemClick(Pages.SettingsUI)}>Settings</button>
        <button className="dropdown-item" onClick={() => handleDropdownItemClick(Pages.AboutUsUI)}>About App</button>
        <button className="dropdown-item" onClick={handleSignout}>Sign Out</button>
      </div>
    </>
  );
}

export default Header;
