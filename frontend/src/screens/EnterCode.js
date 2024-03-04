import React, { useState, useEffect, useRef } from "react";
import ConnectionManager from "../services/ConnectionManager.js";
import { Pages } from "../Pages.js";
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import logo from '../images/logo-black.png';
import Modal from 'react-bootstrap/Modal';


function EnterCode(props) {
    const [show, setShow] = useState(false);
    const handleClose = () => {
        setShow(false);

        props.app.changePage(Pages.DashboardUI);
    };

    const handleShow = () => setShow(true);
    const handleGoBack = () => props.app.goBack();

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
                        //
                    } else if (userProfile.error === "ErrorNotSignedIn") {
                        props.app.changePage(Pages.SignInUI);
                    } else {
                        alert(userProfile.message + "\n" + userProfile.help);
                    }
                } else {
                    props.app.changePage(Pages.DashboardUI);
                }
            });
        } catch (exp) { }
    }, []);

    const onOtpSubmit = async (otp) => {
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

                alert("Error occurred: " + response.message + "\n" + response.help);
            } else if (response.status) {
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
                    props.app.changePage(Pages.SignInUI);
                } else {
                    props.app.setUserProfile(userProfile);
                }
            } else {
                alert("Error: Unknown");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <>
            <Card className="text-center" style={{ height: '100vh' }}>
                <Card.Header className="d-flex justify-content-between align-items-center">
                    <div>
                        <Button variant='primary' onClick={handleGoBack} className="btn btn-primary">Back</Button>

                    </div>
                    <div className="text-center">
                        <img src={logo} style={{ width: '150px' }} alt="Logo" />
                    </div>
                </Card.Header>
                <Card.Body className="f-column justify-content-center align-items-center " style={{marginTop: '35px'}}>

                    <Card.Title><h3>Activate your account</h3></Card.Title>
                    <Card.Text>
                        Please enter the 6-digitOTP we sent to your email</Card.Text>
                    <OtpInput length={6} onOtpSubmit={onOtpSubmit} />

                </Card.Body>
                <Card.Footer className="text-muted" style={{ width: '100vw', marginBottom: '5px', borderTop: '0', backgroundColor: 'transparent' }}>
                    <div className='copyright'>MechaMate © 2024</div></Card.Footer>
            </Card>
            <Modal show={show} onHide={handleClose} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Account activated</Modal.Title>
                </Modal.Header>
                <Modal.Body>Welcome to MechaMate! Now you can use MechaMate features</Modal.Body>
                <Modal.Footer>

                    <Button variant="primary" onClick={handleClose}>
                        Continue
                    </Button>
                </Modal.Footer>
            </Modal>
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
        if (e.key === "Backspace" && !otp[index] && index > 0 && inputRefs.current[index - 1]) {
            inputRefs.current[index - 1].focus();
        }
    };

    return (
        <div>
            {otp.map((value, index) => (
                <input
                    key={index}
                    type="text"
                    placeholder={'#'}
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
