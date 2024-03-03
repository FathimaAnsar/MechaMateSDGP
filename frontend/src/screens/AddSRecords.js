import React, { useState } from 'react';
import Header from "./components/Header";
import { Form, Button } from 'react-bootstrap';
import axios from 'axios';

function AddServiceRecordByServiceProvider(props) {
    const [serviceType, setServiceType] = useState('');
    const [description, setDescription] = useState('');
    const [date, setDate] = useState(new Date());
    const [mileage, setMileage] = useState('');

    async function addSRecord(event) {
        event.preventDefault();
        try {
          await axios.post("http://localhost:8080/api/v1/general/add-service-record", {
            description: description,
            date: date,
            mileage: mileage
          });
          alert("Service Record Added Successfully");
          setServiceType('');
          setDescription('');
          setDate(new Date());
          setMileage('');
        } catch (err) {
          alert("Failed to Add Service Record");
        }
      }


  return (
    <div>
        <Header app ={props.app}/>
      <h2>Add Service Records Manually</h2>

      <Form>
        <Form.Group controlId="serviceType">
          <Form.Label>Service Type:</Form.Label>
          <Form.Control as="select">
            <option value="WheelAlignment">Wheel Alignment</option>
            <option value="EngineOilChange">Engine Oil Change</option>
            <option value="BrakeFluidChange">Brake Fluid Change</option>
            <option value="BrakeCaliperChange">Brake Caliper Change</option>
            <option value="CoolantChange">Coolant Change</option>
            <option value="TireChange">Tire Change</option>
            <option value="PistonRingsChange">Piston Rings Change</option>
            <option value="PistonChange">Piston Change</option>
            <option value="DieselFilterChange">Diesel Filter Change</option>
            <option value="VipersChange">Vipers Change</option>
          </Form.Control>
        </Form.Group>

        <Form.Group controlId="description">
          <Form.Label>Description:</Form.Label>
          <Form.Control as="textarea" rows={4} />
        </Form.Group>

        <Form.Group controlId="date">
          <Form.Label>Service Date:</Form.Label>
          <Form.Control type="date" />
        </Form.Group>

        <Form.Group controlId="mileage">
          <Form.Label>Mileage:</Form.Label>
          <Form.Control type="number" />
        </Form.Group>

        <Button variant="primary" onClick={addSRecord}>Submit</Button> 
      </Form>

      <Button onClick={() => props.app.changePage('ManageMyDocuments')}>
        Go Back
      </Button>
    </div>
  );
}

export default AddServiceRecordByServiceProvider;
