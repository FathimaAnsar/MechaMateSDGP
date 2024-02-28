
import React, { useState } from "react";
import { Pages } from "../Pages.js" 


function GetStarted(props) {


    const handleClick = () => {
//        props.app.setFirstRunDone(true);
        props.app.changePage(Pages.SignInUI); 
    } 

    return(
        <>
        <h2>Welcome to Mechamate</h2>
        <button onClick={handleClick}>Get Started</button>
        </>
    );
    

}

export default GetStarted;