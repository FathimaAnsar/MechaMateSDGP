
import React, { useState } from "react";
import { Pages } from "../Pages.js" 
import ConnectionManager from "../services/ConnectionManager.js"


function ForgotPassword(props) {
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
        <h2>Enter your email to send reset link</h2>

        <form onSubmit={handleSubmit}>
                <label htmlFor="email">Email:</label><br />
                <input
                    type="email"
                    id="email"
                    name="email"
                    value={email}
                    onChange={handleChange}
                    required
                /><br />
                <button type="submit">Send email</button>
                <br />
                <button type="button" onClick={handleGoBack}>Go Back</button>
            </form>
            
        </>
    );
    

}

export default ForgotPassword;