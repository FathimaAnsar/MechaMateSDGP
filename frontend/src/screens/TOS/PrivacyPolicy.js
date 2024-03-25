import React from "react";
import { Container, Row, Col, Card, Nav } from "react-bootstrap";

function PrivacyPolicy() {
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
            <Card.Header as="h5">Privacy Policy</Card.Header>
            <Card.Body>
              <Card.Text>
              Our privacy policy outlines how we handle your personal information when you use our software. We collect and use personal information, such as your name and email address, solely to improve our services and do not share it with third parties. We also collect Log Data, including your IP address and browser information, to enhance your experience. Cookies may be used for this purpose, and you can choose to accept or reject them. While we strive to protect your information, we cannot guarantee absolute security. Our software may contain links to third-party sites, and we are not responsible for their content or practices. We may update our privacy policy periodically, and any changes will be posted here. If you have any questions, please contact us.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

export default PrivacyPolicy;
