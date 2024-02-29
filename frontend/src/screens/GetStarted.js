
import React, { useState } from "react";
import { Pages } from "../Pages.js"
import { ButtonGroup, Button } from 'react-bootstrap';


function GetStarted(props) {

    const handleClick = (page) => { props.app.changePage(page); }

    return (
        <>
            <h2>Welcome to Mechamate</h2>
            <ButtonGroup vertical>
                <Button variant="primary" onClick={() => handleClick(Pages.SignInUI)}>Get Started</Button>
                <Button variant="primary" onClick={() => handleClick(Pages.SignUpUI)}>Sign Up</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.ForgotPasswordUI)}>Forgot Password</Button>
                <Button variant="primary" onClick={() => handleClick(Pages.ResetPasswordUI)}>Reset Password</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.DashboardUI)}>Dashboard</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.SettingsUI)}>Settings</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.NotificationsUI)}>Notifications</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.AboutUsUI)}>About Us</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.MyVehiclesUI)}>My Vehicles</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.PredictMaintenanceUI)}>Predict Maintenance</Button>
                <Button variant="primary" onClick={() => handleClick(Pages.ShowPredictionsUI)}>Show Predictions</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.TrackVehicleUI)}>Track Vehicle</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.AutoMobSearchUI)}>AutoMob Search</Button>
                <Button variant="primary" onClick={() => handleClick(Pages.AutoMobDetailsUI)}>AutoMob Details</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.EmergencyAssistUI)}>Emergency Assistance</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.ManageDocumentsUI)}>Manage Documents</Button>
                <Button variant="primary" onClick={() => handleClick(Pages.AddSRecordManualUI)}>Add Service Record Manually</Button>
                <Button variant="primary" onClick={() => handleClick(Pages.AddSRecordQRUI)}>Add Service Record by QR</Button>
                <Button variant="primary" onClick={() => handleClick(Pages.AddRecordBySPUI)}>Add Service Record by Service Provider</Button>
                <Button variant="primary" onClick={() => handleClick(Pages.ShowRecordReqUI)}>Show Service Record Request</Button>
                <br></br><br></br>
                <Button variant="primary" onClick={() => handleClick(Pages.ParkingFinderUI)}>Parking Finder</Button>
                <Button variant="primary" onClick={() => handleClick(Pages.ParkingInfoUI)}>Parking Info</Button>
            </ButtonGroup >
        </>
    );


}

export default GetStarted;