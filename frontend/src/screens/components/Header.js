import React, { useState } from "react";
import ConnectionManager from "../../services/ConnectionManager";
import { Pages } from "../../Pages.js";
import {
  Navbar,
  Container,
  Nav,
  Offcanvas,
  Button,
  Spinner,
  Image,
} from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import logo from "../../images/logo-white.png";
import ToggleThemeButton from './ToggleThemeButton.js'

function Header(props) {
  const navigate = useNavigate();
  const [showOffcanvas, setShowOffcanvas] = useState(false);
  const handleToggleOffcanvas = () => setShowOffcanvas(!showOffcanvas);
  const [loading, setLoading] = useState(false);

  const handleSignout = () => {
    setLoading(true);
    props.app.clearSessionCache();
    const connection = new ConnectionManager();
    connection.signout().then((resp) => {
      const response = JSON.parse(resp);

      if (!response) {
        alert("Please check your springboot localhost is running");
        navigate("/" + Pages.SignInUI);
        return;
      }

      if (response.error) {
        navigate("/" + Pages.SignInUI);
        // alert("Error occured: " + response.message + "\n" + response.help);
      } else if (response.status) {
        // alert("Success: " + response.message + "\n" + response.info);
        navigate("/" + Pages.SignInUI);
      } else {
        alert("Error: Unknown");
      }
    });
  };

  return (
    <>
      <Navbar
        expand="lg"
        className="bg-body-tertiary"
        bg="dark"
        data-bs-theme="dark"
        sticky="top"
      >
        <Container>
          <Navbar.Brand>
            <Link to="/dashboard">
              <Image
                src={logo}
                alt="MechaMate Logo"
                style={{ height: "25px" }}
              />
            </Link>
          </Navbar.Brand>
          <Button
            variant="dark"
            className="navbar-toggler-icon"
            onClick={handleToggleOffcanvas}
          ></Button>

        </Container>

      </Navbar>

      <Offcanvas
        show={showOffcanvas}
        onHide={handleToggleOffcanvas}
        placement="end"
        style={{ backgroundColor: "#f4f8fa" }}
      >
        <Offcanvas.Header closeButton>
          {/* <Offcanvas.Title> Menu</Offcanvas.Title> */}
          <ToggleThemeButton />
        </Offcanvas.Header>
        <Offcanvas.Body>
          {/* Your offcanvas menu items go here */}
          <Nav className="flex-column">
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.DashboardUI);
              }}
            >
              Home
            </Button>
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.AddVehiclesUI);
              }}
            >
              Add Vehicle
            </Button>
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.AddSRecordManualUI);
              }}
            >
              Add Service Record
            </Button>
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.ParkingFinderUI);
              }}
            >
             AutoMob Search
            </Button>
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.AutoMobSearchUI);
              }}
            >
              Parking Finder
            </Button>
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.ManageDocumentsUI);
              }}
            >
              Documents
            </Button>
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.EmergencyAssistUI);
              }}
            >
              Emergency Assistance
            </Button>
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.TrackVehicleUI);
              }}
            >
              Vehicle Tracker
            </Button>
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.AutoMobSearchUI);
              }}
            >
              Find Mechanic
            </Button>
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.PredictMaintenanceUI);
              }}
            >
              Predict
            </Button>
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.SettingsUI);
              }}
            >
              Settings
            </Button>
            <Button
              variant="light"
              onClick={() => {
                navigate("/" + Pages.AboutUsUI);
              }}
            >
              About Us
            </Button>
            <Button variant="dark" onClick={handleSignout}>
              {loading ? (
                <Spinner
                  as="span"
                  animation="border"
                  size="sm"
                  role="status"
                  aria-hidden="true"
                />
              ) : (
                <>Sign Out</>
              )}
            </Button>
            {/* Add more Nav.Link items as needed */}
          </Nav>
        </Offcanvas.Body>
      </Offcanvas>
    </>
  );
}

export default Header;
