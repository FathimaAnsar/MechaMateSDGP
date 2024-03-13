
const langList = ["default", "sinhala", "tamil"];

//Sinhala translations

export const sinhala = [
    {tag: "get-started-button", attribute: "", value: "මෙතනින් අරඹන්න"},
    {tag: "user-name-txt", attribute: "", value: "ගැලරිය"},

    //dashboard 
    {tag: "dash-vehicle-heading", attribute: "", value: "වාහන"},
    {tag: "dash-vehicle-p1", attribute: "", value: "ඔබ තවම වාහන කිසිවක් එකතු කර නැත"},
    {tag: "dash-add-vehi-btn", attribute: "", value: "වාහනයක් එකතු කරන්න"},

    {tag: "dash-autoS-heading", attribute: "", value: "අලුත් වැඩියා ස්ථානයක් "},
    {tag: "dash-autS_btn", attribute: "", value: "සොයන්න"},

    {tag: "dash-pred-heading", attribute: "", value: "නඩත්තු අනාවැකිය"},
    {tag: "dash-pred_btn", attribute: "", value: "අනාවැකිය බලන්න"},

    {tag: "dash-track-heading", attribute: "", value: "වාහනය නිරීක්ෂණය"},
    {tag: "dash-track_btn", attribute: "", value: "වාහනය නිරීක්ෂණය කරන්න "},

    {tag: "dash-emergen-heading", attribute: "", value: "ආපදා සහකරු"},
    {tag: "dash-emergen_btn", attribute: "", value: "ආපදා සහකරු ක්‍රියාත්මක කරන්න"},

    {tag: "dash-doc-heading", attribute: "", value: "වාහන ලේඛන කළමනාකරණය"},
    {tag: "dash-doc_btn", attribute: "", value: "ලේඛන විවෘත කරන්න"},

    {tag: "dash-park-heading", attribute: "", value: "රථ ගාලක් සොයන්න"},
    {tag: "dash-park_btn", attribute: "", value: "සෙවීමට පිවිසෙන්න"},
]; 


//Tamil Translations

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