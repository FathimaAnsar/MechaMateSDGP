
const langList = ["default", "sinhala", "tamil"];

export class LanguageManager {


    constructor() {
        this.activeLang = this.getLanguageId;
        if(this.activeLang.length>=0 && this.activeLang<=2) {
            document.getElementById("lang-select").selectedIndex = this.activeLang;
        }
        this.loadLanguage(true);
    }

    get getLanguageId() {
        let langId = 0;
        try {
            langId = localStorage.getItem("locale");
            return langId; 
        } catch(exp) {
            return langId;
        }
    }

    setLanguageId(langId) {
        localStorage.setItem("locale", langId);
        this.activeLang = langId;
    }

    loadLanguage(classinit) {
        let langArr, lang, attr;
        if(this.activeLang==1) {
            langArr = sinhala;
        } else if(this.activeLang==2) {
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

