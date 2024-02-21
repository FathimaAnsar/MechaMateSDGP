// SignIn.js
import React, { useState } from "react";
import Header from '../../components/Header';
import Button from '../../components/elements/Button';
import TextBox from '../../components/elements/TextBox';
import PasswordBox from '../../components/elements/PasswordBox';
import CheckBox from '../../components/elements/CheckBox';
import ConnectionManager from "../../services/ConnectionManager";

function SignIn() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [rememberMe, setRememberMe] = useState(false);
    const connection = new ConnectionManager();

    const handleSignIn = async () => {
        try {
            // Call the login method of ConnectionManager
            const response = await connection.login(username, password, rememberMe);
            if (response && response.success) {
                // Login successful, redirect the user to the home page
                alert(response.message);
                window.location.href = "/home";
            } else {
                // Login failed, display an error message
                alert(response.error || 'An error occurred during login');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred during login');
        }
    }

    return (
        <>
            <Header />
            <TextBox
                placeholder="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <PasswordBox
                placeholder="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <CheckBox
                caption='Keep me signed in'
                checked={rememberMe}
                onChange={(e) => setRememberMe(e.target.checked)}
            />
            <Button onClick={handleSignIn} caption="Sign in" />
        </>
    );
}

export default SignIn;
