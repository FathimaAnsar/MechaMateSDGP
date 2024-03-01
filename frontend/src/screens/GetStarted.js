import React, { useState } from "react";
import { Container, Button, Card, CardFooter, CardImg } from 'react-bootstrap';
import logo from '../images/logo-white.png';
import { Pages } from "../Pages.js"
import '../styles/App.css';


import '../styles/App.css';

function GetStarted(props) {

    const handleClick = (page) => { props.app.changePage(page); }

    return (
        <>
            <Card style={{ height: '100vh', backgroundColor: '#085bd4', borderRadius: '0' }}>
                <Card.Body className="d-flex justify-content-center align-items-center">
                    <Card.Img variant="top" src={logo} style={{ width: '250px' }} />
                </Card.Body>
                <Card.Footer className='d-flex  justify-content-center' style={{ width: '100vw', marginBottom:'5px',borderTop: '0', backgroundColor: 'transparent' }}>
                    <Button style={{ backgroundColor: 'white', width: '300px', color: '#085bd4', fontWeight: '500', borderRadius: '30px', padding: '6px' }}
                            onClick={() => handleClick(Pages.SignInUI)}>
                        Let's Get Started
                    </Button>
                </Card.Footer>
            </Card>
            <Card></Card>
        </>
    );


}

export default GetStarted;

