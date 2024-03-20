import React, { useEffect } from "react";
import {
  Button,
  Card,
} from "react-bootstrap";
import logo from "../images/logo-white.png";
import { Pages } from "../Pages.js";
import { useNavigate } from "react-router-dom";

function GetStarted() {
  const navigate = useNavigate();

  const handleClick = (page) => {
    navigate("/" + page);
  };

  // const handleClick222 = () => { props.app.changePage(props.app.currentPage); } //test

  useEffect((e) => {
    //  props.lang.loadLanguage(true);
  });

  return (
    <>
      <Card
        style={{
          height: "100vh",
          backgroundColor: "#085bd4",
          borderRadius: "0",
          margin: "0",
        }}
      >
        <Card.Body className="d-flex  flex-column justify-content-center align-items-center">
          <Card.Img
            variant="top"
            src={logo}
            style={{ width: "300px", marginBottom: "15px" }}
          />
          <Card.Text
            className="text-center"
            style={{
              fontSize: "18px",
              color: "white",
              maxWidth: "350px",
              fontWeight: "200",
              letterSpacing: "2px",
            }}
          >
            Empowering Drivers with Smart Maintenance Insights
          </Card.Text>
        </Card.Body>
        <Card.Footer
          className="d-flex  justify-content-center"
          style={{
            width: "90vw",
            borderTop: "0",
            backgroundColor: "transparent",
            marginLeft: "5vw",
          }}
        >
          <Button
            id="get-started-button"
            style={{
              backgroundColor: "white",
              width: "300px",
              color: "#085bd4",
              fontSize: "12pt",
              marginBottom: "25px",
              fontWeight: "600",
              borderRadius: "30px",
              padding: "8px",
            }}
            onClick={() => handleClick(Pages.SignInUI)}
          >
            Let's Get Started
          </Button>
        </Card.Footer>
      </Card>
    </>
  );
}

export default GetStarted;
