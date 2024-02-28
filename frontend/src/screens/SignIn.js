
import React, { useState } from "react";
import { Pages } from "../Pages.js" 
import ConnectionManager from "../ConnectionManager.js"


function SignIn(props) {

    const handleGoBack = () => {props.app.goBack();} 
    
    const handleSignUp = () => {
        props.app.changePage(Pages.SignUpUI); 
    }

    const handleForgotPass = () => {
        props.app.changePage(Pages.ForgotPasswordUI); 
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        let connection = new ConnectionManager();
        console.log(connection.signin("justin", "Pass123!")); 

        console.log(connection.getProfile()); 

    }
        
    return(
        <>
    <h1>Sign In</h1>
    
    <form action="#" method="post">
        <label htmlFor="username">Username:</label><br></br>
        <input type="text" id="username" name="username"/><br></br>
        
        <label htmlFor="password">Password:</label><br></br>
        <input type="password" id="password" name="password"/><br></br>
        
        <input type="checkbox" id="already_signed_in" name="already_signed_in"/>
        <label htmlFor="already_signed_in">Already Signed In</label><br></br>
        
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