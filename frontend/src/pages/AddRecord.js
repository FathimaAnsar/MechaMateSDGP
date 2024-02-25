import React from 'react';
import QRCode from 'react-qr-code';
import Button from 'react-bootstrap/Button';


function AddRecord({ url }) {
    return (
        
            
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', height: '100vh' }}>
            <div>
            
            <Button variant="primary" onClick={() => window.open(url, '_blank')}>Add manually</Button>

            </div>

            <div>
                <h1>Scan QR Code</h1>
                <QRCode value={ url } />
            </div>
        </div>
    );

}

export default AddRecord;




/*import React, { useRef, useEffect } from 'react';
import QRCode from 'qrcode.react';

function QRCodeGenerator({ url }) 
	const [url, setUrl] = useState('')
	const [qr, setQr] = useState('')

	const GenerateQRCode = () => {
		QRCode.toDataURL(url, {
			width: 800,
			margin: 2,
			color: {
				dark: '#335383FF',
				light: '#EEEEEEFF'
			}
		}, (err, url) => {
			if (err) return console.error(err)

			console.log(url)
			setQr(url)
		})
	}
export default QRCodeGenerator;*/
/*
import React, { useRef, useEffect } from 'react';
import QRCode from 'qrcode';

function QrCodeGenerator({ url }) {
  const qrCodeRef = useRef(null);

  useEffect(() => {
    if (qrCodeRef.current) {
      QRCode.toCanvas(qrCodeRef.current, url, { width: 128 }, function (error) {
        if (error) console.error(error);
        console.log('QR Code generated!');
      });
    }
  }, [url]);

  return <canvas ref={qrCodeRef}></canvas>;
}
export default QrCodeGenerator;
*/