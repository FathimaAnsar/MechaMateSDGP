import React, { useState } from 'react'
import Header from "./components/Header.js";
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import CustomAlert from "./components/CustomAlert.js";

import axios from 'axios';
import { API_BASE_URL } from "../Common.js";

function MyVehicles(props) {
  const [vehicleType, setVehicleType] = useState('Car');
  const [fuelType, setFuelType] = useState(null);
  const [vehicleMake, setVehicleMake] = useState(null);
  const [vehicleModel, setVehicleModel] = useState(null);
  const [registrationNumber, setRegistrationNumber] = useState(null);
  const [regExpDate, setRegExpDate] = useState(new Date());
  const [insNo, setInsNo] = useState(null);
  const [insExpDate, setInsExpDate] = useState(new Date());
  const [showAlert, setShowAlert] = useState(false);
  const [showSuccessAlert, setShowSuccessAlert] = useState(false)
  const [error, setError] = useState({ message: "", heading: "" });



  const handleClose = () => {
    setShowAlert(false);
  };


  async function addVehicle(event) {
    event.preventDefault();

    const requestBody = {
      registrationNumber: registrationNumber,
      vehicleType: vehicleType,
      fuelType: fuelType,
      vehicleMake: vehicleMake,
      vehicleModel: vehicleModel,
      insNo: insNo,
      insExpDate: insExpDate,
      regExpDate: regExpDate
    };

    try {
      const response = await axios.post(`${API_BASE_URL}/api/v1/general/add-vehicle`, requestBody, {
        headers: {
          'Content-Type': 'application/json'
        },
        withCredentials: true // Use withCredentials instead of credentials
      });
      // Check if response is successful (status code 2xx)
      if (response.data.error) {
        setError({
          message: response.data.help,
          heading: response.data.message,
        });
        setShowAlert(true);

      } else if (response.data) {
        const responseData = response.data;
        const message = responseData.message;
        clearInputFields();
      }
    } catch (error) {
      alert("Vehicle registration failed");
      console.error('Error registering vehicle:', error);
    }
  }

  function clearInputFields() {
    // Clear input fields
    setRegistrationNumber("");
    setVehicleType("");
    setFuelType("");
    setVehicleMake("");
    setVehicleModel("");
    setInsNo("");
    setInsExpDate("");
    setRegExpDate("");
  }
  return (

    <div>
      <Header app={props.app} />

      <CustomAlert show={showAlert} handleClose={handleClose} error={error} />


      <Container fluid='sm' style={{ maxWidth: '400px', marginTop: '20px' }}>
        <Form>
          <Row className="mb-3 g-4" xs={1}>
            <Form.Group as={Col} controlId="floatingVehicleType">
              <FloatingLabel label="Vehicle Type">
                <Form.Select aria-label="Floating label select example" value={vehicleType} onChange={(event) => setVehicleType(event.target.value)}>
                  <option>Select Vehicle Type</option>
                  <option value="Car">Car</option>
                  <option value="SUV">SUV</option>
                  <option value="Van">Van</option>
                  <option value="Bus">Bus</option>
                  <option value="Truck">Truck</option>
                  <option value="Motorcycle">Motorcycle</option>
                </Form.Select>
              </FloatingLabel>
            </Form.Group>

            <Form.Group as={Col} controlId="floatingFuelType">
              <FloatingLabel label="Fuel Type">
                <Form.Select aria-label="Floating label select example" value={fuelType} onChange={(event) => setFuelType(event.target.value)}>
                  <option>Select Fuel Type</option>
                  <option value="Petrol">Petrol</option>
                  <option value="Diesel">Diesel</option>
                  <option value="LPGas">LPGas</option>
                  <option value="Electric">Electric</option>
                </Form.Select>
              </FloatingLabel>
            </Form.Group>
          </Row>

          <Row className="mb-3" xs={1}>
            <Form.Group as={Col} controlId="vehicleMake">
              <Form.Label>Vehicle Make</Form.Label>
              <Form.Control type="text" placeholder="Toyota" value={vehicleMake} onChange={(event) => setVehicleMake(event.target.value)} />
            </Form.Group>

            <Form.Group as={Col} controlId="vehicleModel">
              <Form.Label>Vehicle Model</Form.Label>
              <Form.Control type="text" placeholder="Camry" value={vehicleModel} onChange={(event) => setVehicleModel(event.target.value)} />
            </Form.Group>
          </Row>

          <Row className="mb-3" xs={1}>
            <Form.Group as={Col} controlId="registrationNumber">
              <Form.Label>Vehicle Registration Number</Form.Label>
              <Form.Control type="text" placeholder="ABC1234" value={registrationNumber} onChange={(event) => setRegistrationNumber(event.target.value)} />
            </Form.Group>

            <Form.Group as={Col} controlId="vred">
              <Form.Label>Vehicle Registration Expiration Date:</Form.Label>
              <DatePicker selected={regExpDate} onChange={(date) => setRegExpDate(date)} />
            </Form.Group>
          </Row>

          <Row className="mb-3" xs={1}>
            <Form.Group as={Col} controlId="insurancePolicyNumber">
              <Form.Label>Vehicle Insurance Policy Number</Form.Label>
              <Form.Control type="text" placeholder="ABC123456789" value={insNo} onChange={(event) => setInsNo(event.target.value)} />
            </Form.Group>

            <Form.Group as={Col} controlId="vied">
              <Form.Label>Vehicle Insurance Expiration Date:</Form.Label>
              <DatePicker selected={insExpDate} onChange={(date) => setInsExpDate(date)} />
            </Form.Group>
          </Row>

          <Row>
            <Col>
              <Button variant="primary" onClick={addVehicle}>Save</Button>
            </Col>
          </Row>
        </Form>
      </Container>

    </div>


  )
}

export default MyVehicles
