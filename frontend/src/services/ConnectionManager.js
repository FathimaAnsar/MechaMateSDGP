
import { API_BASE_URL } from "../Common.js"


class ConnectionManager {

    constructor() {

    }

    async sendPOSTRequest(apiEndPoint, contentType, postParams) {
        try {
            const response = await fetch(`${API_BASE_URL + apiEndPoint}`, {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: postParams,
                credentials: 'include',
            }).then( response => {
                    response.json().then( json => {
                        return json;
                    })
            }

                
            );
            const data = await response.json();
            if (response.ok) {
                // Response is successful, parse JSON data
                const data = await response.json();
                // Now you can work with the JSON data
                console.log(data); // Do whatever you want with the data
                return JSON.stringify(data); // Convert data to JSON string and return
            } else {
                // Handle non-successful response
                throw new Error('Failed to fetch data');
            }
        } catch (error) {
            // Handle errors in the try block
            console.error('Error:', error);
        }
        
    }

    async sendGETRequest(apiEndPoint) {
        try {
            const response = await fetch(`${API_BASE_URL + apiEndPoint}`, {
                method: 'GET',
                credentials: 'include',
            });
            
            if (response.ok) {
                // Parse the JSON data from the response
                const jsonData = await response.json();
                return jsonData;
            } else {
                // Handle non-successful response
                throw new Error('Failed to fetch data');
            }
        } catch (error) {
            // Handle errors
            console.error('Error:', error);
            throw error; // Rethrow the error for the caller to handle if needed
        }
    }

    signin(username, password) {
        const urlEncodedData = new URLSearchParams();
        urlEncodedData.append('username', username);
        urlEncodedData.append('password', password);
        return this.sendPOSTRequest("/api/v1/auth/signin", "", urlEncodedData);
//        setTimeout(() => {}, 3000);
    }

    signup(userData) {
        const urlEncodedData = new URLSearchParams();
    
        for (const [key, value] of Object.entries(userData)) {
            urlEncodedData.append(key, value);
        }
    
        return this.sendPOSTRequest("/api/v1/auth/signup", "", urlEncodedData);
    }

    sendRecoverEmail(email) {
        const urlEncodedData = new URLSearchParams();
        urlEncodedData.append('email', email);
    
        return this.sendPOSTRequest("/api/v1/auth/recover", "", urlEncodedData);
    }

    getProfile() {
        return this.sendGETRequest("/api/v1/general/profile");
//        setTimeout(() => {}, 3000);
    }

    signout(){
        return this.sendGETRequest("/api/v1/auth/signout");
    }
    
}

export default ConnectionManager;