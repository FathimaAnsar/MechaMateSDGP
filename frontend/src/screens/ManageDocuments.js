import React from 'react';
import Header from "./components/Header";
import { Pages } from "../Pages";
import ConnectionManager from '../services/ConnectionManager';
import { useNavigate } from "react-router-dom";

function ManageDocuments(props) {

  const navigate = useNavigate();

 
  

  const handleQRButtonClick = async () => {

    let connection = new ConnectionManager();

  const resp =  connection.getRequestForQR( );
  const response = JSON.parse(resp);

  if (!response) {
    
    alert("Please check your springboot localhost is running");
    return;
  }

  if (response.error) {
    
    
  } else if (response.url) {
    
    // Navigate to QR UI page and pass the QR URL as a parameter
    navigate("/" + Pages.QrUI,{state:{qrurl:response.url}});

    
  } else {
    alert("Error: Unknown");
  }
};
   

  return (
    <div>
      <Header app ={props.app}/>
      <h2>Manage Documents</h2>
      {/* Button to add service record manually */}
      <button onClick={() =>{props.app.changePage(Pages.AddSRecordManualUI)}}>Add Service Record Manually</button>
      {/* Button to add service record with a QR code */}
      <button onClick={handleQRButtonClick}>Add Service Record by QR Code</button>
    </div>
  );

  }
export default ManageDocuments;
  
