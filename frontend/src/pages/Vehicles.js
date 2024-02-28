import React, { useState } from 'react'
import Button from 'react-bootstrap/Button';
import Header from "../components/Header";
import { renderTableRows } from './AddVehicle'; // Import the renderTableRows function from AddVehicle.js



function Vehicles() {
// edit vehicle 

// async function editVehicles(vehicle)
// {
//  setRegNo(vehicles.regNo);
//  setVehicleType(vehicles.vehicleType);
//  setRegNo(vehicles.fuelType); 
//  setId(vehicles._id);
// }

// const [_id, setId] =useState(null);
// const [vehicleType, setVehicleType] = useState('Car');
// const [fuelType, setFuelType] = useState(null);
// const [vehicleMake, setVehicleMake] = useState(null);
// const [vehicleModel, setVehicleModel] = useState(null);
// const [regNo, setRegNo] = useState(null);
// const [regExpDate, setRegExpDate] = useState(new Date());
// const [insNo, setInsNo] = useState(null);
// const [insExpDate, setInsExpDate] = useState(new Date());
// const [vehicles, setVehicles ] =useState([]);


  return (
    <div>
        <Header/>
        <h1>Vehicles</h1>

      {/* <table>
        <tbody>
          {renderTableRows()} {/* Render the table rows using the imported function */}
        {/* </tbody>
      </table> */} 

        <Button variant="primary" href='/addVehicle'>Add Vehicle</Button>{}
        <Button variant="primary" href='/addVehicle'>Edit Vehicle</Button>{}
        <Button variant="danger" href='/addVehicle'>Delete Vehicle</Button>{}
        
    </div>
  )
}

export default Vehicles
