import React from 'react';
import Card from 'react-bootstrap/Card';

function ClickableCard({ content, onClick }) {
  const { color, title, description } = content;
  const textColor = color.toLowerCase() === 'light' ? 'dark' : 'white';

  const handleClick = () => {
    onClick(content); // Pass the card content to the onClick handler
  };

  return (
    <Card
      bg={color.toLowerCase()}
      text={textColor}
      style={{ width: '16rem', cursor: 'pointer', height: '8rem' }}
      className="mb-2"
      onClick={handleClick}
    >
      <Card.Header><Card.Title>{title}</Card.Title></Card.Header>
      <Card.Body>
        
        <Card.Text>
          {description}
        </Card.Text>
      </Card.Body>
    </Card>
  );
}

export default ClickableCard;
