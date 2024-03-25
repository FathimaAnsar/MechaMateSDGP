import React from "react";
import { Container, Row, Col, Card, Nav } from "react-bootstrap";

function Terms() {
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
            <Card.Header as="h5">Terms</Card.Header>
            <Card.Body>
              <Card.Text>
              By using our software, you agree to these terms and conditions. You may only use the software for lawful purposes and agree not to modify or exploit it. Our software and its content are protected by intellectual property laws. We may terminate or suspend access for any violation. We reserve the right to change these terms with notice. If you continue to use the software after changes, you accept the new terms. If you have questions, contact us.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

export default Terms;
