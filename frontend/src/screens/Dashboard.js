import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  Button,
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
import DashboardCard from "./components/DashboardCard.js";
import LoadingScreen from "./components/LoadingScreen.js";

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


  const servicesList = [
    { title: "Manage Documents", path: "/" + Pages.ManageDocumentsUI },
    { title: "Breakdown Assistance", path: "/" + Pages.BreakdownAssistUI },
    { title: "AutoMob Search", path: "/" + Pages.AutoMobSearchUI },
    {
      title: "Track My Vehicle",
      path: "/" + Pages.TrackVehicleUI,
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
      {loading ? (
        <LoadingScreen />
      ) : (
        <>
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
                        onClick={() => navigate("/" + Pages.AddVehiclesUI)}
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

              {servicesList.map((option, index) => (
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
          </Container>
        </>

      )}
    </>
  );
}

export default Dashboard;
