import React from 'react';
import Header from "./components/Header";
import { Pages } from "../Pages";
import ConnectionManager from '../services/ConnectionManager';
import { useNavigate } from "react-router-dom";
import { Button, Container, Row, Col, Form } from 'react-bootstrap';

function ManageDocuments(props) {
  const navigate = useNavigate();

  const handleQRButtonClick = async () => {
    let connection = new ConnectionManager();
    const regNoQR = document.getElementById("regNoQR").value;
    const resp = await connection.getRequestForQR(regNoQR);
    const response = JSON.parse(resp);

    if (!response) {
      alert("Please check your springboot localhost is running");
      return;
    }

    if (response.error) {
      alert("Error: " + response.message);      
    } else if (response.url) {

      alert("Error: " + response.url);      


      navigate("/" + Pages.QrUI, { state: { qrurl: response.url } });
    } else {
      alert("Error: Unknown");
    }
  };




  return (
    <div>
      <Header app={props.app} />
      <Container className="text-center">
        <h2>Manage Documents</h2>
        <Row className="justify-content-center">
          <Col md="6" className="text-center">            
            <Button variant="primary" className="mb-3" onClick={() => navigate("/" + Pages.AddSRecordManualUI)}>
              Add Service Record Manually
            </Button>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="6" className="text-center">
          <Form onSubmit={handleQRButtonClick}>
                <Form.Group>
                    <Form.Control type="text" id="regNoQR" placeholder="Enter vehicle registration number here" required />
                </Form.Group>
              <Button variant="success" type="submit" className="mt-3">
              Add Service Record by QR Code
            </Button>

            </Form>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default ManageDocuments;
