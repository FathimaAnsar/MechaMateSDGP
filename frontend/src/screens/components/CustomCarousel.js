import React from 'react';
import Carousel from 'react-bootstrap/Carousel';
import Image from 'react-bootstrap/Image';
import slide1 from '../../images/slides/1.jpg';
import slide2 from '../../images/slides/2.jpg';
import slide3 from '../../images/slides/3.jpg';

function CustomCarousel({ }) {
  const slides = [
    { interval: 2000, imageSrc: slide1, alt: "First slide", label: "First slide label", description: "Nulla vitae elit libero, a pharetra augue mollis interdum." },
    { interval: 2000, imageSrc: slide2, alt: "Second slide", label: "Second slide label", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit." },
    { interval: 2000, imageSrc: slide3, alt: "Third slide", label: "Third slide label", description: "Praesent commodo cursus magna, vel scelerisque nisl consectetur." }
  ];
  return (
    <Carousel 
    slide
    controls={false}
    indicators={false}> 
      {slides.map((slide, index) => (
        <Carousel.Item key={index} interval={slide.interval}>
          <Image src={slide.imageSrc} alt={slide.alt} style={{width:'100%'}}/>
          <Carousel.Caption>
            {/* <h3>{slide.label}</h3>
            <p>{slide.description}</p> */}
          </Carousel.Caption>
        </Carousel.Item>
      ))}
    </Carousel>
  );
}

export default CustomCarousel;
