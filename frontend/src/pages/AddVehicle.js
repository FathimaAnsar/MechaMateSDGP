import React, { useState } from 'react'
import Header from "../components/Header";
import Form from 'react-bootstrap/Form';
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";

function AddVehicle() {
  const [vreDate, setVreDate] = useState(new Date());
  const [vieDate, setVieDate] = useState(new Date());
  return (
    <div>
        <Header/>
        <Form>
        <FloatingLabel controlId="floatingSelect" label="Vehicle Type" className='dropdown'>
        <Form.Select aria-label="Floating label select example">
        <option>Select Vehicle Type</option>
        <option value="1">Car</option>
        <option value="2">Van</option>
        <option value="3">Bus</option>
        <option value="4">Lorry</option>
        <option value="5">Motorcycle</option>
        <option value="6">Other</option>
      </Form.Select>
      </FloatingLabel>

      <FloatingLabel controlId="floatingSelect" label="Fuel Type" className='dropdown'>
        <Form.Select aria-label="Floating label select example">
        <option>Select Fuel Type</option>
        <option value="1">Petrol</option>
        <option value="2">Diesel</option>
        <option value="3">LPGas</option>
        <option value="4">Electric</option>
        
      </Form.Select>
      </FloatingLabel>

      <Form.Label>Vehicle Make</Form.Label>
      <Form.Control className='textbox' type="text" placeholder="Toyota" />
      <Form.Label>Vehicle Model</Form.Label>
      <Form.Control className='textbox'  type="text" placeholder="Camry" />
      <Form.Label>Vehicle Registration Number</Form.Label>
      <Form.Control className='textbox'  type="text" placeholder="ABC1234" />

      <Form.Group controlId="vred">
      <Form.Label>Vehicle Registratration Expiration Date:  </Form.Label> 
      <DatePicker selected={vreDate} onChange={(date) => setVreDate(date)} /><br/>
      </Form.Group>
      <Form.Group controlId="vied">
      <Form.Label>Vehicle Insurance Expiration Date:  </Form.Label> 
      <DatePicker selected={vieDate} onChange={(date) => setVieDate(date)} />
      </Form.Group>
      </Form>
    </div>
  )
}

export default AddVehicle
