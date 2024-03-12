
const langList = ["default", "sinhala", "tamil"];



export const sinhala = [
    {tag: "get-started-button", attribute: "", value: "මෙතනින් අරඹන්න"},
    {tag: "user-name-txt", attribute: "", value: "ගැලරිය"},
]; 

export const tamil = [
    {tag: "get-started-button", attribute: "", value: "මෙතනින් අරඹන්න"},
    {tag: "user-name-txt", attribute: "", value: "ගැලරිය"},
]; 




class LanguageManager {


    constructor() {
        this.activeLang = this.getLanguageId;
        this.loadLanguage(true);
    }

    get getLanguageId() {
        let langId = 0;
        try {
            langId = localStorage.getItem("locale");
            if(!langId || !langList.includes(langId)) return langList[0];
            return langId; 
        } catch(exp) {
            return langList[0];
        }
    }

    setLanguageId(langId) {        
        localStorage.setItem("locale", langId);
        this.activeLang = langId;
    }

    loadLanguage(classinit) {
        let langArr, lang, attr;
        if(this.activeLang == this.langList[1]) {
            langArr = sinhala;
        } else if(this.activeLang == this.langList[2]) {
            langArr = tamil;
        } else {
            if(classinit==false) { window.location.reload(); }
            return; 
        }
        langArr.forEach( entry => {
            if(entry.tag.length>0) {
                lang = document.getElementById(entry.tag);
                if(lang!=null) {
                    if(entry.attribute.length<1) {
                        lang.innerText = entry.value;
                    } else {
                        attr = lang.getAttribute(entry.attribute);
                        if(attr!=null) lang.setAttribute(entry.attribute, entry.value);
                    }
                }
            }
        });
    }

}

export default LanguageManager;