import React from 'react';
import '../pages/css/AboutUs.css';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import p1 from '../images/p1.avif';
import p2 from '../images/p2.avif';
import p3 from '../images/p3.avif';
import p4 from '../images/p4.avif';
import p5 from '../images/p5.avif';


function AboutUs(props) {
  return (
    <>
      <header id="about-header">
        {/* <img src="./img1.jpg" alt="About Us Image" /> */}
        <h1>About Us</h1>
      </header>


      <div className="card-container">
        <Card style={{ width: '18rem' }}>
          <Card.Img variant="top" src={p1} />
          <Card.Body>
            <Card.Title>Card Title</Card.Title>
            <Card.Text>
              Some quick example text to build on the card title and make up the bulk of the card's content.
            </Card.Text>
            <Button variant="primary">Go somewhere</Button>
          </Card.Body>
        </Card>

        <Card style={{ width: '18rem' }}>
          <Card.Img variant="top" src={p2} />
          <Card.Body>
            <Card.Title>Card Title</Card.Title>
            <Card.Text>
              Some quick example text to build on the card title and make up the bulk of the card's content.
            </Card.Text>
            <Button variant="primary">Go somewhere</Button>
          </Card.Body>
        </Card>

        <Card style={{ width: '18rem' }}>
          <Card.Img variant="top" src={p3} />
          <Card.Body>
            <Card.Title>Card Title</Card.Title>
            <Card.Text>
              Some quick example text to build on the card title and make up the bulk of the card's content.
            </Card.Text>
            <Button variant="primary">Go somewhere</Button>
          </Card.Body>
        </Card>

        <Card style={{ width: '18rem' }}>
          <Card.Img variant="top" src={p4} />
          <Card.Body>
            <Card.Title>Card Title</Card.Title>
            <Card.Text>
              Some quick example text to build on the card title and make up the bulk of the card's content.
            </Card.Text>
            <Button variant="primary">Go somewhere</Button>
          </Card.Body>
        </Card>

        <Card style={{ width: '18rem' }}>
          <Card.Img variant="top" src={p5} />
          <Card.Body>
            <Card.Title>Card Title</Card.Title>
            <Card.Text>
              Some quick example text to build on the card title and make up the bulk of the card's content.
            </Card.Text>
            <Button variant="primary">Go somewhere</Button>
          </Card.Body>
        </Card>
      </div>




      {/* 
      <div id="container1" className="flex-container">
        <div className='sub-div sub1'>
          <p className="larger-text">MechaMate is the cutting-edge vehicle maintenance assistant developed by Group CS56 of IIT 2022 batch.</p>
        </div>
        <div className='sub-div sub2'>
          <p>MechaMate isn't just a vehicle maintenance assistant—it's a reliable partner dedicated to ensuring the safety, efficiency, and peace of mind of every vehicle owner in Sri Lanka, a forward-thinking software service provider in Sri Lanka. With a primary focus on predictive maintenance, MechaMate harnesses the power of AI and machine learning to forecast maintenance needs accurately, ensuring optimal vehicle health and minimizing unexpected breakdowns. This innovative solution addresses the common challenges faced by novice drivers, new vehicle owners, and especially women vehicle owners in Sri Lanka who may lack awareness about proper maintenance practices. MechaMate not only educates users about maintenance requirements but also assists them in finding authorized service providers, sourcing quality spare parts, and managing documentation and service records effortlessly.</p>
        </div>
      </div> */}



      {/* <Container style={{ marginTop: '20px' }}>
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
      </Container> */}


    </>
















    // <div>
    //   <h2>Our Team</h2>
    //   <div>
    //     <img alt="Hansila" />
    //     <p>Hansila</p>
    //   </div>
    //   <div>
    //     <img alt="Chamath" />
    //     <p>Chamath</p>
    //   </div>
    //   <div>
    //     <img alt="Gihan" />
    //     <p>Gihan</p>
    //   </div>
    //   <div>
    //     <img alt="Radeesh" />
    //     <p>Radeesh</p>
    //   </div>
    //   <div>
    //     <img alt="Fathima" />
    //     <p>Fathima</p>
    //   </div>
    //   <button onClick={() => props.app.goBack()}>Go Back to Dashboard</button>
    // </div>
  );
}

export default AboutUs;
