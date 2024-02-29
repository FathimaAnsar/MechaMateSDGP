
import React, { useState } from "react";
import { Pages } from "../Pages.js" 


function GetStarted(props) {


    const handleClick = () => {
//        props.app.setFirstRunDone(true);
        props.app.changePage(Pages.SignInUI); 
    } 

    const handleDashboardClick = () => {
        //        props.app.setFirstRunDone(true);
                props.app.changePage(Pages.DashboardUI); 
            } 

    return(
        <>
        <h2>Welcome to Mechamate</h2>
        <button onClick={handleClick}>Get Started</button>
        <button onClick={handleDashboardClick}>getDashboard</button>
        </>
    );
    

}

export default GetStarted;