
import ConnectionManager from "./ConnectionManager.js"
import { Pages } from "./Pages.js" 

class MechaMate {

    constructor() {
        this.currentHistoryIndex = 0;
        this.history = [Pages.GetStartedUI];
        this.currentPage = Pages.GetStartedUI;    
        this.refreshStateCaller = null;
    }



    isFirstRunDone() {
        return localStorage.getItem("isFirstRunDone") == 1;
    }

    setFirstRunDone(value) {
        localStorage.setItem("isFirstRunDone", (value == true ? 1 : 0));
    }

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


    reset() {
        localStorage.clear();
    }

    getUserProfile() {
        let userProfile = localStorage.getItem("userProfile");
        return (!userProfile ? userProfile : null);
    }



}

export const main = new MechaMate(); 