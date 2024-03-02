import React from 'react';
import { Container, Row, Col, Image } from 'react-bootstrap';
import '../pages/css/AboutUs.css';

// import teamMember1 from './images/teamMember1.jpg';
// import teamMember2 from './images/teamMember2.jpg';
// import teamMember3 from './images/teamMember3.jpg';
// import teamMember4 from './images/teamMember4.jpg';
// import teamMember5 from './images/teamMember5.jpg';
// src={teamMember5}

function AboutUs(props) {
  return (

<div>
      <header  id="about-header">
        img
        <h1>About Us</h1>
      </header>

      <Container style={{ marginTop: '20px' }}>
        <Row>
          <Col>
            <h2>Our Mission</h2>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam nec purus vel augue molestie interdum nec vitae arcu.</p>
          </Col>
        </Row>

        <Row style={{ marginTop: '20px' }}>
          <Col>
            <h2>Meet Our Team</h2>
            <div className="team-member">
              <Image src="team-member1.jpg" alt="Team Member 1" roundedCircle style={{ marginRight: '20px', width: '100px', height: '100px' }} />
              <div>
                <h3>John Doe</h3>
                <p>Co-founder & CEO</p>
              </div>
            </div>
            <div className="team-member">
              <Image src="team-member2.jpg" alt="Team Member 2" roundedCircle style={{ marginRight: '20px', width: '100px', height: '100px' }} />
              <div>
                <h3>Jane Smith</h3>
                <p>Co-founder & CTO</p>
              </div>
            </div>
          </Col>
        </Row>
      </Container>

      <footer style={{ backgroundColor: '#333', color: 'white', padding: '20px', textAlign: 'center', marginTop: '20px' }}>
        <p>&copy; 2024 Your Company. All rights reserved.</p>
      </footer>
    </div>
















    // <div>
    //   <h2>Our Team</h2>
    //   <div>
    //     <img  alt="Hansila" />
    //     <p>Hansila</p>
    //   </div>
    //   <div>
    //     <img  alt="Chamath" />
    //     <p>Chamath</p>
    //   </div>
    //   <div>
    //     <img  alt="Gihan" />
    //     <p>Gihan</p>
    //   </div>
    //   <div>
    //     <img  alt="Radeesh" />
    //     <p>Radeesh</p>
    //   </div>
    //   <div>
    //     <img  alt="Fathima" />
    //     <p>Fathima</p>
    //   </div>
    //   <button onClick={() => props.app.goBack()}>Go Back to Dashboard</button>
    // </div>
  );
}

export default AboutUs;
