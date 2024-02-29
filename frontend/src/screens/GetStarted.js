
import React, { useState } from "react";
import { Pages } from "../Pages.js" 


function GetStarted(props) {

    const handleClick = (page) => {props.app.changePage(page);} 

    return(
        <>
        <h2>Welcome to Mechamate</h2>
        <button onClick={() => handleClick(Pages.SignInUI)}>Get Started</button><br></br>
        <button onClick={() => handleClick(Pages.DashboardUI)}>Dashboard</button><br></br>
        <button onClick={() => handleClick(Pages.SignUpUI)}>Sign Up</button><br></br>
        <button onClick={() => handleClick(Pages.ForgotPasswordUI)}>Forgot Password</button><br></br>

        <button onClick={() => handleClick(Pages.ForgotPasswordUI)}>Forgot Password</button><br></br>
        <button onClick={() => handleClick(Pages.ForgotPasswordUI)}>Forgot Password</button><br></br>

        <button onClick={() => handleClick(Pages.AddSRecordQRUI)}>Add using QR</button><br></br>

        </>
    );
    

}

export default GetStarted;