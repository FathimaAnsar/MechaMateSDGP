import React from "react";
import { Container, Button, Row, Col, Card, Stack } from "react-bootstrap";
import Header from "./components/Header";
import Image, { propTypes } from "react-bootstrap/esm/Image";
import {
  FaBatteryFull,
  FaTools,
  FaOilCan,
  FaCar,
  FaGasPump,
  FaBuilding,
  FaCalendarAlt,
} from "react-icons/fa";
import { useState } from "react";
import "./styles/viewVehicle.css";
import { useLocation } from "react-router-dom";
import { useEffect } from "react";
import camryImage from "../images/vehicles/camry-side.jpg";
import vitzImage from "../images/vehicles/vitz.jpg";
import civicImage from "../images/vehicles/civic-side.jpg";
import defaultImage from "../images/vehicles/default.jpg";
import corollaImage from "../images/vehicles/corolla-side.jpg";

export default function ViewVehicle(props) {
  const location = useLocation();
  const index = new URLSearchParams(location.search).get("vehicle");
  const [vehicle, setVehicle] = useState(null);
  const [vehicleList, setVehicleList] = useState([]);

  const carImages = {
    Camry: camryImage, // Assuming you've imported the image statically
    Civic: civicImage,
    Vitz: vitzImage,
    Corolla: corollaImage,
    default: defaultImage,
  };

  useEffect(() => {
    const storedVehicleList = JSON.parse(localStorage.getItem("vehicleList"));
    if (storedVehicleList) {
      setVehicleList(storedVehicleList);
    }
  }, []);

  useEffect(() => {
    if (index !== null && index !== "") {
      if (index >= 0 && index < vehicleList.length) {
        setVehicle(vehicleList[index]);
      }
    }
  }, [index, vehicleList]);

  const imageUrl = vehicle
    ? carImages[vehicle.vehicleModel] || carImages["default"]
    : carImages["default"];

  const fuelCapacity = "Remaining: 89%";
  const battery = "30%";
  const maintenance = "in 29043km";
  const oilChange = "Remaining: 60km";

  const calculateDaysDifference = (date) => {
    const today = new Date();
    const maintenanceDate = new Date(date);
    const differenceInTime = today.getTime() - maintenanceDate.getTime();
    const differenceInDays = Math.floor(differenceInTime / (1000 * 3600 * 24));
    return differenceInDays;
  };

  const [maintenanceRecords] = useState([
    {
      id: 1,
      title: "Wheel Alignment",
      description: "Balanced all 4 wheels and replaced a bearing.",
      date: "2024-03-10", // Example date (YYYY-MM-DD format)
    },

    //     description "descpriyjfnd"
    // appliedMaintenanceId
    // "WheelAlignment"
    // nextServiceInKMs
    // 1000
    // serviceQuality
    // "High"
    {
      id: 2,
      title: "Engine Tune-Up",
      description:
        "Performed a thorough engine tune-up including spark plug replacement and ignition system inspection.",
      date: "2024-02-28", // Example date (YYYY-MM-DD format)
    },
    {
      id: 3,
      title: "Brake Inspection",
      description:
        "Inspected brake pads, rotors, and calipers for wear and tear. Replaced brake fluid.",
      date: "2024-02-15", // Example date (YYYY-MM-DD format)
    },
    {
      id: 4,
      title: "Oil Change",
      description:
        "Changed engine oil and oil filter for improved engine performance and longevity.",
      date: "2024-01-20", // Example date (YYYY-MM-DD format)
    },
  ]);

  return (
    <>
      <Header app={props.app} />
      {/* <Button variant='dark' onClick={() => props.app.goBack()}>Go Back</Button> */}
      <div>
        {vehicle && (
          <Container style={{ marginTop: "20px" }}>
            <Row className="text-center">
              <h2>
                {vehicle.vehicleMake} {vehicle.vehicleModel}
              </h2>
              <p style={{ color: "gray", marginTop: "-10px" }}>
                {2024} • {vehicle.fuelType}
              </p>
            </Row>
            <Row className="d-flex align-items-center">
              <Image style={{ width: "100%" }} src={imageUrl} alt="car" />
            </Row>
            <Row className="text-center">
              <p style={{ color: "gray" }}>{vehicle.registrationNumber}</p>
            </Row>

            <Row>
              <h3>Status</h3>
            </Row>
            <Row xs={2} md={3}>
              <Col>
                <Card className="shadow-glow-success">
                  <Card.Body className="card-body-with-icon">
                    <div>
                      <Card.Title className="card-title">Fuel </Card.Title>
                      <Card.Text className="card-text">
                        {fuelCapacity}
                      </Card.Text>
                    </div>
                    <FaGasPump className="icon me-1" />
                  </Card.Body>
                </Card>
              </Col>
              <Col>
                <Card className="shadow-glow-warning">
                  <Card.Body className="card-body-with-icon">
                    <div>
                      <Card.Title className="card-title">Battery</Card.Title>
                      <Card.Text className="card-text">{battery}</Card.Text>
                    </div>
                    <FaBatteryFull className="icon me-1" />
                  </Card.Body>
                </Card>
              </Col>
              <Col>
                <Card className="shadow-glow-success">
                  <Card.Body className="card-body-with-icon">
                    <div>
                      <Card.Title className="card-title">
                        Maintenance
                      </Card.Title>
                      <Card.Text className="card-text">{maintenance}</Card.Text>
                    </div>
                    <FaTools className="icon me-1" />
                  </Card.Body>
                </Card>
              </Col>
              <Col>
                <Card className="shadow-glow-danger">
                  <Card.Body className="card-body-with-icon">
                    <div>
                      <Card.Title className="card-title">Oil Change</Card.Title>
                      <Card.Text className="card-text">{oilChange}</Card.Text>
                    </div>
                    <FaOilCan className="icon me-1" />
                  </Card.Body>
                </Card>
              </Col>
              <Col>
                <Card className="shadow-glow-blue">
                  <Card.Body className="card-body-with-icon">
                    <div>
                      <Card.Title className="card-title">
                        Current Mileage
                      </Card.Title>
                      <Card.Text className="card-text">
                        {vehicle.currentMileage}km
                      </Card.Text>
                    </div>
                    <FaCar className="icon me-1" />
                  </Card.Body>
                </Card>
              </Col>
            </Row>
            {/* <Row><h3>Details</h3></Row>
        <Row xs={2} md={3} className="g-2">
          
          <Col>
            <Card className="shadow-glow-blue">
              <Card.Body className="card-body-with-icon">
                <div>
                  <Card.Title className="card-title">Fuel Type</Card.Title>
                  <Card.Text className="card-text">{fuelType}</Card.Text>
                </div>
                <FaGasPump className="icon me-1" />
              </Card.Body>
            </Card>
          </Col>
        </Row> */}
            <Row>
              <h3>Recent Activity</h3>
            </Row>
            <Row xs={1} md={1}>
              {maintenanceRecords.map((record) => (
                <Col key={record.id}>
                  <Card className="shadow-glow-blue">
                    <Card.Body className="card-body-with-icon">
                      <div>
                        <Card.Title className="card-title">
                          {record.title}
                        </Card.Title>
                        <Card.Text className="card-text">
                          {record.description}
                        </Card.Text>
                        <Card.Text className="disabled">
                          {calculateDaysDifference(record.date)} day(s) ago
                        </Card.Text>
                      </div>
                    </Card.Body>
                  </Card>
                </Col>
              ))}
            </Row>
            <Row>
              <h3>Other</h3>
            </Row>
            <Row xs={2} md={3}>
              <Col>
                <Card className="shadow-glow-blue">
                  <Card.Body className="card-body-with-icon">
                    <div>
                      <Card.Title className="card-title">
                        Insurance Number
                      </Card.Title>
                      <Card.Text className="card-text">
                        {vehicle.insNo}
                      </Card.Text>
                    </div>
                    <FaBuilding className="icon me-3" />
                  </Card.Body>
                </Card>
              </Col>
              <Col>
                <Card className="shadow-glow-blue">
                  <Card.Body className="card-body-with-icon">
                    <div>
                      <Card.Title className="card-title">
                        Insurance Expiry Date
                      </Card.Title>
                      <Card.Text className="card-text">
                        {vehicle.insExpDate.slice(0, 10)}
                      </Card.Text>
                    </div>
                    <FaCalendarAlt className="icon me-3" />
                  </Card.Body>
                </Card>
              </Col>
              <Col>
                <Card className="shadow-glow-blue">
                  <Card.Body className="card-body-with-icon">
                    <div>
                      <Card.Title className="card-title">
                        Registration Expiry Date
                      </Card.Title>
                      <Card.Text className="card-text">
                        {vehicle.regExpDate.slice(0, 10)}
                      </Card.Text>
                    </div>
                    <FaCalendarAlt className="icon me-3" />
                  </Card.Body>
                </Card>
              </Col>

              {/* <Col>
            <Card className="shadow-glow-blue">
              <Card.Body className="card-body-with-icon">
                <FaClipboardList className="icon me-3" />
                <div>
                  <Card.Title className="card-title">Service Records</Card.Title>
                  <Card.Text className="card-text">{JSON.stringify(serviceRecords)}</Card.Text>
                </div>
              </Card.Body>
            </Card>
          </Col> */}
            </Row>
          </Container>
        )}
        {!vehicle && <p>Loading...</p>}
      </div>
    </>
  );
}
