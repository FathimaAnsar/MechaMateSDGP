
import React, { useState } from "react";
import { Pages } from "../Pages.js" 
import ConnectionManager from "../services/ConnectionManager.js"


function SignIn(props) {

    const handleGoBack = () => {props.app.goBack();} 
    const handleClick = (page) => { props.app.changePage(page); }
    const handleSubmit = async (e) => {
        e.preventDefault();

        
        const username = "justin";
        const password = "Pass123";
        const keepMeSignedIn = true;

        // const username = document.getElementById('username').value;
        // const password = document.getElementById('password').value;
        // const keepMeSignedIn = document.getElementById('keep-me-signed-in').checked;

        // Validate inputs
        // If failed to validate, return here

        let connection = new ConnectionManager();

        console.log(1234);
        const response = connection.signin(username, password, (keepMeSignedIn ? 1 : 0));

        console.log(2345);
        if(response) {
            console.log(response);
        }

        const userProfile = connection.getUserProfile();
        console.log(55678);

//        console.log(userProfile);
//        props.app.setUserProfile(userProfile);
}
        
    return(
        <>
    <button onClick={handleGoBack}>Go Back</button>

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

    <button onClick={() => {handleClick(Pages.SignUpUI)}}>Dont you have an account?</button>
    <br></br>
    <button onClick={() => {handleClick(Pages.ForgotPasswordUI)}}>Forgot Password?</button>
    <br></br>
    
        </>
    );
    

}



export default SignIn;