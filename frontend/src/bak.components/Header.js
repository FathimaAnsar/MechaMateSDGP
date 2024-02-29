import React from "react";
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import ToggleThemeButton from './ToggleThemeButton';

function Header() {
    return (
        <Navbar data-bs-theme="dark" expand="lg" className="bg-body-tertiary">
            <Container>
                <Navbar.Brand href="#home">MechaMate</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/home">Home</Nav.Link>
                        <Nav.Link href="/documents">Documents</Nav.Link>
                        <NavDropdown title="Actions" id="basic-nav-dropdown">
                            <NavDropdown.Item href="/Mechanics">Find Mechanic</NavDropdown.Item>
                            <NavDropdown.Item href="/serviceRecords">Service Records</NavDropdown.Item>
                            <NavDropdown.Item href="/Emergency">Emergency Service</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="/contact">Contact Us</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                    <ToggleThemeButton />
                </Navbar.Collapse>

            </Container>
        </Navbar>
    );
}

export default Header;

