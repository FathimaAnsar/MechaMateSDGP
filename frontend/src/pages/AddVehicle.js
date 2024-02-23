import React from 'react'
import Header from "../components/Header";
import Form from 'react-bootstrap/Form';
import FloatingLabel from 'react-bootstrap/FloatingLabel';

function AddVehicle() {
  return (
    <div>
        <Header/>
        <FloatingLabel controlId="floatingSelect" label="Vehicle Type">
        <Form.Select aria-label="Floating label select example">
        <option>Select Vehicle Type</option>
        <option value="1">Car</option>
        <option value="2">Van</option>
        <option value="3">SUV</option>
        <option value="4">Bus</option>
        <option value="5">Lorry</option>
        <option value="6">Motorcycle</option>
      </Form.Select>
      </FloatingLabel>

      <FloatingLabel controlId="floatingSelect" label="Fuel Type">
        <Form.Select aria-label="Floating label select example">
        <option>Select Fuel Type</option>
        <option value="1">Petrol</option>
        <option value="2">Diesel</option>
        <option value="3">LPGas</option>
        <option value="4">Electric</option>
        
      </Form.Select>
      </FloatingLabel>
        
     
    </div>
  )
}

export default AddVehicle
