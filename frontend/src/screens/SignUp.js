
import React, { useState } from "react";
import { Pages } from "../Pages.js" 


function SignUp(props) {

    const handleGoBack = () => {props.app.goBack();} 

    const handleClick = () => {
        props.app.changePage(Pages.SignInUI); 
    } 

    return(
        <>

    <h2>Signup Form</h2>
    <form action="#" method="POST">
        <label htmlFor="firstname">First Name:</label><br></br>
        <input type="text" id="firstname" name="firstname" required/><br></br>

        <label htmlFor="lastname">Last Name:</label><br></br>
        <input type="text" id="lastname" name="lastname" required/><br></br>

        <label htmlFor="username">Username:</label><br></br>
        <input type="text" id="username" name="username" required/><br></br>

        <label htmlFor="password">Password:</label><br></br>
        <input type="password" id="password" name="password" required/><br></br>

        <label htmlFor="email">Email:</label><br></br>
        <input type="email" id="email" name="email" required/><br></br>

        <label htmlFor="telephone">Telephone:</label><br></br>
        <input type="tel" id="telephone" name="telephone"/><br></br>

        <input type="checkbox" id="keepsignedin" name="keepsignedin" value="yes"/>
        <label htmlFor="keepsignedin">Keep me signed in</label><br></br>

        <input type="submit" value="Sign Up"/>
    </form>
    <br></br>
    <button onClick={handleGoBack}>Go Back</button>
 
        </>
    );
    

}

export default SignUp;