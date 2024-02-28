
import { API_BASE_URL } from "./Common.js"


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
            });
            const data = await response.json();
            if (response.ok) {
                return response.body();
            }
        } catch (error) {
            //
        }        
    }

    async sendGETRequest(apiEndPoint) {
        try {
            const response = await fetch(`${API_BASE_URL + apiEndPoint}`, {
                method: 'GET',
                credentials: 'include',
            });
            const data = await response.json();
            if (response.ok) {
                return response.body();
            }
        } catch (error) {
            //
        }        
    }

    signin(username, password) {
        const urlEncodedData = new URLSearchParams();
        urlEncodedData.append('username', username);
        urlEncodedData.append('password', password);
        return this.sendPOSTRequest("/api/v1/auth/signin", "", urlEncodedData);
//        setTimeout(() => {}, 3000);
    }

    getProfile() {
        return this.sendGETRequest("/api/v1/general/profile");
//        setTimeout(() => {}, 3000);
    }
    
}

export default ConnectionManager;