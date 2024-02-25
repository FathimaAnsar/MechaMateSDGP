import React from 'react'
import Header from "../components/Header";
import Button from 'react-bootstrap/Button';


function ServiceRecords() {
  return (
    <div>
        <Header/>
        <h1>Service Records</h1>
        <Button variant="primary" href='/addServiceRecord'>Add Service Records</Button>{}

      
    </div>
  )
}

export default ServiceRecords
