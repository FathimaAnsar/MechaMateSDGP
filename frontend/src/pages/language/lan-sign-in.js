

const getLanguage = (selectedLanguage) => {
    const languages = {
        english: {
            username: 'Username',
            password: 'Password',
            login: 'Login',
            forgottenPassword: 'Forgotten your password?',
            loginError: 'The login details that you\'ve entered doesn\'t match any account.',
            signUpForNewAccount: 'Sign up for an account.',
            dontHaveAnAccount: 'Don\'t have an account?',
            signup: 'Sign up',
            rememberMe: 'Keep me signed in'
        },
        sinhala: {
            username: 'පරිශීලක නාමය',
            password: 'මුරපදය',
            login: 'ඇතුල් වන්න',
            forgottenPassword: 'ඔබගේ මුරපදය අමතකද?',
            loginError: 'ඔබ ඇතුළු කළ ඊමේල් ලිපිනය හෝ මුරපදය වැරදියි.',
            signUpForNewAccount: 'ගිණුමක් සඳහා ලියාපදිංචි වන්න.',
            dontHaveAnAccount: 'ගිණුමක් නොමැතිද?',
            signup: 'ලියාපදිංචි වන්න',
            rememberMe: 'මාව මතක තබා ගන්න'
        },
        tamil: {
            username: 'பயனர் பெயர்',
            password: 'கடவுச்சொல்',
            login: 'உள்நுழைய',
            forgottenPassword: 'உங்கள் கடவுச்சொல்லை மறந்துவிட்டீர்களா?',
            loginError: 'மின்னஞ்சல் முகவரி அல்லது கடவுச்சொல் தவறானது.',
            signUpForNewAccount: 'கணக்கில் பதிவு செய்ய வேணும்.',
            dontHaveAnAccount: 'கணக்கில் இல்லையா?',
            signup: 'பதிவு செய்யவும்',
            rememberMe: 'என்னை உள்நுழைய வைத்திருங்கள்'
        }
    };

    return languages[selectedLanguage];
};

export default getLanguage;
