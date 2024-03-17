import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
    Button,
    Card,
    Col,
    Container,
    Row,
    Spinner,
    Stack
} from 'react-bootstrap';
import { Pages } from '../Pages.js';
import ConnectionManager from '../services/ConnectionManager.js';
import Header from './components/Header.js';
import ClickableCard from './components/ClickableCard.js';
import CustomCarousel from './components/CustomCarousel.js';
import ViewVehicle from './ViewVehicle.js';
import LoadingScreen from './components/LoadingScreen.js';


function Dashboard(props) {
  const navigate = useNavigate();
  const connection = new ConnectionManager();
  const [vehicles, setVehicles] = useState(null);
  const [loading, setLoading] = useState(true);

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
      // console.log(vehicles)
      props.app.setVehicleList(vehicles);
      return vehicles;
    } catch (error) {
      console.error(error);
    }
  }

  async function fetchData() {
    const vehicles = await getVehicles();
    setVehicles(vehicles);
    setLoading(false);
  }

  useEffect(() => {
    fetchData();
  }, []);

  const handleCardClick = (index) => {
    navigate(`/${Pages.ViewVehicle}?vehicle=${index}`);
  };

  const handleClick = (page) => {
    props.app.changePage(page);
  };

  return (
    <>
      <div
        style={{ position: "sticky", top: "0", width: "100vw", zIndex: "5" }}
      >
        <Header app={props.app} />
      </div>

      <Container fluid>
        <Row style={{ marginTop: "10px" }}>
          <Col>
            <h2 style={{ fontWeight: "500" }}>
              {generateGreeting()}{" "}
              {props.app.getUserProfile() == null
                ? "<User>"
                : props.app.getUserProfile().firstName}
              !
            </h2>
          </Col>
        </Row>
        {/* <Row>
          <Col>
            <CustomCarousel />
          </Col>
        </Row> */}

        <Row>
          <Col md={12}>
            <h3 id="dash-vehicle-heading">My Vehicles</h3>
          </Col>
          <Col md={12}>
            <div
              style={{
                height: "100%", // Consider removing this
                overflowY: "auto",
              }}
            >
              {loading || vehicles == null ? (
                <div
                  style={{
                    display: "flex",
                    justifyContent: "center",
                    width: "100%",
                    height: "100px",
                    alignItems: "center",
                  }}
                >
                  <Spinner animation="border" variant="secondary" />
                </div>
              ) : vehicles.length > 0 ? (
                <Row>
                  {" "}
                  {/* Add a Row here */}
                  {vehicles.map((vehicle, index) => (
                    <Col key={index} sm={12} md={6} xxl={4}>
                      <div>
                        <ClickableCard
                          content={vehicle}
                          onClick={handleCardClick}
                          index={index}
                        />
                      </div>
                    </Col>
                  ))}
                </Row>
              ) : (
                <div style={{ width: "100%" }}>
                  <p id="dash-vehicle-p1">
                    You have not added any vehicles yet
                  </p>
                  <Button
                    id="dash-add-vehi-btn"
                    variant="dark"
                    onClick={() => navigate("/" + Pages.MyVehiclesUI)}
                  >
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
      <br></br>
      <br></br>

        <Row>
            <Col md={4}>
                <Card>
                    <Card.Body>
                        <Card.Title>AutoMob Search</Card.Title>
                        <Button variant="primary" onClick={() => navigate("/" + Pages.AutoMobSearchUI)}>Search</Button>
                    </Card.Body>
                </Card>
            </Col>

            <Col md={4}>
                <Card>
                    <Card.Body>
                        <Card.Title>Maintenance Predictions</Card.Title>
                        <Button variant="primary" onClick={() => navigate("/" + Pages.PredictMaintenanceUI)}>
                            Open Maintenance Predictions
                        </Button>
                    </Card.Body>
                </Card>
            </Col>

            <Col md={4}>
                <Card>
                    <Card.Body>
                        <Card.Title>Track My Vehicle</Card.Title>
                        <Button variant="primary" onClick={() => navigate("/" + Pages.TrackVehicleUI)}>Track Vehicle</Button>
                    </Card.Body>
                </Card>
            </Col>

            <Col md={4}>
                <Card>
                    <Card.Body>
                        <Card.Title>Emergency Assistance</Card.Title>
                        <Button variant="primary" onClick={() => navigate("/" + Pages.EmergencyAssistUI)}>Emergency Assistance</Button>
                    </Card.Body>
                </Card>
            </Col>

            <Col md={4}>
                <Card>
                    <Card.Body>
                        <Card.Title>Manage Vehicle Documents</Card.Title>
                        <Button variant="primary" onClick={() => navigate("/" + Pages.ManageDocumentsUI)}>
                            Manage Documents
                        </Button>
                    </Card.Body>
                </Card>
            </Col>

            <Col md={4}>
                <Card>
                    <Card.Body>
                        <Card.Title>Parking Finder</Card.Title>
                        <Button variant="primary" onClick={() => navigate("/" + Pages.ParkingFinderUI)}>Find Parking</Button>
                    </Card.Body>
                </Card>
            </Col>
        </Row>

    </>
  );
}

export default Dashboard;
