import React from 'react';
import Header from "./components/Header";
import { Pages } from "../Pages";
import ConnectionManager from '../services/ConnectionManager';
import { useNavigate } from "react-router-dom";
import { Button, Container, Row, Col } from 'react-bootstrap';

function ManageDocuments(props) {
  const navigate = useNavigate();

  const handleQRButtonClick = async () => {
    let connection = new ConnectionManager();

    const resp = await connection.getRequestForQR();
    const response = JSON.parse(resp);

    if (!response) {
      alert("Please check your springboot localhost is running");
      return;
    }

    if (response.error) {
      
    } else if (response.url) {
      
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
            
            <Button variant="success" onClick={handleQRButtonClick}>
              Add Service Record by QR Code
            </Button>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default ManageDocuments;
