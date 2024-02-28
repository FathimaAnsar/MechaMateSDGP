
import React, { useState } from "react";
import { Pages } from "../Pages.js" 


function SignUp(props) {

    const handleGoBack = () => {props.app.goBack();} 

    const handleClick = () => {
        props.app.setFirstRunDone(true);
        props.app.changePage("signin"); 
    } 

    return(
        <>

    <h2>Signup Form</h2>
    <form action="/submit-your-form-here" method="POST">
        <label for="firstname">First Name:</label><br></br>
        <input type="text" id="firstname" name="firstname" required/><br></br>

        <label for="lastname">Last Name:</label><br></br>
        <input type="text" id="lastname" name="lastname" required/><br></br>

        <label for="username">Username:</label><br></br>
        <input type="text" id="username" name="username" required/><br></br>

        <label for="password">Password:</label><br></br>
        <input type="password" id="password" name="password" required/><br></br>

        <label for="email">Email:</label><br></br>
        <input type="email" id="email" name="email" required/><br></br>

        <label for="telephone">Telephone:</label><br></br>
        <input type="tel" id="telephone" name="telephone"/><br></br>

        <input type="checkbox" id="keepsignedin" name="keepsignedin" value="yes"/>
        <label for="keepsignedin">Keep me signed in</label><br></br>

        <input type="submit" value="Sign Up"/>
    </form>
    <br></br>
    <button onClick={handleGoBack}>Go Back</button>
 
        </>
    );
    

}

export default SignUp;