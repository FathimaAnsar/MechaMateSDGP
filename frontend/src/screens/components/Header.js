import React, { useState }  from 'react';
import ConnectionManager from '../../services/ConnectionManager';
import { Pages } from "../../Pages.js";
import { Navbar, Container, Nav, Offcanvas, Button } from 'react-bootstrap';




function Header(props) {
  const [showOffcanvas, setShowOffcanvas] = useState(false);

  const handleToggleOffcanvas = () => setShowOffcanvas(!showOffcanvas);

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
      {/* <div id="main-header">
        <div id="main-logo">Logo Mechamate</div>

        <input type="checkbox" id="notify-checkbox"></input>
        <label htmlFor="notify-checkbox">Notifications</label>

        <input type="checkbox" id="menu-checkbox" onChange={toggleDropdown}></input>
        <label htmlFor="menu-checkbox">Menu button</label>
      </div>

      {/* Dropdown Menu */}
      {/* <div className="dropdown">
        <button className="dropdown-item" onClick={() => handleDropdownItemClick(Pages.SettingsUI)}>Settings</button>
        <button className="dropdown-item" onClick={() => handleDropdownItemClick(Pages.AboutUsUI)}>About App</button>
        <button className="dropdown-item" onClick={handleSignout}>Sign Out</button>
      </div> */} 
      <Navbar expand="lg" className="bg-body-tertiary">
        <Container>
          <Navbar.Brand href="#home">MechaMate</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" onClick={handleToggleOffcanvas} />
          {/* Navbar.Collapse is moved inside the Offcanvas */}
        </Container>
      </Navbar>

      <Offcanvas show={showOffcanvas} onHide={handleToggleOffcanvas} placement='end'>
        <Offcanvas.Header closeButton>
          <Offcanvas.Title>MechaMate Menu</Offcanvas.Title>
        </Offcanvas.Header>
        <Offcanvas.Body>
          {/* Your offcanvas menu items go here */}
          <Nav className="flex-column">
          <Button onClick={() =>{props.app.changePage(Pages.MyVehiclesUI)}}>Add Vehicle</Button>
          <Button onClick={() =>{props.app.changePage(Pages.AddSRecordManualUI)}}>Add Service Record</Button>
          <Button onClick={() =>{props.app.changePage(Pages.ParkingFinderUI)}}>Parking Finder</Button>
          <Button onClick={() =>{props.app.changePage(Pages.ManageDocumentsUI)}}>Documents</Button>
          <Button onClick={() =>{props.app.changePage(Pages.EmergencyAssistUI)}}>Emergency Assistance</Button>
          <Button onClick={() =>{props.app.changePage(Pages.TrackVehicleUI)}}>Vehicle Tracker</Button>
          <Button onClick={() =>{props.app.changePage(Pages.AutoMobSearchUI)}}>Find Mechanic</Button>
          <Button onClick={() =>{props.app.changePage(Pages.SettingsUI)}}>Settings</Button>
          <Button onClick={() =>{props.app.changePage(Pages.PredictMaintenanceUI)}}>Predict</Button>
          <Button onClick={handleSignout}>Sign Out</Button>
            {/* Add more Nav.Link items as needed */}
          </Nav>
        </Offcanvas.Body>
      </Offcanvas>

    </>
  );
}

export default Header;
