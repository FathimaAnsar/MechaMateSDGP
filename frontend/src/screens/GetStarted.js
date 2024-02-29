
import React, { useState } from "react";
import { Pages } from "../Pages.js"
import { ButtonGroup, Button } from 'react-bootstrap';


function GetStarted(props) {

    const handleClick = (page) => { props.app.changePage(page); }

    return (
        <>
            <h2>Welcome to Mechamate</h2>
            <p>Please click 'Get Started' to get started!</p>
            <ButtonGroup vertical>
                <Button variant="primary" onClick={() => handleClick(Pages.SignInUI)}>Get Started</Button>
            </ButtonGroup >
        </>
    );


}

export default GetStarted;

