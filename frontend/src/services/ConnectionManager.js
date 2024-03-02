
import { API_BASE_URL } from "../Common.js"


class ConnectionManager {

    constructor() {

    }

    async postParamRequest(apiEndPoint, postParams) {
        document.body.style.cursor = 'wait';
        try {
            const response = await fetch(`${API_BASE_URL + apiEndPoint}`, {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: postParams,
                credentials: 'include',
            });
            const data = await response.json();
            document.body.style.cursor = 'default';
            return JSON.stringify(data);            
        } catch (error) {}
        document.body.style.cursor = 'default';
        return null;
    }

    async postJsonRequest(apiEndPoint, jsonObject) {
        document.body.style.cursor = 'wait';
        try {
            const response = await fetch(`${API_BASE_URL + apiEndPoint}`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: jsonObject,
                credentials: 'include',
            });
            const data = await response.json();
            document.body.style.cursor = 'default';
            return JSON.stringify(data);            
        } catch (error) {}
        document.body.style.cursor = 'default';
        return null;
    }

    async getRequest(apiEndPoint,  urlEncodedData = null) {
        document.body.style.cursor = 'wait';
        try {
            const response = await fetch(`${API_BASE_URL + ((urlEncodedData == null) ? apiEndPoint : apiEndPoint + "?" + urlEncodedData) }`, {
                method: 'GET',
                credentials: 'include',
            });
            const data = await response.json();
            document.body.style.cursor = 'default';
            return JSON.stringify(data);            
        } catch (error) {}
        document.body.style.cursor = 'default';
        return null;
    }

    async signin(username, password) {
        const urlEncodedData = new URLSearchParams();
        urlEncodedData.append('username', username);
        urlEncodedData.append('password', password);
        return await this.postParamRequest("/api/v1/auth/signin", urlEncodedData);
    }

    async signup(username, password, email, firstName, lastName, telephone, agreedTOS) {
        const urlEncodedData = new URLSearchParams();
        urlEncodedData.append('username', username);
        urlEncodedData.append('password', password);
        urlEncodedData.append('email', email);
        urlEncodedData.append('firstname', firstName);
        urlEncodedData.append('lastname', lastName);
        urlEncodedData.append('telephone', telephone);
        urlEncodedData.append('agreedTOS', agreedTOS);
        return await this.postParamRequest("/api/v1/auth/signup", urlEncodedData);
    }

    async activate(code) {
        return await this.getRequest("/api/v1/auth/activate?key=" + code);
    }

    async signout() {
        return await this.getRequest("/api/v1/auth/signout");
    }

    async requestPasswordRecovery(email) {
        const urlEncodedData = new URLSearchParams();
        urlEncodedData.append('email', email);    
        return await this.postParamRequest("/api/v1/auth/recover", urlEncodedData);
    }

    async getUserProfile() {
        return await this.getRequest("/api/v1/general/profile");
    }

    async getDetailedUserProfile() {
        return await this.getRequest("/api/v1/general/detailed-profile");
    }

    async getNearbyParking(lat, lng, radius, limit) {
        return await this.getRequest("/api/v1/features/get-nearby-parking?lat=" + lat + "&lng=" + lng + "&radius=" + radius + "&limit=" + limit);
    }
    
}

export default ConnectionManager;