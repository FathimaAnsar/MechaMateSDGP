import React from "react";

function Button(props){
    return(
        <>
            <button onClick={props.onclick(props.uname, props.psw, true)}>{props.caption}</button><br></br>
        </>
    );
}

export default Button;