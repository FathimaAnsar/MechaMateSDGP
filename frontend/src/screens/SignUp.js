import React, { useState } from "react";
import { Pages } from "../Pages.js" 
import ConnectionManager from "../services/ConnectionManager.js"



function SignUp(props) {

    const handleGoBack = () => {props.app.goBack();} 
    let connection = new ConnectionManager();

    const handleClick = () => {
        props.app.changePage(Pages.SignInUI); 
    } 

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        const firstName = document.getElementById('firstname').value;
        const lastName = document.getElementById('lastname').value;
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirm-password').value;
        const email = document.getElementById('email').value;
        const telephone = document.getElementById('telephone').value;
        const agreedTOS = document.getElementById('agreed-tos').checked;
    
        // Validation should be done here
        
        if (password !== confirmPassword) {
            alert("Passwords do not match");
            return;
        }

        try {
            const resp = await connection.signup(username, password, email, firstName, lastName, telephone, agreedTOS);
            const response = JSON.parse(resp);
           if(response.error) {
                alert("Error occured: " + response.message + "\n" + response.help);
           } else if(response.status) {
                if(!props.app.isFirstRunDone()) props.app.setFirstRunDone(true);
                alert("Success: " + response.message + "\n" + response.info);
                props.app.changePage(Pages.SignInUI);
            } else {
                alert("Error: Unknown");
            }
        } catch (error) {
            console.error("Signup failed:", error);
        }
    };
    

    return(
        <>
    <button onClick={handleGoBack}>Go Back</button>

    <h2>Signup Form</h2>
    <form>
        <label htmlFor="firstname">First Name:</label><br></br>
        <input type="text" id="firstname" name="firstname" required/><br></br>

        <label htmlFor="lastname">Last Name:</label><br></br>
        <input type="text" id="lastname" name="lastname" required/><br></br>

        <label htmlFor="username">Username:</label><br></br>
        <input type="text" id="username" name="username" required/><br></br>

        <label htmlFor="password">Password:</label><br></br>
        <input type="password" id="password" name="password" required/><br></br>

        <label htmlFor="confirm-password">Confirm password:</label><br></br>
        <input type="password" id="confirm-password" name="confirm-password" required/><br></br>

        <label htmlFor="email">Email:</label><br></br>
        <input type="email" id="email" name="email" required/><br></br>

        <label htmlFor="telephone">Telephone:</label><br></br>
        <input type="tel" id="telephone" name="telephone"/><br></br>

        <input type="checkbox" id="agreed-tos" name="agreed-tos" value="yes"/>
        <label htmlFor="agreed-tos">Agree to TOS</label><br></br>

        <input type="submit" onClick={handleSubmit} value="Sign Up"/>
    </form>
    <br></br>
 
        </>
    );
    

}

export default SignUp;