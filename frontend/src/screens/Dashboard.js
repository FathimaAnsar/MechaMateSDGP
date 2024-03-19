import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  Button,
  Image,
  Col,
  Container,
  Row,
  Spinner,
  Accordion,
} from "react-bootstrap";
import { Pages } from "../Pages.js";
import ConnectionManager from "../services/ConnectionManager.js";
import Header from "./components/Header.js";
import ClickableCard from "./components/ClickableCard.js";
import CustomCarousel from "./components/CustomCarousel.js";
import ViewVehicle from "./ViewVehicle.js";
import LoadingScreen from "./components/LoadingScreen.js";
import corollaImage from "../images/vehicles/corolla-side.jpg";
import DashboardCard from "./components/DashboardCard.js";

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

  const emergencyAssist = [
    { title: "Manage Documents", path: "/" + Pages.ManageDocumentsUI },
    { title: "Breakdown Assistance", path: "/" + Pages.EmergencyAssistUI },
    { title: "AutoMob Search", path: "/" + Pages.AutoMobSearchUI },
    {
      title: "Track My Vehicle",
      path: "/" + Pages.EmergencTrackVehicleUIyAssistUI,
    },

    { title: "Parking Finder", path: "/" + Pages.ParkingFinderUI },
    { title: "Accident Assistance", path: "/" + Pages.AccidentAssistUi },
  ];

  const handleOptionClick = () => {
    return;
  };

  return (
    <>
      <Header app={props.app} />
      <Container fluid style={{ padding: "5px 15px" }}>
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
        <Row>
          <Col>
            <div>
              <CustomCarousel />
            </div>
          </Col>
        </Row>
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
        <br />

        <Row>
          <Col md={12}>
            <h3>Services</h3>
          </Col>

          {emergencyAssist.map((option, index) => (
            <Col key={index} xs={6} sm={6} md={4} lg={3}>
              <div>
                <DashboardCard
                  content={option}
                  onClick={() => navigate(option.path)}
                />
              </div>
            </Col>
          ))}
        </Row>

        <br />

        <Accordion>
          {/* 
          <Accordion.Item eventKey="0">
            <Accordion.Header style={{ fontWeight: "bold", fontSize: "20px" }}>
              Predict Maintenance
            </Accordion.Header>
            <Accordion.Body>
              <Row className="justify-content-center text-center">
                <Col xs={12} md={8}>
                  <Image
                    style={{ width: "80%" }}
                    src={corollaImage}
                    alt="car"
                  />
                  <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad
                  </p>
                  <Button
                    variant="primary"
                    style={{ width: "80%" }}
                    onClick={() => navigate("/" + Pages.PredictMaintenanceUI)}
                  >
                    Predictive Now
                  </Button>
                </Col>
              </Row>
            </Accordion.Body>
          </Accordion.Item> 
          <Accordion.Item eventKey="1">
            <Accordion.Header style={{ fontWeight: "bold", fontSize: "20px" }}>
              AutoMob Search
            </Accordion.Header>
            <Accordion.Body>
              <Row className="justify-content-center text-center">
                <Col xs={12} md={8}>
                  <Image
                    style={{ width: "80%" }}
                    src={corollaImage}
                    alt="car"
                  />
                  <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad
                  </p>
                  <Button
                    variant="primary"
                    style={{ width: "80%" }}
                    onClick={() => navigate("/" + Pages.AutoMobSearchUI)}
                  >
                    Search Now
                  </Button>
                </Col>
              </Row>
            </Accordion.Body>
          </Accordion.Item>
          <Accordion.Item eventKey="2">
            <Accordion.Header style={{ fontWeight: "bold", fontSize: "20px" }}>
              Emergency Assistance
            </Accordion.Header>
            <Accordion.Body>
              <Row className="justify-content-center text-center">
                <Col xs={12} md={8}>
                  <Image
                    style={{ width: "80%" }}
                    src={corollaImage}
                    alt="car"
                  />
                  <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad
                  </p>
                  <Button
                    variant="primary"
                    style={{ width: "80%" }}
                    onClick={() => navigate("/" + Pages.EmergencyAssistUI)}
                  >
                    Need Assistance
                  </Button>
                </Col>
              </Row>
            </Accordion.Body>
          </Accordion.Item>
          <Accordion.Item eventKey="3">
            <Accordion.Header style={{ fontWeight: "bold", fontSize: "20px" }}>
              Track My Vehicle
            </Accordion.Header>
            <Accordion.Body>
              <Row className="justify-content-center text-center">
                <Col xs={12} md={8}>
                  <Image
                    style={{ width: "80%" }}
                    src={corollaImage}
                    alt="car"
                  />
                  <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad
                  </p>
                  <Button
                    variant="primary"
                    style={{ width: "80%" }}
                    onClick={() => navigate("/" + Pages.TrackVehicleUI)}
                  >
                    Search Now
                  </Button>
                </Col>
              </Row>
            </Accordion.Body>
          </Accordion.Item>
          <Accordion.Item eventKey="4">
            <Accordion.Header style={{ fontWeight: "bold", fontSize: "20px" }}>
              Manage Vehicle Documents
            </Accordion.Header>
            <Accordion.Body>
              <Row className="justify-content-center text-center">
                <Col xs={12} md={8}>
                  <Image
                    style={{ width: "80%" }}
                    src={corollaImage}
                    alt="car"
                  />
                  <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad
                  </p>
                  <Button
                    variant="primary"
                    style={{ width: "80%" }}
                    onClick={() => navigate("/" + Pages.ManageDocumentsUI)}
                  >
                    Manage Now
                  </Button>
                </Col>
              </Row>
            </Accordion.Body>
          </Accordion.Item>
          <Accordion.Item eventKey="5">
            <Accordion.Header style={{ fontWeight: "bold", fontSize: "20px" }}>
              Parking Finder
            </Accordion.Header>
            <Accordion.Body>
              <Row className="justify-content-center text-center">
                <Col xs={12} md={8}>
                  <Image
                    style={{ width: "80%" }}
                    src={corollaImage}
                    alt="car"
                  />
                  <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad
                  </p>
                  <Button
                    variant="primary"
                    style={{ width: "80%" }}
                    onClick={() => navigate("/" + Pages.ParkingFinderUI)}
                  >
                    Search Parking Now
                  </Button>
                </Col>
              </Row>
            </Accordion.Body>
          </Accordion.Item>*/}
        </Accordion>
      </Container>
    </>
  );
}

export default Dashboard;
