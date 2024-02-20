import React from "react";

function Button(props){
    return(
        <>
            <button onClick={props.onclick}>{props.caption}</button><br></br>
        </>
    );
}

export default Button;