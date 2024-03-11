import React from 'react';
import Card from 'react-bootstrap/Card';
import camryImage from '../../images/vehicles/camry.jpg';
import vitzImage from '../../images/vehicles/vitz.jpg';
import civicImage from '../../images/vehicles/civic.jpg'
import defaultImage from '../../images/vehicles/default.jpg'
import { Button } from 'react-bootstrap';

function ClickableCard({ content, onClick }) {
  const { registrationNumber, vehicleMake, vehicleModel } = content;

  const textColor = 'dark';

  const carImages = {
    'Camry': camryImage, // Assuming you've imported the image statically
    'Civic': civicImage,
    'Vitz': vitzImage,
    'default' : defaultImage
  };
  const imageUrl = carImages[vehicleModel] || carImages['default'];

  const handleClick = () => {
    onClick(content);
  };

  return (
    <Card
      bg='light'
      text={textColor}
      style={{
        width: '16rem',
        cursor: 'pointer',
        height: 'auto',
        overflow: 'hidden',
        boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)',
        outline: '0',
        border: 'none',
        borderRadius: '15px'
      }}

      className="text-center"
      onClick={handleClick}
    >

      <Card.Img variant="top" src={imageUrl} style={{ maxWidth: '100%'}}/>
      <Card.Body>
        <Card.Title>{vehicleMake} {vehicleModel}</Card.Title>
        <Card.Text>
        {registrationNumber}
        </Card.Text>
      </Card.Body>
      <Card.Footer>
        <Button variant='dark' style={{width:'100%'}}>View vehicle</Button>
      </Card.Footer>
    </Card>

  );
}

export default ClickableCard;



{/* <Card>
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
        <img
          src={imageUrl}
          alt={`${title} Image`}
          style={{ maxWidth: '100%', opacity: '0.5' }}
        />
      </div>
      <Card.ImgOverlay>

        <Card.Body>
          <Card.Title>{description} {title}</Card.Title>
          <Card.Text>
            {regNo}
          </Card.Text>


        </Card.Body>

        {/* 
        <Card.Body>
        <Card.Title >
        {description} {title}
          </Card.Title>
          <span style={{ fontSize: '10pt' }}>{regNo}</span>
          <Card.Text>
            
          </Card.Text>
        </Card.Body> */}
    //   </Card.ImgOverlay>
    // </Card > */}