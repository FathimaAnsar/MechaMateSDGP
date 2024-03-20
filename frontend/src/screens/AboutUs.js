import React from "react";
import "../screens/styles/AboutUs.css";
import p1 from "../images/p1.jpg";
import p2 from "../images/p2.jpg";
import p3 from "../images/p3.jpg";
import p4 from "../images/p4.jpg";
import p5 from "../images/p5.jpg";
import Header from "./components/Header";
import Carousel from "react-bootstrap/Carousel";

function AboutUs(props) {
  return (
    <>
      <Header app={props.app} />
      <header id="about-header">
        {/* <img src="./img1.jpg" alt="About Us Image" /> */}
        <h1>About Us</h1>
        <h6>Mechamate V1.0</h6>
      </header>

      <section className="" id="team-section">
        <h2>Meet Our Team</h2>
        <h4 className="">Developers and Board of Directors</h4>

        <Carousel>
          <Carousel.Item interval={null}>
            <div className="carousel-content">
              <div className="image-container">
                <img src={p1} alt="First slide" />
              </div>
              <div className="inner-div">
                <h1></h1>
                <div class="person-info">
                  <h4>Gihan Jayawickrama</h4>
                  <h5>FINANCIAL CONSULTANT</h5>
                  <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                    ullamco laboris nisi ut aliquip ex ea commodo consequat.
                    Duis aute irure dolor in reprehenderit in voluptate velit
                    esse cillum dolore eu fugiat nulla pariatur. Excepteur sint
                    occaecat cupidatat non proident, sunt in culpa qui officia
                    deserunt mollit anim id est laborum.
                  </p>
                </div>
              </div>
            </div>
          </Carousel.Item>

          <Carousel.Item interval={null}>
            <div className="carousel-content">
              <div className="image-container">
                <img src={p2} alt="First slide" />
              </div>
              <div className="inner-div">
                <h1></h1>
                <div class="person-info">
                  <h4>fathima Ansar</h4>
                  <h5>Web designer</h5>
                  <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                    ullamco laboris nisi ut aliquip ex ea commodo consequat.
                    Duis aute irure dolor in reprehenderit in voluptate velit
                    esse cillum dolore eu fugiat nulla pariatur. Excepteur sint
                    occaecat cupidatat non proident, sunt in culpa qui officia
                    deserunt mollit anim id est laborum.
                  </p>
                </div>
              </div>
            </div>
          </Carousel.Item>

          <Carousel.Item interval={null}>
            <div className="carousel-content">
              <div className="image-container">
                <img src={p3} alt="First slide" />
              </div>
              <div className="inner-div">
                <h1></h1>
                <div class="person-info">
                  <h4>Chamath Jayasanka</h4>

                  <h5>Project manager</h5>
                  <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                    ullamco laboris nisi ut aliquip ex ea commodo consequat.
                    Duis aute irure dolor in reprehenderit in voluptate velit
                    esse cillum dolore eu fugiat nulla pariatur. Excepteur sint
                    occaecat cupidatat non proident, sunt in culpa qui officia
                    deserunt mollit anim id est laborum.
                  </p>
                </div>
              </div>
            </div>
          </Carousel.Item>

          <Carousel.Item interval={null}>
            <div className="carousel-content">
              <div className="image-container">
                <img src={p4} alt="First slide" />
              </div>
              <div className="inner-div">
                <h1></h1>
                <div class="person-info">
                  <h4>Radeesh Mayadunne</h4>
                  <h5>Tech lead</h5>
                  <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                    ullamco laboris nisi ut aliquip ex ea commodo consequat.
                    Duis aute irure dolor in reprehenderit in voluptate velit
                    esse cillum dolore eu fugiat nulla pariatur. Excepteur sint
                    occaecat cupidatat non proident, sunt in culpa qui officia
                    deserunt mollit anim id est laborum.
                  </p>
                </div>
              </div>
            </div>
          </Carousel.Item>

          <Carousel.Item interval={null}>
            <div className="carousel-content">
              <div className="image-container">
                <img src={p5} alt="First slide" />
              </div>
              <div className="inner-div">
                <h1></h1>
                <div class="person-info">
                  <h4>Hansila Kodagoda</h4>
                  <h5>Network Administrator</h5>
                  <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                    do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                    ullamco laboris nisi ut aliquip ex ea commodo consequat.
                    Duis aute irure dolor in reprehenderit in voluptate velit
                    esse cillum dolore eu fugiat nulla pariatur. Excepteur sint
                    occaecat cupidatat non proident, sunt in culpa qui officia
                    deserunt mollit anim id est laborum.
                  </p>
                </div>
              </div>
            </div>
          </Carousel.Item>
        </Carousel>
      </section>
    </>

    // <>
    // <Header app ={props.app}/>
    //   <header id="about-header">
    //     {/* <img src="./img1.jpg" alt="About Us Image" /> */}
    //     <h1>About Us</h1>
    //   </header>

    //   <div className="card-container">
    //     <Card style={{ width: '18rem' }}>
    //       <Card.Img variant="top" src={p1} />
    //       <Card.Body>
    //         <Card.Title>Card Title</Card.Title>
    //         <Card.Text>
    //           Some quick example text to build on the card title and make up the bulk of the card's content.
    //         </Card.Text>
    //         <Button variant="primary">Go somewhere</Button>
    //       </Card.Body>
    //     </Card>

    //     <Card style={{ width: '18rem' }}>
    //       <Card.Img variant="top" src={p2} />
    //       <Card.Body>
    //         <Card.Title>Card Title</Card.Title>
    //         <Card.Text>
    //           Some quick example text to build on the card title and make up the bulk of the card's content.
    //         </Card.Text>
    //         <Button variant="primary">Go somewhere</Button>
    //       </Card.Body>
    //     </Card>

    //     <Card style={{ width: '18rem' }}>
    //       <Card.Img variant="top" src={p3} />
    //       <Card.Body>
    //         <Card.Title>Card Title</Card.Title>
    //         <Card.Text>
    //           Some quick example text to build on the card title and make up the bulk of the card's content.
    //         </Card.Text>
    //         <Button variant="primary">Go somewhere</Button>
    //       </Card.Body>
    //     </Card>

    //     <Card style={{ width: '18rem' }}>
    //       <Card.Img variant="top" src={p4} />
    //       <Card.Body>
    //         <Card.Title>Card Title</Card.Title>
    //         <Card.Text>
    //           Some quick example text to build on the card title and make up the bulk of the card's content.
    //         </Card.Text>
    //         <Button variant="primary">Go somewhere</Button>
    //       </Card.Body>
    //     </Card>

    //     <Card style={{ width: '18rem' }}>
    //       <Card.Img variant="top" src={p5} />
    //       <Card.Body>
    //         <Card.Title>Card Title</Card.Title>
    //         <Card.Text>
    //           Some quick example text to build on the card title and make up the bulk of the card's content.
    //         </Card.Text>
    //         <Button variant="primary">Go somewhere</Button>
    //       </Card.Body>
    //     </Card>
    //   </div>

    /* 
      <div id="container1" className="flex-container">
        <div className='sub-div sub1'>
          <p className="larger-text">MechaMate is the cutting-edge vehicle maintenance assistant developed by Group CS56 of IIT 2022 batch.</p>
        </div>
        <div className='sub-div sub2'>
          <p>MechaMate isn't just a vehicle maintenance assistant—it's a reliable partner dedicated to ensuring the safety, efficiency, and peace of mind of every vehicle owner in Sri Lanka, a forward-thinking software service provider in Sri Lanka. With a primary focus on predictive maintenance, MechaMate harnesses the power of AI and machine learning to forecast maintenance needs accurately, ensuring optimal vehicle health and minimizing unexpected breakdowns. This innovative solution addresses the common challenges faced by novice drivers, new vehicle owners, and especially women vehicle owners in Sri Lanka who may lack awareness about proper maintenance practices. MechaMate not only educates users about maintenance requirements but also assists them in finding authorized service providers, sourcing quality spare parts, and managing documentation and service records effortlessly.</p>
        </div>
      </div> */

    /* <Container style={{ marginTop: '20px' }}>
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
        </Container> */

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
