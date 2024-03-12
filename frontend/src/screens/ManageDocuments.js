import React from 'react';
import Header from "./components/Header";
import { Pages } from "../Pages";

function ManageDocuments(props) {
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

