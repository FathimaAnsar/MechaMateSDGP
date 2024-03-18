import React from "react";
import Card from "react-bootstrap/Card";
import defaultImg from "../../images/p3.avif";
import sosImage from "../../images/dashboard/sos.png";
import breakdownImage from "../../images/dashboard/breakdown.png";
import manageDocumentsImage from "../../images/dashboard/manageDocuments.png";
import trackMyVehicleImage from "../../images/dashboard/location.png";
import autoMobSearchImage from "../../images/dashboard/autoMob.png";
import parkingfinderImage from "../../images/dashboard/parkingFinder.png";

import { Link } from "react-router-dom";

function DashboardCard({ content, onClick }) {
  const { title, path } = content;

  const optionImage = {
    "Parking Finder": parkingfinderImage,
    "Manage Documents": manageDocumentsImage,
    "Track My Vehicle": trackMyVehicleImage,
    "AutoMob Search": autoMobSearchImage,
    "Breakdown Assistance": breakdownImage,
    "Accident Assistance": sosImage,
    default: sosImage,
  };
  const imageUrl = optionImage[title] || optionImage["default"];

  return (
    <Link to={path} style={{ textDecoration: "none" }}>
      <Card className="text-center" shadow style={{ height: "220px" }}>
        <div>
          <Card.Img
            variant="top"
            src={imageUrl}
            style={{ maxWidth: "150px" }}
          />
        </div>

        <Card.Body>
          <Card.Title style={{ fontSize: "16px" }}>{title}</Card.Title>
        </Card.Body>
      </Card>
    </Link>
  );
}

export default DashboardCard;
