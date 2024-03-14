import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { Pages } from "../Pages.js";
import ConnectionManager from "../services/ConnectionManager.js";
import Header from "./components/Header.js";
import ClickableCard from "./components/ClickableCard.js";
import Stack from "react-bootstrap/Stack";
import CustomCarousel from "./components/CustomCarousel.js";
import { Button } from "react-bootstrap";
import Spinner from "react-bootstrap/Spinner";
import ViewVehicle from "./ViewVehicle.js";
import { useNavigate } from "react-router-dom";
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
      <div id="AutoMobSection">
        <h2 id="dash-autoS-heading">AutoMob Search</h2>
        <button
          id="dash-autS_btn"
          onClick={() => {
            navigate("/" + Pages.AutoMobSearchUI);
          }}
        >
          <span style={{ marginRight: "5px" }}></span> {/* Material Icon */}
          Search
        </button>
      </div>

      <div id="PredictiveMaintenance">
        <h2 id="dash-pred-heading">Maintenance predictions</h2>
        <button
          id="dash-pred_btn"
          onClick={() => {
            navigate("/" + Pages.PredictMaintenanceUI);
          }}
        >
          <span style={{ marginRight: "5px" }}>🛠️</span> {/* Material Icon */}
          Open Maintenance predictions
        </button>
      </div>

      <div id="Track my vehicle">
        <h2 id="dash-track-heading">Track my Vehicle</h2>
        <button
          id="dash-track_btn"
          onClick={() => {
            navigate("/" + Pages.TrackVehicleUI);
          }}
        >
          <span style={{ marginRight: "5px" }}></span> {/* Material Icon */}
          Open To Track my vehicle
        </button>
      </div>

      <div id="emergency assistence">
        <h2 id="dash-emergen-heading">Emergency assistence</h2>
        <button
          id="dash-emergen_btn"
          onClick={() => {
            navigate("/" + Pages.EmergencyAssistUI);
          }}
        >
          <span style={{ marginRight: "5px" }}></span> {/* Material Icon */}
          Open Emergency assistence
        </button>
      </div>

      <div id="manage my documents">
        <h2 id="dash-doc-heading">Manage vehicle documents</h2>
        <button
          id="dash-doc_btn"
          onClick={() => {
            navigate("/" + Pages.ManageDocumentsUI);
          }}
        >
          <span style={{ marginRight: "5px" }}></span> {/* Material Icon */}
          Open vehicle documents/sercice records
        </button>
      </div>

      <div id="parking finder">
        <h2 id="dash-park-heading">Parking Finder</h2>
        <button
          id="dash-park_btn"
          onClick={() => {
            navigate("/" + Pages.ParkingFinderUI);
          }}
        >
          <span style={{ marginRight: "5px" }}></span> {/* Material Icon */}
          Open to Find a parking place
        </button>
      </div>
    </>
  );
}

export default Dashboard;
