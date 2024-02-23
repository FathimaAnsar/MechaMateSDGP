// ConnectionManager.js
import axios from 'axios';

class ConnectionManager {
    constructor() {}

    async login(userName, passWord, rememberMe) {
        try {
            const response = await axios.post('http://localhost:8080/api/v1/auth/signin', {
                username: userName,
                password: passWord,
                keepMeSignedIn: rememberMe
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                transformRequest: [(data, headers) => {
                    // Serialize the data to x-www-form-urlencoded format
                    const params = new URLSearchParams();
                    for (const key in data) {
                        params.append(key, data[key]);
                    }
                    return params.toString();
                }]
            });

            if (response.status === 200) {
                // Login successful, redirect the user to the home page
                alert(response.data.message)
                window.location.href = "/home";
            } else {
                // Login failed, handle the error (e.g., display an error message)
                alert(response.data.error)
                console.error('Login failed:', response.statusText);
            }
        } catch (error) {
            alert('An error occurred during login')
            console.error('Error:', error);
        }
    }
}

export default ConnectionManager;
