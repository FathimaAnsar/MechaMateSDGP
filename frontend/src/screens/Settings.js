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


  const handleFirstNameChange = (event) => {
    setFirstName(event.target.value);
  };

  const handleLastNameChange = (event) => {
    setLastName(event.target.value);
  };

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

  }

  const handleSubmit = (event) => {
    event.preventDefault();
    // Logic to handle form submission (update settings)
  };

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

export default Settings;

