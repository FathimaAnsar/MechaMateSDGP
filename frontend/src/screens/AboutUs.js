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
                  <h5>DevOps</h5>
                  <p>
                  As a DevOps engineer involved in the development of the mechamate web application,
                   my role revolves around ensuring seamless collaboration between development
                    and operations teams to streamline the deployment and management processes. 
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
                  <h4>Fathima Ansar</h4>
                  <h5>Web designer UI/UX</h5>
                  <p>
                  As a UI/UX designer involved in the development of the Mechamate web application,
                   my role centers around crafting intuitive and visually appealing user interfaces 
                   while ensuring a seamless user experience.
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

                  <h5>Programmer</h5>
                  <p>
                  As a programmer involved in the development of the Mechamate web application,
                   my role focuses on writing clean, efficient, and maintainable code to implement
                    the application's functionality.
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
                  <h5>System Architect</h5>
                  <p>
                  As a System Architect involved in the development of the Mechamate web application,
                   my role is to design the overall architecture and infrastructure of the system to
                    meet the functional and non-functional requirements.
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
                  <h5>Web Administrator</h5>
                  <p>
                  As a Web Administrator involved in the development of the Mechamate web application,
                   my role is to manage and maintain the infrastructure and server environment that 
                   hosts the application. 
                  </p>
                </div>
              </div>
            </div>
          </Carousel.Item>
        </Carousel>
      </section>
    </>
  );
}

export default AboutUs;
