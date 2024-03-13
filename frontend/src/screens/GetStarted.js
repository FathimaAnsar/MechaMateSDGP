import React, { useState, useEffect } from "react";
import { Container, Button, Card, CardFooter, CardImg, CardText } from 'react-bootstrap';
import logo from '../images/logo-white.png';
import { Pages } from "../Pages.js"


function GetStarted(props) {

    const handleClick = (page) => { props.app.changePage(page); }

   // const handleClick222 = () => { props.app.changePage(props.app.currentPage); } //test

    useEffect((e) => {
      //  props.lang.loadLanguage(true);
    });

    return (
        <>

            

            <Card style={{ height: '100vh', backgroundColor: '#085bd4', borderRadius: '0', margin: '0' }}>
                <Card.Body className="d-flex justify-content-center align-items-center">
                    <Card.Img variant="top" src={logo} style={{ width: '250px' }} />
                    <CardText></CardText>
                </Card.Body>
                <Card.Footer className='d-flex  justify-content-center' style={{ width: '90vw',borderTop: '0', backgroundColor: 'transparent',marginLeft:'5vw' }}>
                    <Button id="get-started-button" style={{ backgroundColor: 'white', width: '300px', color: '#085bd4', fontSize: '12pt',marginBottom:'25px', fontWeight: '700', borderRadius: '30px', padding: '8px' }}
                            onClick={() => handleClick(Pages.SignInUI)}>
                        Let's Get Started
                    </Button>
                </Card.Footer>
            </Card>
            
        </>
    );


}

export default GetStarted;

