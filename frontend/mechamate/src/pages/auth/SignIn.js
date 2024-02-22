// SignIn.js
import React, { useState } from "react";
import Header from '../../components/Header';
import Button from '../../components/elements/Button';
import TextBox from '../../components/elements/TextBox';
import PasswordBox from '../../components/elements/PasswordBox';
import CheckBox from '../../components/elements/CheckBox';
import ConnectionManager from "../../services/ConnectionManager";

function SignIn() {
    const [recall, setRecall] = useState(false);

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [rememberMe, setRememberMe] = useState(false);
    const connection = new ConnectionManager();

    const usernamChanged = (e) => {setUsername(e.target.value)};
    const passwordChanged = (e) => {setPassword(e.target.value)};
    const rememberMeChanged = (e) => {setRememberMe(e.target.value)};

    return (
        <>
            <Header />
            <TextBox placeholder="username" onchange={usernamChanged} />
            <PasswordBox placeholder="password" onchange={passwordChanged} />
            <CheckBox caption='Keep me signed in' onchange={rememberMeChanged} />
            <Button onclick={() => {if(recall) {connection.login(username, password, rememberMe); }} } caption="Sign in" />
        </>
    );
}

export default SignIn;
