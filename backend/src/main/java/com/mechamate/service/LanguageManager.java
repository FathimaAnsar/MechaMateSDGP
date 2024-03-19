package com.mechamate.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageManager {

    @Autowired
    private Environment env;

    private final List<String> supported = new ArrayList<>();

    LanguageManager() {
        supported.add("default");
        supported.add("sinhala");
        supported.add("tamil");
    }

    public String getLanguage(HttpSession session) {
        try {
            String language = "";
            if(session != null) {
                try {
                    language = (String) session.getAttribute("locale");
                    if(language == null) language = "";
                } catch (Exception e) {
                    //
                }
            }
            if(!supported.contains(language)) language = supported.get(0);
            return language;
        } catch (Exception e) {
            //
        }
        return supported.get(0);
    }


    public String get(String key, String language) {
        try {
            String s = env.getProperty("lang." + (supported.contains(language) ? language : supported.get(0)) + "." + key);
            return (s == null) ? "": s;
        } catch (Exception e) {
            //
        }
        return "";
    }

    public String get(String key, HttpSession session) {
        try {
            String language = "";
            if(session != null) {
                try {
                    language = (String) session.getAttribute("locale");
                    if(language == null) language = "";
                } catch (Exception e) {
                    //
                }
            }
            if(!supported.contains(language)) language = supported.get(0);
            String s = env.getProperty("lang." + (supported.contains(language) ? language : supported.get(0)) + "." + key);
            return (s == null) ? "": s;
        } catch (Exception e) {
            //
        }
        return "";
    }

    public String getFilledWith(String key, String language, String[] values) {
        try {
            String s = env.getProperty("lang." + (supported.contains(language) ? language : supported.get(0)) + "." + key);
            if(s == null) return "";
            if(values == null) return s;
            if(values.length > 0) {
                for(int i = 0; i < values.length; i++) {
                    s = s.replace("[REPLACE" + (i + 1) + "]", values[i]);
                }
            }
            return s;
        } catch (Exception e) {
            //
        }
        return "";
    }


    public String getFilledWith(String key, HttpSession session, String[] values) {
        try {
            String language = "";
            if(session != null) {
                try {
                    language = (String) session.getAttribute("locale");
                    if(language == null) language = "";
                } catch (Exception e) {
                    //
                }
            }
            if(!supported.contains(language)) language = supported.get(0);
            String s = env.getProperty("lang." + (supported.contains(language) ? language : supported.get(0)) + "." + key);
            if(s == null) return "";
            if(values == null) return s;
            if(values.length > 0) {
                for(int i = 0; i < values.length; i++) {
                    s = s.replace("[REPLACE" + (i + 1) + "]", values[i]);
                }
            }
            return s;
        } catch (Exception e) {
            //
        }
        return "";
    }

}
