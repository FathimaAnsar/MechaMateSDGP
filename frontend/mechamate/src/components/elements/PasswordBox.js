import React from "react";

function PasswordBox(props){
    return(
        <>
            <input type="password" onChange={props.onchange} placeholder={props.placeholder}/><br></br>
        </>
    );
}

export default PasswordBox;