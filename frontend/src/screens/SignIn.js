
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

        // props.app.changePage(Pages.DashboardUI); 
        
        e.preventDefault();
        
        // Extract username and password from form fields
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const keepMeSignedIn = document.getElementById('keep-me-signed-in').checked;

        let connection = new ConnectionManager();
        const signInResponse = await connection.signin(username, password, keepMeSignedIn);

        

        const userProfile = await connection.getProfile();

        console.log(userProfile);
        props.app.setUserProfile(userProfile);
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