import React from 'react';

import QRCode from 'react-qr-code';

function AddServiceRecordByQR(props) {
    return (
        <div className="add-record-container">
        <div className="add-manually-button">
          <Button variant="primary" onClick={() => window.open(url, '_blank')}>Add manually On this device</Button>
        </div>
        <h6>or</h6>
        <h1>Scan QR Code</h1>
        <h6>Scan this QRCode to Open Service Record form</h6>
        <div className="qr-code-section">
  
          <div className="qr-code-wrapper">
            <QRCode value={url} />
          </div>
        </div>
      </div>
    );
}

export default AddServiceRecordByQR;

