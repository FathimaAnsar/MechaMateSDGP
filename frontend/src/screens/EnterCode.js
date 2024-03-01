import React, { useState } from "react";
import { Pages } from "../Pages.js" 
import ConnectionManager from "../services/ConnectionManager.js"


function EnterCode(props) {
    const [email, setEmail] = useState('');

    const handleChange = (e) => {
        setEmail(e.target.value);
    };

    const handleGoBack = () => {props.app.goBack();} 

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {

            let connection = new ConnectionManager();
            const recoverResponse = await connection.sendRecoverEmail(email);
            console.log("Recovery email sent successfully:", recoverResponse);

        } catch (error) {
            console.error("Error sending recovery email:", error);
        }
    };

    return(
        <>
        <button type="button" onClick={handleGoBack}>Go Back</button>

        <h2>Enter your email to send reset link</h2>

        <form onSubmit={handleSubmit}>
                <label htmlFor="code">Activation Code:</label><br />
                <input type="code" id="code" name="code" value={email} required/><br />
                <button type="submit">Activate</button>
                <br />
                <br />
            </form>
            
        </>
    );
    

}

export default EnterCode;