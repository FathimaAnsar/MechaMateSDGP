
import ConnectionManager from "./services/ConnectionManager.js"

class MechaMate {
    constructor() {
        this.connection = new ConnectionManager();
        this.appLoaded = false;
    }

    isAppLoaded() { return this.appLoaded; }
    setAppLoaded(value) { this.appLoaded = value; }


    async isAlreadySignedIn() {
        const resp = await this.connection.signin("username", "password", 0);
        const response = JSON.parse(resp);
        try {
            return (response.status === "AlreadySignedIn");
        } catch(exp) {}
        return false;
    }

    async isPendingActivation() {
        const resp = await this.connection.getUserProfile();
        const response = JSON.parse(resp);
        try {
            return (response.error === "ErrorPendingActivation");
        } catch(exp) {}
        return false;
    }

    isFirstRunDone() { return localStorage.getItem("isFirstRunDone") == 1; }
    setFirstRunDone(value) { localStorage.setItem("isFirstRunDone", (value ? 1 : 0)); }


    setRefreshCaller(refreshStateCaller) {
        return;
    }
    goBack() {
        return;
    }

    goForward() {
        return;
    }

    changePage(newPage) {
        return;
    }

    clearSessionCache() {
        localStorage.removeItem("userProfile");
        localStorage.removeItem("vehicleList");
    }

    reset() {
        localStorage.clear();
    }

    getUserProfile() {
        try {
            let userProfile = JSON.parse(localStorage.getItem("userProfile"));
            return (userProfile ? userProfile : null);
        } catch(exp) {}
        return null;
    }

    setUserProfile(userProfile) {
        try {
            localStorage.setItem("userProfile", JSON.stringify(userProfile));
        } catch(exp) {}
    }

    getVehicleList() {
        try {
            let vehicleList = JSON.parse(localStorage.getItem("vehicleList"));
            return (vehicleList ? vehicleList : null);
        } catch(exp) {}
    }
      

    setVehicleList(vehicleList) {
        try {
            localStorage.setItem("vehicleList", JSON.stringify(vehicleList));
        } catch(exp) {}
    }

    getCurrentLocation() {
        return new Promise((resolve, reject) => {
            if ("geolocation" in navigator) {
                navigator.geolocation.getCurrentPosition(
                    position => {
                        const latitude = position.coords.latitude;
                        const longitude = position.coords.longitude;
                        resolve({ longitude, latitude });
                    },
                    error => {
                        reject(error);
                    }
                );
            } else {
                reject("Geolocation is not supported by this browser");
            }
        });
    }

}

export const main = new MechaMate(); 


