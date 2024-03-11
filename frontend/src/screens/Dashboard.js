import React, { useState, useEffect } from "react";
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { Pages } from "../Pages.js"
import ConnectionManager from "../services/ConnectionManager.js"
import Header from "./components/Header.js";
import ClickableCard from "./components/ClickableCard.js";
import Stack from 'react-bootstrap/Stack';
import CustomCarousel from "./components/CustomCarousel.js";
import { Button } from "react-bootstrap";
import Spinner from 'react-bootstrap/Spinner';
import ViewVehicle from "./ViewVehicle.js";


function Dashboard(props) {
  const connection = new ConnectionManager();
  const [vehicles, setVehicles] = useState(null);
  const [selectedVehicle, setSelectedVehicle] = useState(null);

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

  async function getVehicles() {
    try {
      const resp = await connection.getVehicleList();
      const vehicles = JSON.parse(resp);
      console.log(vehicles)
      props.app.setVehicleList(vehicles)
      return vehicles;

    } catch (error) {
      console.error(error);
    }
  }

  async function fetchData() {
    const vehicles = await getVehicles();
    setVehicles(vehicles);
  }

  useEffect(() => {
    fetchData();
  }, []);

  const handleCardClick = (vehicle) => {
    // console.log(vehicle);
    // props.app.changePage(Pages.ViewVehicle);
    setSelectedVehicle(vehicle);
  };

  if (selectedVehicle) {
    return <ViewVehicle app={props.app} vehicle={selectedVehicle} />;
  }

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
            <div style={{
              height: '100%',
              overflowY: 'auto',
            }}>
              {vehicles === null ? (
                <div style={{display: 'flex',
                justifyContent: 'center', 
                width: '100%',
                height:'100px',
                alignItems: 'center'}}><Spinner animation="border" variant="secondary" /></div>
              ) : vehicles.length > 0 ? (
                <Stack direction="horizontal" gap={4} >
                  {vehicles.map((vehicle, index) => (
                    <div key={index}>
                      <ClickableCard content={vehicle} onClick={handleCardClick} />
                    </div>
                  ))}
                </Stack>
              ) : ( // Render message and button if no vehicles are available
                <div style={{width:'100%'}}>
                  <p>You have not added any vehicles yet</p>
                  <Button variant="dark" onClick={() => props.app.changePage(Pages.MyVehiclesUI)}>
                    Add a Vehicle
                  </Button>
                </div>
              )}
            </div>
          </Col>
        </Row>

      </Container>

                    <br></br>
      <hr></hr>
      <br></br><br></br>
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
