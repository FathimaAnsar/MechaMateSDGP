import React from 'react';
import Header from "./components/Header";
import { Pages } from "../Pages";
import ConnectionManager from '../services/ConnectionManager';

function ManageDocuments(props) {

 
  const connectionManager = new ConnectionManager(); 

  const handleQRButtonClick = async () => {
    try {
      // Send request to the server to get the QR URL
      const qrUrl = await connectionManager.getRequestForQR(); // Define getRequestForQR method in ConnectionManager class
      // Navigate to QR UI page and pass the QR URL as a parameter
      props.app.changePage(Pages.QrUI, { qrUrl });
    } catch (error) {
      console.error("Error fetching QR URL:", error);
    }
  };

  return (
    <div>
      <Header app ={props.app}/>
      <h2>Manage Documents</h2>
      {/* Button to add service record manually */}
      <button onClick={() =>{props.app.changePage(Pages.AddSRecordManualUI)}}>Add Service Record Manually</button>
      {/* Button to add service record with a QR code */}
      <button onClick={() => props.app.changePage(Pages.QrUI)}>Add Service Record by QR Code</button>
    </div>
  );
}

export default ManageDocuments;

