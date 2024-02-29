import React from 'react';
import QRCode from 'react-qr-code';

function AddServiceRecordByQR({ url }) {
    return (
        <div className="add-record-containerwithQR">
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
