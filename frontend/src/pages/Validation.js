const validateForm = () => {
        const usernameInput = document.getElementById("username");
        const passwordInput = document.getElementById("password");

        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();

        let isValid = true;

        if (username === "") {
            usernameInput.style.border = "1px solid red";
            isValid = false;
        } else {
            usernameInput.style.border = "";
        }

        if (password === "") {
            passwordInput.style.border = "1px solid red";
            isValid = false;
        } else {
            passwordInput.style.border = "";
        }

        const errorMessage = document.getElementById("login-error");


        
        if (!isValid) {
            errorMessage.style.display = "block";
        } else {
            errorMessage.style.display = "none";
        }

        return isValid;
    }

export default validateForm;