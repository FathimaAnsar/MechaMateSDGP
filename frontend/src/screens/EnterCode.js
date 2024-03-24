import React, { useState, useEffect, useRef } from "react";
import ConnectionManager from "../services/ConnectionManager.js";
import { Pages } from "../Pages.js";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import logo from "../images/logo-black.png";
import Modal from "react-bootstrap/Modal";

import { Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { Container } from "react-bootstrap";
import CustomAlert from "./components/CustomAlert.js";
import LoadingScreen from "./components/LoadingScreen.js";



function EnterCode(props) {
  const connection = new ConnectionManager();
  const [buttonLoading, setButtonLoading] = useState(false);

  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const [show, setShow] = useState(false);
  const [mainModelShow, setMainModelShow] = useState(true)

  const [showAlert, setShowAlert] = useState(false);
  const [error, setError] = useState({ message: "", heading: "" });

  const handleModalClose = () => {
    setShow(false);
    navigate("/" + Pages.DashboardUI);
  };
  const handleClose = () => {
    setShowAlert(false);
  };

  useEffect(() => {
    try {
      let connection = new ConnectionManager();
      connection.getUserProfile().then((uProf) => {
        const userProfile = JSON.parse(uProf);

        if (!userProfile) {
          alert("Please check your springboot localhost is running");
          return;
        }

        if (userProfile.error) {
          if (userProfile.error === "ErrorPendingActivation") {

          } else if (userProfile.error === "ErrorNotSignedIn") {
            navigate("/" + Pages.SignInUI);
          } else {
            alert(userProfile.message + "\n" + userProfile.help);
          }
        } else {
          navigate("/" + Pages.DashboardUI);
        }
      });
    } catch (exp) { }
  }, []);

  const onOtpSubmit = async (otp) => {
    setLoading(true);
    // Call the activation logic here using the OTP
    try {
      let connection = new ConnectionManager();
      const resp = await connection.activate(otp);
      const response = JSON.parse(resp);

      if (!response) {
        alert("Please check your springboot localhost is running");
        return;
      }

      if (response.error) {
        setError({
          message: response.help,
          heading: response.message,
        });
        setShowAlert(true);
      } else if (response.status) {
        setMainModelShow(false);
        setShow(true);
        // alert("Success: " + response.message + "\n" + response.info);
        const uProf = await connection.getUserProfile();
        const userProfile = JSON.parse(uProf);

        if (!userProfile) {
          alert("Please check your springboot localhost is running");
          return;
        }

        if (userProfile.error) {
          alert(userProfile.message + "\n" + userProfile.help);
          navigate("/" + Pages.SignInUI);
        } else {
          props.app.setUserProfile(userProfile);
        }
      } else {
        alert("Error: Unknown");
      }
    } catch (error) {
      console.error("Error:", error);
    }
    setLoading(false);
  };

  const handleSignout = () => {
    setButtonLoading(true);
    props.app.clearSessionCache();
    const connection = new ConnectionManager();
    connection.signout().then((resp) => {
      const response = JSON.parse(resp);

      if (!response) {
        alert("Please check your springboot localhost is running");
        navigate("/" + Pages.SignInUI);
        return;
      }

      if (response.error) {
        // navigate("/" + Pages.SignInUI);
      } else if (response.status) {
        navigate("/" + Pages.SignInUI);
      } else {
        alert("Error: Unknown");
      }
    });
  };

  return (
    <>
      {loading && <LoadingScreen />}
      <Container fluid>
        <Modal
          size="md"
          show={mainModelShow}
          centered
          className="text-center"
          style={{
            variant: "light",
          }}
        >
          <Modal.Header className="f-column justify-content-center ">
            <img
              src={logo}
              style={{
                width: "150px",
              }}
              alt="Logo"
            />
          </Modal.Header>

          <Card.Body
            className="f-column justify-content-center align-items-center "
            style={{ height: "100%" }}
          >
            <Card.Title style={{ marginTop: "20px" }}>
              <h3>Activate your account</h3>
            </Card.Title>
            <Card.Text>
              Please enter the 6-digit OTP we sent to your email
            </Card.Text>
            <OtpInput length={6} onOtpSubmit={onOtpSubmit} />

            <Button id="resend-otp-btn" variant="dark">
              Resend OTP
            </Button>{"        "}
            <Button id="resend-otp-btn" variant="warning" onClick={handleSignout}
              style={{ width: '100px' }}>
              {buttonLoading ? (
                <Spinner
                  as="span"
                  animation="border"
                  size="sm"
                  role="status"
                  aria-hidden="true"
                />
              ) : (
                <>Sign Out</>
              )}
            </Button>
          </Card.Body>
          <Card.Footer
            className="text-muted"
            style={{
              borderTop: "100px",
              backgroundColor: "transparent",
            }}
          >
            <div id="entercode-copyright">MechaMate © 2024</div>
          </Card.Footer>
        </Modal>

        <CustomAlert show={show} handleClose={handleModalClose} error={{
          heading: "Account activated",
          message: "Welcome to MechaMate! Now you can use MechaMate features",
        }} variant="success" />


        <CustomAlert show={showAlert} handleClose={handleClose} error={error} />
      </Container>
    </>
  );
}

const OtpInput = ({ length = 6, onOtpSubmit = () => { } }) => {
  const [otp, setOtp] = useState(new Array(length).fill(""));
  const inputRefs = useRef([]);

  useEffect(() => {
    if (inputRefs.current[0]) {
      inputRefs.current[0].focus();
    }
  }, []);

  const handleChange = (index, e) => {
    const value = e.target.value;
    if (isNaN(value)) return;

    const newOtp = [...otp];
    newOtp[index] = value.substring(value.length - 1);
    setOtp(newOtp);

    const combinedOtp = newOtp.join("");
    if (combinedOtp.length === length) onOtpSubmit(combinedOtp);

    if (value && index < length - 1 && inputRefs.current[index + 1]) {
      inputRefs.current[index + 1].focus();
    }
  };

  const handleClick = (index) => {
    inputRefs.current[index].setSelectionRange(1, 1);
    if (index > 0 && !otp[index - 1]) {
      inputRefs.current[otp.indexOf("")].focus();
    }
  };

  const handleKeyDown = (index, e) => {
    if (
      e.key === "Backspace" &&
      !otp[index] &&
      index > 0 &&
      inputRefs.current[index - 1]
    ) {
      inputRefs.current[index - 1].focus();
    }
  };

  return (
    <div>
      {otp.map((value, index) => (
        <input
          key={index}
          type="text"
          placeholder={"#"}
          ref={(input) => (inputRefs.current[index] = input)}
          value={value}
          onChange={(e) => handleChange(index, e)}
          onClick={() => handleClick(index)}
          onKeyDown={(e) => handleKeyDown(index, e)}
          className="otpInput"
        />
      ))}
    </div>
  );
};

export default EnterCode;
