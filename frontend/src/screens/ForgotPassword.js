import React, { useState } from "react";
import { Pages } from "../Pages.js"
import ConnectionManager from "../services/ConnectionManager.js"


function ForgotPassword(props) {

    const handleGoBack = () => {props.app.goBack();}
    const handleSubmit = async (e) => {
        e.preventDefault();

        const email = document.getElementById('email').value;

        // Validate inputs
        // If failed to validate, return here

        let connection = new ConnectionManager();

        const resp = await connection.requestPasswordRecovery(email);
        const response = JSON.parse(resp);

        if(response.error) {
            alert("Error occured: " + response.message + "\n" + response.help);
        }else if(response.status) {
            alert("Success: " + response.message + "\n" + response.info);
            props.app.changePage(Pages.DashboardUI);
        } else {
            alert("Error: Unknown");
        }
    }
    return(
        <>
            <button onClick={handleGoBack}>Go Back</button>

            <h1>Password Recovery</h1>
            <p>Please enter your email address </p>
            <form action="#" method="post">
                <label htmlFor="email">Email:</label><br></br>
                <input type="text" id="email" name="email"/><br></br>
                <br></br>
                <button onClick={handleSubmit}>Request Password Reset Link</button>
            </form>


        </>
    );


}



export default ForgotPassword;