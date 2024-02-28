import React, { useState, useEffect } from 'react'
import Header from "../components/Header";
import Form from 'react-bootstrap/Form';
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import DatePicker from "react-datepicker"; 
import Button from 'react-bootstrap/Button';
import "react-datepicker/dist/react-datepicker.css";
import axios from 'axios';



const renderTableRow = (label, value) => {
  return (
    <tr key={label}>
      <td>{label}</td>
      <td>{value}</td>
    </tr>
  );
};

// Function to render all table rows
export const renderTableRows = (vehicleType, fuelType, vehicleMake, vehicleModel, regNo, regExpDate, insNo, insExpDate) => {
  return [
    renderTableRow("Vehicle Type", vehicleType),
    renderTableRow("Fuel Type", fuelType),
    renderTableRow("Vehicle Make", vehicleMake),
    renderTableRow("Vehicle Model", vehicleModel),
    renderTableRow("Vehicle Registration Number", regNo),
    renderTableRow("Vehicle Registration Expiration Date", regExpDate.toDateString()),
    renderTableRow("Vehicle Insurance Policy Number", insNo),
    renderTableRow("Vehicle Insurance Expiration Date", insExpDate.toDateString())
  ];
};

function AddVehicle() {
  // all fields
  const [_id, setId] =useState(null);
  const [vehicleType, setVehicleType] = useState('Car');
  const [fuelType, setFuelType] = useState(null);
  const [vehicleMake, setVehicleMake] = useState(null);
  const [vehicleModel, setVehicleModel] = useState(null);
  const [regNo, setRegNo] = useState(null);
  const [regExpDate, setRegExpDate] = useState(new Date());
  const [insNo, setInsNo] = useState(null);
  const [insExpDate, setInsExpDate] = useState(new Date());
 // const [vehicles, setVehicles ] =useState([]);

  // add vehicle with api logic
  async function addVehicle(event)
  {
      event.preventDefault();
  try
      {
       await axios.post("http://localhost:8080/api/v1/general/add-vehicle",
      {
        regNo: regNo,
        vehicleType: vehicleType,
        fuelType: fuelType,
        vehicleMake: vehicleMake,
        vehicleModel: vehicleModel,
        insNo: insNo,
        insExpDate: insExpDate,
        regExpDate: regExpDate
      });
        alert("Vehicle Registation Successful");
        //setId("");
        setRegNo("");
        setVehicleType("");
        setFuelType("");
        setVehicleMake("");
        setVehicleModel("");
        setInsNo("");
        setInsExpDate("");
        setRegExpDate("");

      }
  catch(err)
      {
        alert("Vehicle Registation Failed");
      }
 }


  return (
    <div>
        <Header/>
        <Form>
        <FloatingLabel controlId="floatingSelect" label="Vehicle Type" className='dropdown'>
        <Form.Select aria-label="Floating label select example"  value={vehicleType}
          onChange={(event) => {
            setVehicleType(event.target.value);
          }}>
        <option>Select Vehicle Type</option>
        <option value="Car">Car</option>
        <option value="Van">Van</option>
        <option value="Bus">Bus</option>
        <option value="Lorry">Lorry</option>
        <option value="Motorcycle">Motorcycle</option>

      </Form.Select>
      </FloatingLabel>
      
      <FloatingLabel controlId="floatingSelect" label="Fuel Type" className='dropdown'>
        <Form.Select aria-label="Floating label select example" value={fuelType}
        onChange={(event) => {
          setFuelType(event.target.value);
        }}>
        <option>Select Fuel Type</option>
        <option value="Petrol">Petrol</option>
        <option value="Diesel">Diesel</option>
        <option value="LPGas">LPGas</option>
        <option value="Electric">Electric</option>
      </Form.Select>
      </FloatingLabel>
     
      <Form.Label>Vehicle Make</Form.Label>
      <Form.Control className='textbox' type="text" placeholder="Toyota" value={vehicleMake}
      onChange={(event)=>{
        setVehicleMake(event.target.value);
      }}
      />
      <Form.Label>Vehicle Model</Form.Label>
      <Form.Control className='textbox'  type="text" placeholder="Camry" value={vehicleModel} 
      onChange={(event)=>{
        setVehicleModel(event.target.value);
      }}
      />
      <Form.Label>Vehicle Registration Number</Form.Label>
      <Form.Control className='textbox'  type="text" placeholder="ABC1234" value={regNo}
      onChange={(event)=>{
        setRegNo(event.target.value);
      }}/>
    
      <Form.Group controlId="vred">
      <Form.Label>Vehicle Registratration Expiration Date:  </Form.Label> 
      <DatePicker selected={regExpDate} onChange={(date) => setRegExpDate(date)} /><br/>
      </Form.Group>

      <Form.Label>Vehicle Insurance Policy Number</Form.Label>
      <Form.Control className='textbox'  type="text" placeholder="ABC123456789" value={insNo}
      onChange={(event)=>{
        setInsNo(event.target.value);
      }}/>


      <Form.Group controlId="vied">
      <Form.Label>Vehicle Insurance Expiration Date:  </Form.Label> 
      <DatePicker selected={insExpDate} onChange={(date) => setInsExpDate(date)} />
      </Form.Group>

      <Button variant="primary" onClick={addVehicle}>Save</Button> 
      </Form>


    </div>

  )
}

export default AddVehicle;
