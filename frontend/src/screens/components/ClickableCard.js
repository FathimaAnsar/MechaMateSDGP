import React from "react";
import Card from "react-bootstrap/Card";
import camryImage from "../../images/vehicles/camry-side.jpg";
import vitzImage from "../../images/vehicles/vitz.jpg";
import civicImage from "../../images/vehicles/civic-side.jpg";
import defaultImage from "../../images/vehicles/default.jpg";
import corollaImage from "../../images/vehicles/corolla-side.jpg";
import { Button } from "react-bootstrap";
import "../styles/viewVehicle.css";
import { FaAngleRight } from "react-icons/fa";

function ClickableCard({ content, onClick, index }) {
  const { registrationNumber, vehicleMake, vehicleModel } = content;

  const textColor = "dark";

  const carImages = {
    Camry: camryImage,
    Civic: civicImage,
    Vitz: vitzImage,
    Corolla: corollaImage,
    default: defaultImage,
  };
  const imageUrl = carImages[vehicleModel] || carImages["default"];

  const handleClick = () => {
    onClick(index);
  };

  return (
    <Card
      className="d-flex flex-row align-items-center"
      style={{ height: "120px", overflow: "hidden" }}
    >
      <Card.Img
        variant="left"
        src={imageUrl}
        style={{ maxWidth: "50%", height: "auto", objectFit: "contain" }}
      />
      <Card.Body className="d-flex flex-column justify-content-between">
        <div>
          <Card.Title>
            {vehicleMake} {vehicleModel}
          </Card.Title>
          <Card.Text>{registrationNumber}</Card.Text>
        </div>
        <FaAngleRight
          className="align-self-end"
          style={{ fontSize: "24px", cursor: "pointer" }}
          onClick={handleClick}
        />
      </Card.Body>
    </Card>
  );
}

export default ClickableCard;
