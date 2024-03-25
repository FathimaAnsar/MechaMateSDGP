

import Header from "./components/Header";
import React from 'react';
import QRCode from 'qrcode.react'; 
import { useLocation } from "react-router-dom";
import './styles/QrPage.css'; 

function QrPage(props) {
  const { state } = useLocation();

  return (
    <>
    <Header app ={props.app}/>
    <div className="qr-page-container">
      <div className="qr-content">
        <h2 className="qr-topic">Scan to Open and Add Service Details</h2>
        <p className="qr-instruction">Scan this QR code to open the service record form.</p>
      </div>
      <div className="qr-container shadow">
        <QRCode value={state.qrurl} size={200} />
      </div>
    </div>
    </>
  );
}

export default QrPage;





























// // import React from 'react';

// // import QRCode from 'react-qr-code';

// // function AddServiceRecordByQR(props) {
// //     return (
// //         <div className="add-record-container">
// //         <div className="add-manually-button">
// //           <Button variant="primary" onClick={() => window.open(url, '_blank')}>Add manually On this device</Button>
// //         </div>
// //         <h6>or</h6>
// //         <h1>Scan QR Code</h1>
// //         <h6>Scan this QRCode to Open Service Record form</h6>
// //         <div className="qr-code-section">
  
// //           <div className="qr-code-wrapper">
// //             <QRCode value={url} />
// //           </div>
// //         </div>
// //       </div>
// //     );
// // }

// // export default AddServiceRecordByQR;

// import React, { useEffect, useRef } from 'react';
// import QRCode from 'qrcode.react';
// import { Pages } from "../Pages";

// function QrUi({ url }) {
//   const qrCodeRef = useRef(null);

//   useEffect(() => {
//     if (qrCodeRef.current) {
//       const canvas = qrCodeRef.current.querySelector('canvas');
//       if (canvas) {
//         const context = canvas.getContext('2d');
//         if (context) {
//           context.clearRect(0, 0, canvas.width, canvas.height);
//         }
//       }

//       new QRCode(qrCodeRef.current, {
//         text: url,
//         width: 128,
//         height: 128,
//         colorDark: '#000',
//         colorLight: '#fff',
//         correctLevel: QRCode.CorrectLevel.H
//       });
//     }
//   }, [url]);

//   return <div ref={qrCodeRef}></div>;
// }

// export default QrUi;
