
import React, { useState } from "react";
import { Pages } from "../Pages.js" 


function ForgotPassword(props) {

    const handleGoBack = () => {props.app.goBack();} 

    const handleClick = () => {
//        props.app.setFirstRunDone(true);
   //     props.app.changePage(Pages.SignInUI); 
    } 

    return(
        <>
        <h2>Enter your email to send reset link</h2>
        <button onClick={handleClick}>Send email</button>
        <br></br>
        <button onClick={handleGoBack}>Go Back</button>
            
        </>
    );
    

}

export default ForgotPassword;