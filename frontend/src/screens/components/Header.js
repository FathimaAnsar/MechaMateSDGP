import React, { useState }  from 'react';
import ConnectionManager from '../../services/ConnectionManager';
import { Pages } from "../../Pages.js";
import { Navbar, Container, Nav, Offcanvas, Button } from 'react-bootstrap';




function Header(props) {
  const [showOffcanvas, setShowOffcanvas] = useState(false);

  const handleToggleOffcanvas = () => setShowOffcanvas(!showOffcanvas);

  const handleSignout = () => {
      props.app.clearSessionCache();
    const connection = new ConnectionManager();
    connection.signout().then( resp => {
     const response = JSON.parse(resp);

    if(!response) {
      alert("Please check your springboot localhost is running");
      props.app.changePage(Pages.SignInUI);
      return;
    }


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
      
      <Navbar expand="lg" className="bg-body-tertiary" bg="primary" data-bs-theme="dark">
        <Container>
          <Navbar.Brand >MechaMate</Navbar.Brand>
          <Button variant="dark"  className="navbar-toggler-icon" onClick={handleToggleOffcanvas}>
          </Button>
 {/*           <Navbar.Toggle aria-controls="basic-navbar-nav" /> */}

          {/* <Navbar.Toggle aria-controls="basic-navbar-nav" onClick={handleToggleOffcanvas} /> */}
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
          <Button variant='link' onClick={() =>{props.app.changePage(Pages.DashboardUI)}}>Home</Button>
          <Button variant='link' onClick={() =>{props.app.changePage(Pages.MyVehiclesUI)}}>Add Vehicle</Button>
          <Button variant='link' onClick={() =>{props.app.changePage(Pages.AddSRecordManualUI)}}>Add Service Record</Button>
          <Button variant='link' onClick={() =>{props.app.changePage(Pages.ParkingFinderUI)}}>Parking Finder</Button>
          <Button variant='link' onClick={() =>{props.app.changePage(Pages.ManageDocumentsUI)}}>Documents</Button>
          <Button variant='link' onClick={() =>{props.app.changePage(Pages.EmergencyAssistUI)}}>Emergency Assistance</Button>
          <Button variant='link' onClick={() =>{props.app.changePage(Pages.TrackVehicleUI)}}>Vehicle Tracker</Button>
          <Button variant='link' onClick={() =>{props.app.changePage(Pages.AutoMobSearchUI)}}>Find Mechanic</Button>
          <Button variant='link' onClick={() =>{props.app.changePage(Pages.PredictMaintenanceUI)}}>Predict</Button>
          <Button variant='link' onClick={() =>{props.app.changePage(Pages.SettingsUI)}}>Settings</Button>
          <Button variant='link' onClick={() =>{props.app.changePage(Pages.AboutUsUI)}}>About Us</Button>
          <Button variant='link' onClick={handleSignout}>Sign Out</Button>
            {/* Add more Nav.Link items as needed */}
          </Nav>
        </Offcanvas.Body>
      </Offcanvas>

    </>
  );
}

export default Header;
