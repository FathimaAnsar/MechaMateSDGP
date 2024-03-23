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
import { useNavigate } from "react-router-dom";

function BreakdownAssist(props) {

  const navigate = useNavigate();

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
      <Header />
      <Container
        style={{
          width: "100vw",
          height: "93vh",
          zIndex: 999,
          margin: 0,
          padding: 0,
          position: "fixed",
        }}
        fluid
      >
        <iframe
          title="map"
          src={
            selectedMapUri ||
            `https://maps.google.com/maps?q=${currentLocation ? currentLocation.latitude : ""
            },${currentLocation ? currentLocation.longitude : ""
            }&z=15&output=embed`
          }
          style={{
            // border: 0,
            width: "100%",
            height: "100%",
          }}
        ></iframe>
      </Container>
    </>
  );
}

export default BreakdownAssist;
