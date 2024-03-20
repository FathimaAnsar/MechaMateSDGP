import React, { useState, useEffect } from "react";
import {
  Container,
  Row,
  Col,
  Card,
  Button,
  Form,
  Alert,
} from "react-bootstrap";
import ConnectionManager from "../services/ConnectionManager";
import { Pages } from "../Pages";
import Header from "./components/Header";
import "./styles/ParkingFinder.css";

function ParkingFinder(props) {
  const [currentLocation, setCurrentLocation] = useState(null);
  const [parkingSpaces, setParkingSpaces] = useState([]);
  const [varCap, setVarCap] = useState("");
  const [loading, setLoading] = useState(false);
  const [alertInfo, setAlertInfo] = useState({ show: false, message: "" });
  const [selectedMapUri, setSelectedMapUri] = useState("");
  const [expandedInfo, setExpandedInfo] = useState({});

  useEffect(() => {
    props.app
      .getCurrentLocation()
      .then((location) => {
        setCurrentLocation(location);
      })
      .catch((exp) => {
        displayAlert(exp.message || "Failed to get location information");
        props.app.changePage(Pages.DashboardUI);
      });
  }, [props.app]);

  const displayAlert = (message) => {
    setAlertInfo({ show: true, message });
    setTimeout(() => setAlertInfo({ show: false, message: "" }), 5000);
  };

  const fetchNearbyParking = async () => {
    setLoading(true);
    const limit = document.getElementById("limit").value;
    const radius = document.getElementById("radius").value;

    let connection = new ConnectionManager();

    try {
      const resp = await connection.getNearbyParking(
        currentLocation.latitude,
        currentLocation.longitude,
        radius,
        limit
      );
      const response = JSON.parse(resp);
      console.log(response);

      if (response.error) {
        displayAlert(`Error occurred: ${response.message}`);
      } else {
        setVarCap("Parking Spaces");
        setParkingSpaces(response.places);
      }
    } catch (error) {
      displayAlert("Please check your Spring Boot service is running");
    } finally {
      setLoading(false);
    }
  };
  const showOnMap = (location) => {
    if (location && location.latitude && location.longitude) {
      const mapsUrl = `https://maps.google.com/maps?q=${location.latitude},${location.longitude}&z=15&output=embed`;
      setSelectedMapUri(mapsUrl);
    } else {
      displayAlert("Invalid location data.");
    }
  };
  const getDirections = (endLocation) => {
    if (currentLocation && endLocation) {
      const directionsUrl = `https://www.google.com/maps/dir/?api=1&origin=${currentLocation.latitude},${currentLocation.longitude}&destination=${endLocation.latitude},${endLocation.longitude}&travelmode=driving`;
      window.open(directionsUrl, "_blank");
    } else {
      displayAlert("Location data for start or destination is missing.");
    }
  };

  const toggleDetails = (index) => {
    setExpandedInfo((prevState) => ({
      ...prevState,
      [index]: !prevState[index],
    }));
  };

  return (
    <>
      <Header app={props.app} />
      <Container>
        <Row className="mt-3">
          <Col>
            <h2 className="text-center mb-4">Finding a parking place!</h2>
            <h4 className="text-secondary text-center mb-3">You are here!</h4>
            <div className="map-container mb-4">
              <iframe
                title="map"
                src={
                  selectedMapUri ||
                  `https://maps.google.com/maps?q=${
                    currentLocation ? currentLocation.latitude : ""
                  },${
                    currentLocation ? currentLocation.longitude : ""
                  }&z=15&output=embed`
                }
                width="100%"
                height="300"
                frameBorder="0"
                style={{ border: 0 }}
                allowFullScreen="true"
              ></iframe>
            </div>
          </Col>
        </Row>

        <Row className="my-3">
          <Col xs={12} md={6}>
            <Form.Group controlId="limit">
              <Form.Label>Number of Results</Form.Label>
              <Form.Control as="select" defaultValue="5">
                <option value="5">Show only 5 results</option>
                <option value="10">Show only 10 results</option>
                <option value="15">Show only 15 results</option>
                <option value="20">Show only 20 results</option>
              </Form.Control>
            </Form.Group>
          </Col>
          <Col xs={12} md={6}>
            <Form.Group controlId="radius">
              <Form.Label>Search Radius</Form.Label>
              <Form.Control as="select" defaultValue="500">
                <option value="500">Within 500 meters</option>
                <option value="1000">Within 1 kilometer</option>
                <option value="2000">Within 2 kilometers</option>
                <option value="5000">Within 5 kilometers</option>
                <option value="10000">Within 10 kilometers</option>
              </Form.Control>
            </Form.Group>
          </Col>
        </Row>

        <Row className="my-3">
          <Col>
            <Button
              variant="primary"
              onClick={fetchNearbyParking}
              disabled={loading}
            >
              {loading ? "Loading..." : "Show Me Nearby Parking Spaces"}
            </Button>
          </Col>
        </Row>

        {alertInfo.show && (
          <Row className="my-3">
            <Col>
              <Alert
                variant="danger"
                onClose={() => setAlertInfo({ show: false, message: "" })}
                dismissible
              >
                {alertInfo.message}
              </Alert>
            </Col>
          </Row>
        )}

        <Row className="my-3">
          {parkingSpaces.map((parking, index) => (
            <Col key={index} sm={12} md={6} lg={4} className="mb-3">
              <Card>
                <Card.Body>
                  <Card.Title>{parking.displayName.text}</Card.Title>
                  {expandedInfo[index] && (
                    <div>
                      <Card.Text>Address: {parking.formattedAddress}</Card.Text>
                    </div>
                  )}
                  <Button
                    variant="primary"
                    onClick={() => toggleDetails(index)}
                  >
                    {expandedInfo[index] ? "Hide Details" : "Show Details"}
                  </Button>
                  <Button
                    variant="primary"
                    onClick={() => showOnMap(parking.location)}
                  >
                    View on Map
                  </Button>
                  <Button
                    variant="primary"
                    onClick={() => getDirections(parking.location)}
                  >
                    Get Directions
                  </Button>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      </Container>
    </>
  );
}

export default ParkingFinder;
