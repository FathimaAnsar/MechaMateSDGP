import React from 'react';
import Header from "./components/Header";

function ManageDocuments(props) {
  return (
    <div>
      <Header app ={props.app}/>
      <h2>Manage Documents</h2>
      {/* Button to add service record manually */}
      <button onClick={() => props.app.changePage('AddServiceRecordByQR')}>Add Service Record Manually</button>
      {/* Button to add service record with a QR code */}
      <button onClick={() => props.app.changePage('AddServiceRecordByServiceProvider')}>Add Service Record by QR Code</button>
    </div>
  );
}

export default ManageDocuments;

