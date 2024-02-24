import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import validateForm from './Validation';
import getLanguage from './language/lan-sign-in.js';

import logo from './logo-black.png';
import './css/SignIn.css';
import ConnectionManager from '../services/ConnectionManager';

const SignIn = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [keepMeSignedIn, setKeepMeSignedIn] = useState(false);
    const [activeLanguage, setActiveLanguage] = useState('english');
    const connectionManager = new ConnectionManager();

// Function to handle language change
const handleLanguageChange = (language) => {
    setActiveLanguage(language);
};

const languages = getLanguage(activeLanguage);

const handleLogin = async (e) => {
    e.preventDefault(); // Prevent default form submission
    if (validateForm()) {
        connectionManager.signIn(username, password, keepMeSignedIn);
    }
};

    return (
        <>
            <div id="login-container" className="login-container">
                <div id='login-error' className='error-message'>
                    {languages.loginError} <a href='/signup'>{languages.signUpForNewAccount}</a>
                </div>
                <img src={logo} className="sign-up-screen-logo" alt="logo"></img>
                <Form id='loginForm' onSubmit={handleLogin}>
                    <Form.Group controlId='username'>
                        <Form.Control
                            type='text'
                            placeholder={languages.username}
                            value={username}
                            autoComplete='current-username'
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </Form.Group>
                    <Form.Group controlId='password'>
                        <Form.Control
                            type='password'
                            placeholder={languages.password}
                            value={password}
                            autoComplete='current-password'
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </Form.Group>
                    <Form.Group controlId='keepMeSignedIn'>
                        <label>
                            <input
                                type="checkbox"
                                checked={keepMeSignedIn}
                                onChange={(e) =>
                                    setKeepMeSignedIn(e.target.checked)
                                }
                            />
                            {languages.rememberMe}
                        </label>
                    </Form.Group>
                    <Button variant='primary' type='submit'>
                        {languages.login}
                    </Button>
                </Form>
                <div className="forget-password"><a href='/home'>{languages.forgottenPassword}</a></div>
                <div className='sign-up'>{languages.dontHaveAnAccount} <a href='/signup'>{languages.signup}</a></div>
            </div>

            <div className="login-footer">
                <div className='languages'>
                    <Button
                        id='bt-sinhala'
                        className={activeLanguage === 'sinhala' ? 'active-lan-button' : ''}
                        onClick={() => handleLanguageChange('sinhala')}
                    >
                        සිංහල
                    </Button>
                    <Button
                        id='bt-en'
                        className={activeLanguage === 'english' ? 'active-lan-button' : ''}
                        onClick={() => handleLanguageChange('english')}
                    >
                        English
                    </Button>
                    <Button
                        id='bt-tamil'
                        className={activeLanguage === 'tamil' ? 'active-lan-button' : ''}
                        onClick={() => handleLanguageChange('tamil')}
                    >
                        தமிழ்
                    </Button>
                </div>
                <div className='copyright'>MechaMate © 2024</div>
            </div>
        </>
    );
};

export default SignIn;
