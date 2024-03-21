import React, { useState } from 'react';
import Header from "./components/Header";
import { Form, Button } from 'react-bootstrap';
import ToggleThemeButton from "./components/ToggleThemeButton";
import './styles/Form.css';

function Settings(props) {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [theme, setTheme] = useState('light');
  const [language, setLanguage] = useState('english');
  const [newUsername, setNewUsername] = useState('');
  const [newPassword, setNewPassword] = useState('');

  

  const handleFirstNameChange = (event) => {
    setFirstName(event.target.value);
  };

  const handleLastNameChange = (event) => {
    setLastName(event.target.value);
  };

  const handleThemeChange = (event) => {
    setTheme(event.target.value);
  };

  const handleLanguageChange = (event) => {
    setLanguage(event.target.value);
  };

  const handleNewUsernameChange = (event) => {
    setNewUsername(event.target.value);
  };

  const handleNewPasswordChange = (event) => {
    setNewPassword(event.target.value);
  };

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
       <Form.Label>Theme:  .</Form.Label>
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
     <Form.Group controlId="newUsername">
       <Form.Label>New Username:</Form.Label>
       <Form.Control type="text" value={newUsername} onChange={handleNewUsernameChange} />
     </Form.Group>
     <Form.Group controlId="newPassword">
       <Form.Label>New Password:</Form.Label>
       <Form.Control type="password" value={newPassword} onChange={handleNewPasswordChange} />
     </Form.Group>
     <Button type="submit">Save Changes</Button>
   </Form>
 </div>
 </div>
  );
}

export default Settings;

