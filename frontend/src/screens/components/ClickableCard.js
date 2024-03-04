import React from 'react';
import Card from 'react-bootstrap/Card';
import camryImage from '../../images/vehicles/camry-2022.jpg';
import vitzImage from '../../images/vehicles/vitz-2016.jpeg';
import civicImage from '../../images/vehicles/civic-2019.jpg'

function ClickableCard({ content, onClick }) {
  const { title, regNo, description } = content;
  const textColor =  'white';

  const carImages = {
    'Camry': camryImage, // Assuming you've imported the image statically
    'Civic': civicImage,
    'Vitz': vitzImage,
  };
  const imageUrl = carImages[title] || 'default_image_url.jpg';

  const handleClick = () => {
    onClick(content);
  };

  return (
    <Card
      bg='dark'
      text={textColor}
      style={{
        width: '16rem',
        cursor: 'pointer',
        height: '8rem',
        overflow: 'hidden',
        boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)',
        outline: '0',
        border: 'none',
      }}

      className="text-center"
      onClick={handleClick}
    >
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
      </Card.ImgOverlay>
    </Card>
  );
}

export default ClickableCard;
