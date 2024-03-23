<<<<<<< HEAD
// import React, { useState } from 'react';
// import Header from "./components/Header";
// import { Form, Button } from 'react-bootstrap';
// import ToggleThemeButton from "./components/ToggleThemeButton";
// import './styles/Form.css';

// function Settings(props) {
//   const [firstName, setFirstName] = useState('');
//   const [lastName, setLastName] = useState('');
//   const [language, setLanguage] = useState('english');
//   const [newPassword, setNewPassword] = useState('');
//   const [confirmPassword, setConfirmPassword] = useState('');
//   const [passwordsMatch, setPasswordsMatch] = useState(true);
  
=======
import React, { useState } from 'react';
import Header from "./components/Header";
import { Form, Button, Container, Row, Col } from 'react-bootstrap';


function Settings(props) {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [language, setLanguage] = useState('english');
  const [newPassword, setNewPassword] = useState('');
  const [confirmUsername, setConfirmUsername] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('')

  const userProfile = props.app.getUserProfile();

>>>>>>> d2105a9435c6a9f154a6b5c4ea0612b1d6612403

//   const handleFirstNameChange = (event) => {
//     setFirstName(event.target.value);
//   };

//   const handleLastNameChange = (event) => {
//     setLastName(event.target.value);
//   };

<<<<<<< HEAD
//   const handleLanguageChange = (event) => {
//     setLanguage(event.target.value);
//   };
//   const handleConfirmUsername =(event) => {
//     setConfirmUsername(event.target.value);
//   }

//   const handleNewPasswordChange = (event) => {
//     setNewPassword(event.target.value);
//     checkPasswordsMatch(event.target.value, confirmPassword);
//   };

//   const handleConfirmPasswordChange = (event) => {
//     setConfirmPassword(event.target.value);
//     checkPasswordsMatch(newPassword, event.target.value);
//   };

//   const checkPasswordsMatch = (newPassword, confirmPassword) => {
//     setPasswordsMatch(newPassword === confirmPassword);
//   };

 
=======
  const handleLanguageChange = (event) => {
    setLanguage(event.target.value);
  };
  const handleConfirmUsername = (event) => {
    setConfirmUsername(event.target.value);
  }

  const handleNewPasswordChange = (event) => {
    setNewPassword(event.target.value);
  };
  const handleConfirmPassword = (event) => {
    setConfirmPassword(event.target.value);

    if (newPassword !== confirmPassword) {
      alert("Passwords do not match");
      return;
    }

  
>>>>>>> d2105a9435c6a9f154a6b5c4ea0612b1d6612403

//   const handleSubmit = (event) => {
//     event.preventDefault();
//     // Logic to handle form submission (update settings)
//   };

<<<<<<< HEAD
//   return (
//    <div>
//     <Header app={props.app} />
//     <div className="form-container">
   
//    <h2>Settings</h2>
//    <Form onSubmit={handleSubmit} className="settings-form">
//      <Form.Group controlId="firstName">
//        <Form.Label>First Name:</Form.Label>
//        <Form.Control type="text" value={firstName} onChange={handleFirstNameChange} />
//      </Form.Group>
//      <Form.Group controlId="lastName">
//        <Form.Label>Last Name:</Form.Label>
//        <Form.Control type="text" value={lastName} onChange={handleLastNameChange} />
//      </Form.Group>
//      <Form.Group controlId="theme">
//        <Form.Label>Theme:  :</Form.Label>
//        <ToggleThemeButton />
//      </Form.Group>
//      <Form.Group controlId="language">
//        <Form.Label>Language:</Form.Label>
//        <Form.Select value={language} onChange={handleLanguageChange}>
//          <option value="english">English</option>
//          <option value="sinhala">Sinhala</option>
//          <option value="tamil">Tamil</option>
//        </Form.Select>
//      </Form.Group>
//      <Form.Group controlId="ConfirmUsername">
//        <Form.Label>Confirm Username:</Form.Label>
//        <Form.Control type="text" value={confirmUsername} onChange={handleConfirmUsername}/>
//      </Form.Group>
//       <Form.Group controlId="newPassword">
//         <Form.Label>New Password:</Form.Label>
//         <Form.Control type="password" value={newPassword} onChange={handleNewPasswordChange} />
//       </Form.Group>
//       <Form.Group controlId="conPassword">
//         <Form.Label>Confirm Password:</Form.Label>
//         <Form.Control type="password" value={confirmPassword} onChange={handleConfirmPasswordChange} />
//       </Form.Group>
//       {!passwordsMatch && <Alert variant="danger">Passwords do not match!</Alert>}
//      <Button type="submit">Save Changes</Button>
//    </Form>
//  </div>
//  </div>
//   );
//   }

// export default Settings;
=======
  return (
    <div className='text-centered'>
      <Header app={props.app} />

      <Container fluid='sm' style={{ maxWidth: '400px' }}>
        <div className="settings-container">
          <h2>Settings</h2>
          <Form onSubmit={handleSubmit} className="settings-form">
            <Row className="mb-3" xs={1} md={2} lg={2}>
              <Form.Group as={Col} controlId="firstName">
                <Form.Label>First Name:</Form.Label>
                <Form.Control type="text" value={firstName} onChange={handleFirstNameChange} placeholder={userProfile.firstName} />
              </Form.Group>
              <Form.Group as={Col} controlId="lastName">
                <Form.Label>Last Name:</Form.Label>
                <Form.Control type="text" value={lastName} placeholder={userProfile.lastName} onChange={handleLastNameChange} />
              </Form.Group>
            </Row>

            <Row className="mb-3" xs={1} md={2} lg={2}>
              <Form.Group as={Col} controlId="newPassword">
                <Form.Label>New Password:</Form.Label>
                <Form.Control type="password" value={newPassword} onChange={handleNewPasswordChange} placeholder='********' />
              </Form.Group>
              <Form.Group as={Col} controlId="conPassword">
                <Form.Label>Confirm Password:</Form.Label>
                <Form.Control type="password" placeholder='********' value={confirmPassword} onChange={handleConfirmPassword} />
              </Form.Group>
            </Row>
            <Row className="mb-3">
              <Form.Group as={Col} controlId="language">
                <Form.Label>Language:</Form.Label>
                <Form.Select
                  value={userProfile.language !== 'default' ? userProfile.language : 'english'}
                  onChange={handleLanguageChange}
                >
                  <option value="english">English</option>
                  <option value="sinhala">Sinhala</option>
                  <option value="tamil">Tamil</option>
                </Form.Select>
              </Form.Group>
            </Row>

            <Row className="mb-3">
              <Col>
                <Button type="submit">Save Changes</Button>
              </Col>
            </Row>
          </Form>
        </div>
      </Container >

    </div >
  );
}
}

export default Settings;
>>>>>>> d2105a9435c6a9f154a6b5c4ea0612b1d6612403

