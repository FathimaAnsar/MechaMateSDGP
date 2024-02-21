import React from "react";

function CheckBox(props) {
    return (
        <>
            <label>
                <input type="checkbox" onChange={props.onchange} />{props.caption}
            </label>
            <br></br>
        </>
    );
}

export default CheckBox;