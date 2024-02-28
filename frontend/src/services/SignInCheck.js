import React, { useEffect } from 'react';
import SignIn from '../pages/SignIn';
import Home from '../pages/Home';

function SignInCheck(WrappedComponent) {
    return function SignInCheck(props) {
        useEffect(() => {
            const userProfile = localStorage.getItem('userProfile');
            if (!userProfile){
                <SignIn />
            }
        }, []);

        return <WrappedComponent {...props} />;
    };
}
export function IsNotSignedInCheck() {
    useEffect(() => {
        const userProfile = localStorage.getItem('userProfile');
        if (userProfile) {
            window.location.href = '/home';
        } 
    }, []); // Empty dependency array means this effect runs once after the component mounts
}


export default SignInCheck;
