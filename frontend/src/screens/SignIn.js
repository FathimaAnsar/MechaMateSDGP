
import React, { useState, useEffect } from "react";
import { Pages } from "../Pages.js" 
import ConnectionManager from "../services/ConnectionManager.js"


function SignIn(props) {

    const handleGoBack = () => {props.app.goBack();} 
    const handleClick = (page) => { props.app.changePage(page); }
    const handleSubmit = async (e) => {
        e.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const keepMeSignedIn = document.getElementById('keep-me-signed-in').checked;

        // Validate inputs
        // If failed to validate, return here

        let connection = new ConnectionManager();

        const resp = await connection.signin(username, password, (keepMeSignedIn ? 1 : 0));
        const response = JSON.parse(resp);

        if(response.error) {
            alert("Error occured: " + response.message + "\n" + response.help);
        } else if(response.status) {
            props.app.setFirstRunDone(true);
            alert("Success: " + response.message + "\n" + response.info);
            const uProf = await connection.getUserProfile();
            const userProfile = JSON.parse(uProf);
            if(userProfile.error) {
                if(userProfile.error === "ErrorPendingActivation") {
                    alert("Your account is in a pending activation state. Please check your email inbox and enter the code we sent during registration process.");
                    props.app.changePage(Pages.EnterCodeUI);
                } else {
                    alert(userProfile.message + "\n" + userProfile.help);
                }
            } else {
                props.app.setUserProfile(userProfile);
                props.app.changePage(Pages.DashboardUI);
            }    
        } else {
            alert("Error: Unknown");
        }
    }

    useEffect(() => {
        try {
            let connection = new ConnectionManager();
            connection.getUserProfile().then( uProf => {
                const userProfile = JSON.parse(uProf);
                if(userProfile.error) {
                    if(userProfile.error === "ErrorPendingActivation") {
                        props.app.changePage(Pages.EnterCodeUI);
                    } else if(userProfile.error === "ErrorNotSignedIn") {
                        //
                    } else {
                        alert(userProfile.message + "\n" + userProfile.help);

                    }
                } else {
                    props.app.setUserProfile(userProfile);
                    props.app.changePage(Pages.DashboardUI);
                }    
            });
        } catch(exp) {}
    }, []);

    
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