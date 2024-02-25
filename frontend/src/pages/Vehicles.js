import React from 'react'
import Button from 'react-bootstrap/Button';
import Header from "../components/Header";


function Vehicles() {
  return (
    <div>
        <Header/>
        <h1>Vehicles</h1>

        <Button variant="primary" href='/addVehicle'>Add Vehicle</Button>{}
      
    </div>
  )
}

export default Vehicles
