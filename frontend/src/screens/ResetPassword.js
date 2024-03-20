
import React from "react";

function ReplaceMe(props) {

    const handleGoBack = () => {props.app.goBack();} 

    const handleClick = () => {
   //     props.app.changePage(Pages.SignInUI); 
    } 

    return(
        <>
        <h2>Page</h2>
        <button onClick={handleClick}>Test</button>
        <br></br>
        <button onClick={handleGoBack}>Go Back</button>
            
        </>
    );
    

}

export default ReplaceMe;