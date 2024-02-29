import React from 'react';
// import teamMember1 from './images/teamMember1.jpg';
// import teamMember2 from './images/teamMember2.jpg';
// import teamMember3 from './images/teamMember3.jpg';
// import teamMember4 from './images/teamMember4.jpg';
// import teamMember5 from './images/teamMember5.jpg';
// src={teamMember5}

function AboutUs(props) {
  return (
    <div>
      <h2>Our Team</h2>
      <div>
        <img  alt="Hansila" />
        <p>Hansila</p>
      </div>
      <div>
        <img  alt="Chamath" />
        <p>Chamath</p>
      </div>
      <div>
        <img  alt="Gihan" />
        <p>Gihan</p>
      </div>
      <div>
        <img  alt="Radeesh" />
        <p>Radeesh</p>
      </div>
      <div>
        <img  alt="Fathima" />
        <p>Fathima</p>
      </div>
      <button onClick={() => props.app.changePage('Dashboard')}>Go Back to Dashboard</button>
    </div>
  );
}

export default AboutUs;
