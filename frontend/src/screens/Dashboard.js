import React, { useState } from "react";
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { Pages } from "../Pages.js"
import ConnectionManager from "../services/ConnectionManager.js"
import Header from "./components/Header.js";
import { main } from "../MechaMate.js";
import Button from 'react-bootstrap/Button';
import ClickableCard from "./components/ClickableCard.js";
import Stack from 'react-bootstrap/Stack';
import CustomCarousel from "./components/CustomCarousel.js";



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

  function generateGreeting() {
    var currentHour = new Date().getHours();

    if (currentHour >= 5 && currentHour < 12) {
      return "Good morning, ";
    } else if (currentHour >= 12 && currentHour < 17) {
      return "Good afternoon, ";
    } else {
      return "Good evening, ";
    }
  }

  const cars = [
    { title: 'Camry', regNo: 'CBK-4567', description: 'Toyota' },
    { title: 'Civic', regNo: 'CGK-4367', description: 'Honda' },
    { title: 'Vitz', regNo: 'CEK-4567', description: 'Toyota' },
    { title: 'Camry', regNo: 'CBK-4567', description: 'Toyota' },
    { title: 'Civic', regNo: 'CGK-4367', description: 'Honda' },
  ];



  const handleCardClick = (car) => {
    console.log(`Clicked on ${car.title}`);
  };


  const handleGoBack = () => { props.app.goBack(); }
  const handleClick = (page) => { props.app.changePage(page); }


  return (

    <>
      <div style={{ position: 'sticky', top: '0', width: '100vw', zIndex: '5' }}>
        <Header app={props.app} />
      </div>

      <Container fluid >

        <Row style={{ marginTop: '10px' }}>
          <Col><h1 style={{ fontWeight: '700', fontFamily: 'sans-serif' }}>{generateGreeting()} {(props.app.getUserProfile() == null ? "<User>" : props.app.getUserProfile().firstName)}!</h1>
          </Col>
        </Row>
        <Row>
          <Col>
            <CustomCarousel />
          </Col>
        </Row>

        <Row>
          <Col>
            <h2>Vehicles</h2>
            <div style={{ marginTop:'15px', height: '100%', overflowY: 'auto' }}>
              <Stack direction="horizontal" gap={4}>
                {cars.map((car, index) => (
                  <div className="">
                    <ClickableCard key={index} content={car} onClick={handleCardClick} />
                  </div>
                ))}
              </Stack>
            </div>
          </Col>
        </Row>

      </Container>




      <hr></hr>




      <div id="AutoMobSection">
        <h2>AutoMob Search</h2>
        <button onClick={() => { props.app.changePage(Pages.AutoMobSearchUI) }}>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Search
        </button>
      </div>

      <div id="PredictiveMaintenance">
        <h2>Maintenance predictions</h2>
        <button onClick={() => { props.app.changePage(Pages.PredictMaintenanceUI) }}>
          <span style={{ marginRight: '5px' }}>🛠️</span> {/* Material Icon */}
          Open Maintenance predictions
        </button>
      </div>

      <div id="Track my vehicle">
        <h2>Track my Vehicle</h2>
        <button onClick={() => { props.app.changePage(Pages.TrackVehicleUI) }}>
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
        <button onClick={() => { props.app.changePage(Pages.ManageDocumentsUI) }}>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Open vehicle documents/sercice records
        </button>
      </div>

      <div id="parking finder">
        <h2>Parking Finder</h2>
        <button onClick={() => { props.app.changePage(Pages.ParkingFinderUI) }}>
          <span style={{ marginRight: '5px' }}></span> {/* Material Icon */}
          Open to Find a parking place
        </button>
      </div>
    </>
  )
}

export default Dashboard
