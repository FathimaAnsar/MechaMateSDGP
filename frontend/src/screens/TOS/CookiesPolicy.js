import React from "react";
import { Container, Row, Col, Card, Nav } from "react-bootstrap";

function CookiesPolicy() {
  return (
    <Container>
      <Row>
        <Col md={3}>
          <Nav defaultActiveKey="/home" className="flex-column">
            <Nav.Link href="/home">Section 1</Nav.Link>
            <Nav.Link href="/home">Section 2</Nav.Link>
            <Nav.Link href="/home">Section 3</Nav.Link>
          </Nav>
        </Col>
        <Col md={9}>
          <Card>
            <Card.Header as="h5">Cookies Policy</Card.Header>
            <Card.Body>
              <Card.Text>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed
                euismod ultrices nunc, nec sollicitudin arcu sodales et.
                Phasellus euismod libero at est placerat, euismod elementum
                augue hendrerit. Proin commodo leo vel ligula elementum, et
                mattis purus fringilla. Duis convallis ligula in leo
                sollicitudin, ut facilisis urna euismod. Cras vel ullamcorper
                est, id fringilla nunc. Proin tempus bibendum feugiat. Cras
                ullamcorper magna in massa feugiat, nec mattis justo
                scelerisque. Aenean commodo velit nec erat tempor posuere. Ut
                convallis ut neque in sollicitudin. Mauris viverra auctor
                mauris, nec rhoncus orci varius nec.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

export default CookiesPolicy;
