
import ConnectionManager from "./services/ConnectionManager.js"
import { Pages } from "./Pages.js" 

class MechaMate {

    constructor() {
        this.currentHistoryIndex = 0;
        this.history = [Pages.DashboardUI];
        this.currentPageInternal = Pages.DashboardUI;    
        this.refreshStateCaller = null;
        this.connection = new ConnectionManager();
        this.appLoaded = false;
    }

    isAppLoaded() { return this.appLoaded; }
    setAppLoaded(value) { this.appLoaded = value; }

    get currentPage() { return this.currentPageInternal; }
    set currentPage(newPage) { 
        if(this.history.length > 0) {
            if(this.currentHistoryIndex >= 0 && this.currentHistoryIndex < (this.history.length - 1)) {
                this.history.splice(this.currentHistoryIndex + 1);
            }            
            if(this.history[this.history.length - 1] !== newPage) this.history.push(newPage);
        } else {
            this.history.push(newPage);
        }
        if(this.history.length > 10) this.history.splice(0, 1);
        this.currentHistoryIndex = this.history.length - 1;
        this.currentPageInternal = newPage;
    }

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
        this.refreshStateCaller = refreshStateCaller;
    }

    goBack() {
        if(this.currentHistoryIndex >= 0 && this.currentHistoryIndex < this.history.length) {
            if(this.currentHistoryIndex > 0) {
                this.currentHistoryIndex--;
                this.currentPage = this.history[this.currentHistoryIndex];
                if(this.refreshStateCaller) this.refreshStateCaller();
            }
        }        
    }

    goForward() {
        if(this.currentHistoryIndex >= 0 && this.currentHistoryIndex < this.history.length) {
            if(this.currentHistoryIndex < this.history.length - 1) {
                this.currentHistoryIndex++;
                this.currentPage = this.history[this.currentHistoryIndex];
                if(this.refreshStateCaller) this.refreshStateCaller();
            }
        }        
    }

    changePage(newPage) {
        if(this.history.length > 0) {
            if(this.currentHistoryIndex >= 0 && this.currentHistoryIndex < (this.history.length - 1)) {
                this.history.splice(this.currentHistoryIndex + 1);
            }            
            if(this.history[this.history.length - 1] !== newPage) this.history.push(newPage);
        } else {
            this.history.push(newPage);
        }
        if(this.history.length > 10) this.history.splice(0, 1);
        this.currentHistoryIndex = this.history.length - 1;
        this.currentPage = newPage;
        if(this.refreshStateCaller) this.refreshStateCaller();
    }

    clearSessionCache() {
        localStorage.removeItem("userProfile");
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



}

export const main = new MechaMate(); 