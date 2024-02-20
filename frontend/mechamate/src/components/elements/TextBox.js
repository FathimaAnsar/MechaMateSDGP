import React from "react";

function TextBox(props){
    return(
        <>
            <input type="text" onChange={props.onchange} placeholder={props.placeholder}/><br></br>
        </>
    );
}

export default TextBox;