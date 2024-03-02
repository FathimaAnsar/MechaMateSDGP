import React, { useState, useEffect, useRef } from "react";
import ConnectionManager from "../services/ConnectionManager.js";
import { Pages } from "../Pages.js";

function EnterCode(props) {
    const handleGoBack = () => props.app.goBack();

    useEffect(() => {
        try {
            let connection = new ConnectionManager();
            connection.getUserProfile().then((uProf) => {
                const userProfile = JSON.parse(uProf);


                if(!userProfile) {
                    alert("Please check your springboot localhost is running");
                    return;
                }
        

                if (userProfile.error) {
                    if (userProfile.error === "ErrorPendingActivation") {
                        //
                    } else if (userProfile.error === "ErrorNotSignedIn") {
                        props.app.changePage(Pages.SignInUI);
                    } else {
                        alert(userProfile.message + "\n" + userProfile.help);
                    }
                } else {
                    props.app.changePage(Pages.DashboardUI);
                }
            });
        } catch (exp) {}
    }, []);

    const onOtpSubmit = async (otp) => {
        // Call the activation logic here using the OTP
        try {
            let connection = new ConnectionManager();
            const resp = await connection.activate(otp);
            const response = JSON.parse(resp);

            if(!response) {
                alert("Please check your springboot localhost is running");
                return;
            }
    

            if (response.error) {
                alert("Error occurred: " + response.message + "\n" + response.help);
            } else if (response.status) {
                alert("Success: " + response.message + "\n" + response.info);
                const uProf = await connection.getUserProfile();
                const userProfile = JSON.parse(uProf);


                if(!userProfile) {
                    alert("Please check your springboot localhost is running");
                    return;
                }
        

                if (userProfile.error) {
                    alert(userProfile.message + "\n" + userProfile.help);
                    props.app.changePage(Pages.SignInUI);
                } else {
                    props.app.setUserProfile(userProfile);
                    props.app.changePage(Pages.DashboardUI);
                }
            } else {
                alert("Error: Unknown");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <>
            <button onClick={handleGoBack}>Go Back</button>

            <h1>Account Activation</h1>

            <p>Please enter the 6-digit activation code we sent to your email</p>

            <OtpInput length={6} onOtpSubmit={onOtpSubmit} />
        </>
    );
}

const OtpInput = ({ length = 6, onOtpSubmit = () => {} }) => {
    const [otp, setOtp] = useState(new Array(length).fill(""));
    const inputRefs = useRef([]);

    useEffect(() => {
        if (inputRefs.current[0]) {
            inputRefs.current[0].focus();
        }
    }, []);

    const handleChange = (index, e) => {
        const value = e.target.value;
        if (isNaN(value)) return;

        const newOtp = [...otp];
        newOtp[index] = value.substring(value.length - 1);
        setOtp(newOtp);

        const combinedOtp = newOtp.join("");
        if (combinedOtp.length === length) onOtpSubmit(combinedOtp);

        if (value && index < length - 1 && inputRefs.current[index + 1]) {
            inputRefs.current[index + 1].focus();
        }
    };

    const handleClick = (index) => {
        inputRefs.current[index].setSelectionRange(1, 1);
        if (index > 0 && !otp[index - 1]) {
            inputRefs.current[otp.indexOf("")].focus();
        }
    };

    const handleKeyDown = (index, e) => {
        if (e.key === "Backspace" && !otp[index] && index > 0 && inputRefs.current[index - 1]) {
            inputRefs.current[index - 1].focus();
        }
    };

    return (
        <div>
            {otp.map((value, index) => (
                <input
                    key={index}
                    type="text"
                    placeholder={index+1}
                    ref={(input) => (inputRefs.current[index] = input)}
                    value={value}
                    onChange={(e) => handleChange(index, e)}
                    onClick={() => handleClick(index)}
                    onKeyDown={(e) => handleKeyDown(index, e)}
                    className="otpInput"
                />
            ))}
        </div>
    );
};

export default EnterCode;
