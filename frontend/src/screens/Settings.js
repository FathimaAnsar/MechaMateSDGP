import React, { useState } from 'react';
import Header from "./components/Header";
import { Form, Button } from 'react-bootstrap';
import ToggleThemeButton from "./components/ToggleThemeButton";
import './styles/Form.css';

function Settings(props) {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [language, setLanguage] = useState('english');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordsMatch, setPasswordsMatch] = useState(true);
  

  const handleFirstNameChange = (event) => {
    setFirstName(event.target.value);
  };

  const handleLastNameChange = (event) => {
    setLastName(event.target.value);
  };

  const handleLanguageChange = (event) => {
    setLanguage(event.target.value);
  };
  const handleConfirmUsername =(event) => {
    setConfirmUsername(event.target.value);
  }

  const handleNewPasswordChange = (event) => {
    setNewPassword(event.target.value);
    checkPasswordsMatch(event.target.value, confirmPassword);
  };

  const handleConfirmPasswordChange = (event) => {
    setConfirmPassword(event.target.value);
    checkPasswordsMatch(newPassword, event.target.value);
  };

  const checkPasswordsMatch = (newPassword, confirmPassword) => {
    setPasswordsMatch(newPassword === confirmPassword);
  };

  }

  const handleSubmit = (event) => {
    event.preventDefault();
    // Logic to handle form submission (update settings)
  };

  return (
   <div>
    <Header app={props.app} />
    <div className="form-container">
   
   <h2>Settings</h2>
   <Form onSubmit={handleSubmit} className="settings-form">
     <Form.Group controlId="firstName">
       <Form.Label>First Name:</Form.Label>
       <Form.Control type="text" value={firstName} onChange={handleFirstNameChange} />
     </Form.Group>
     <Form.Group controlId="lastName">
       <Form.Label>Last Name:</Form.Label>
       <Form.Control type="text" value={lastName} onChange={handleLastNameChange} />
     </Form.Group>
     <Form.Group controlId="theme">
       <Form.Label>Theme:  :</Form.Label>
       <ToggleThemeButton />
     </Form.Group>
     <Form.Group controlId="language">
       <Form.Label>Language:</Form.Label>
       <Form.Select value={language} onChange={handleLanguageChange}>
         <option value="english">English</option>
         <option value="sinhala">Sinhala</option>
         <option value="tamil">Tamil</option>
       </Form.Select>
     </Form.Group>
     <Form.Group controlId="ConfirmUsername">
       <Form.Label>Confirm Username:</Form.Label>
       <Form.Control type="text" value={confirmUsername} onChange={handleConfirmUsername}/>
     </Form.Group>
      <Form.Group controlId="newPassword">
        <Form.Label>New Password:</Form.Label>
        <Form.Control type="password" value={newPassword} onChange={handleNewPasswordChange} />
      </Form.Group>
      <Form.Group controlId="conPassword">
        <Form.Label>Confirm Password:</Form.Label>
        <Form.Control type="password" value={confirmPassword} onChange={handleConfirmPasswordChange} />
      </Form.Group>
      {!passwordsMatch && <Alert variant="danger">Passwords do not match!</Alert>}
     <Button type="submit">Save Changes</Button>
   </Form>
 </div>
 </div>
  );


export default Settings;

