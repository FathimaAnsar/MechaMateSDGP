
import React, { useState } from "react";
import { Pages } from "../Pages.js" 
import ConnectionManager from "../services/ConnectionManager.js"


function SignIn(props) {

    const handleGoBack = () => {props.app.goBack();} 
    
    const handleSignUp = () => {
        props.app.changePage(Pages.SignUpUI); 
    }

    const handleForgotPass = () => {
        props.app.changePage(Pages.ForgotPasswordUI); 
    }

    const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Extract username and password from form fields
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const keepMeSignedIn = document.getElementById('keep-me-signed-in').checked;

    // Call the signin method with extracted credentials
    let connection = new ConnectionManager();
    const signInResponse = await connection.signin(username, password, keepMeSignedIn);

    // Handle the sign-in response
    if (signInResponse.ok) {
        // Sign-in successful, get profile
        const profileResponse = await connection.getProfile();
        console.log("Profile:", profileResponse);
    } else {
        // Sign-in failed, handle error
        console.error("Sign-in failed");
        // You can display an error message to the user or handle it as needed
    }
}
        
    return(
        <>
    <h1>Sign In</h1>
    
    <form action="#" method="post">
        <label htmlFor="username">Username:</label><br></br>
        <input type="text" id="username" name="username"/><br></br>
        
        <label htmlFor="password">Password:</label><br></br>
        <input type="password" id="password" name="password"/><br></br>
        
        <input type="checkbox" id="keep-me-signed-in" name="already_signed_in"/>
        <label htmlFor="keep-me-signed-in">Keep Me Signed In</label><br></br>
        
        <button onClick={handleSubmit}>SignIn</button>        
    </form>
    <br></br>

    <button onClick={handleSignUp}>Dont have an account</button>
    <br></br>
    <button onClick={handleForgotPass}>Forgot Password</button>
    <br></br>
    <button onClick={handleGoBack}>Go Back</button>
    

        </>
    );
    

}



export default SignIn;