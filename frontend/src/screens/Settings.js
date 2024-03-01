import React, { useState } from 'react';

function Settings() {
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
      <h2>Settings</h2>
      <form onSubmit={handleSubmit}>
        <label>
          First Name:
          <input type="text" value={firstName} onChange={handleFirstNameChange} />
        </label>
        <br />
        <label>
          Last Name:
          <input type="text" value={lastName} onChange={handleLastNameChange} />
        </label>
        <br />
        <label>
          Theme:
          <select value={theme} onChange={handleThemeChange}>
            <option value="light">Light</option>
            <option value="dark">Dark</option>
          </select>
        </label>
        <br />
        <label>
          Language:
          <select value={language} onChange={handleLanguageChange}>
            <option value="english">English</option>
            <option value="sinhala">Sinhala</option>
            <option value="tamil">Tamil</option>
          </select>
        </label>
        <br />
        <label>
          New Username:
          <input type="text" value={newUsername} onChange={handleNewUsernameChange} />
        </label>
        <br />
        <label>
          New Password:
          <input type="password" value={newPassword} onChange={handleNewPasswordChange} />
        </label>
        <br />
        <button type="submit">Save Changes</button>
      </form>
    </div>
  );
}

export default Settings;

