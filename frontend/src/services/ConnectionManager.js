import axios from 'axios';

// const domain = "https://mechamate-backend.el.r.appspot.com/";

class ConnectionManager {
    constructor() {}

    async signIn(userName, passWord, rememberMe) {
        const errorMessage = document.getElementById("login-error");

        try {
            const response = await axios.post('https://mechamate-backend.el.r.appspot.com/api/v1/auth/signin', {
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
                window.location.href = "/home";
            } else if (response.status === 400){
                // Login failed, handle the error 
                errorMessage.style.display = "block";
                console.error('Login failed:', response.statusText);
            }
            

        } catch (error) {
            errorMessage.style.display = "block";
            console.error('Error:', error);
        }
    }

    async signUp(formData) {
        try {
            const response = await axios.post('https://mechamate-backend.el.r.appspot.com/api/v1/auth/signup', formData, {
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
            alert("done")

            if (response.status === 200) {
                // Sign up successful, handle success 
                window.location.href = "/home";
            } else {
                // Handle other status codes
                if (response.status === 401) {
                    // Unauthorized, handle accordingly 
                    alert("Unauthorized. Please check your credentials.");
                    // Redirect to login page
                    window.location.href = "/login";
                } else {
                    // Handle other status codes
                    console.error('Error:', response.status);
                    // Display a generic error message
                    alert("An error occurred. Please try again later.");
                }
            }
        } catch (error) {
            // for testing purposes
            alert(error.response.data.message)
        }

    }
    
}


export default ConnectionManager;
