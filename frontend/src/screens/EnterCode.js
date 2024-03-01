import React, { useState } from "react";
import { Pages } from "../Pages.js"
import ConnectionManager from "../services/ConnectionManager.js"


function EnterCode(props) {

    const handleGoBack = () => {props.app.goBack();}
    const handleSubmit = async (e) => {
        e.preventDefault();

        const code = document.getElementById('code').value;

        // Validate inputs
        // If failed to validate, return here

        let connection = new ConnectionManager();

        const resp = await connection.activate(code);
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

            <h1>Account Activation</h1>
            <p>Please enter your the 6 digits activation code we sent to your email</p>
            <form action="#" method="post">
                <label htmlFor="code">Activation code:</label><br></br>
                <input type="code" id="code" name="code"/><br></br>
                <br></br>
                <button onClick={handleSubmit}>Activate</button>
            </form>


        </>
    );


}

export default EnterCode;