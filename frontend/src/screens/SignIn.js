import React, { useState, useEffect } from "react";
import { Pages } from "../Pages.js";
import ConnectionManager from "../services/ConnectionManager.js";
import logo from "../images/logo-black.png";
import "./styles/signIn.css";
import SignUpModal from "./SignUpModal.js";
import { useNavigate } from "react-router-dom";
import LoadingScreen from "./components/LoadingScreen.js";

function SignIn(props) {
  const navigate = useNavigate();
  const [modalShow, setModalShow] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleClick = (page) => {
    navigate("/" + page);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const keepMeSignedIn = document.getElementById("keep-me-signed-in").checked;

    const errorMessage = document.getElementById("login-error");
    const loginContainer = document.getElementById("login-container");

    // Validate inputs
    // If failed to validate, return here

    let connection = new ConnectionManager();

    const resp = await connection.signin(
      username,
      password,
      keepMeSignedIn ? 1 : 0
    );
    const response = JSON.parse(resp);

    if (!response) {
      alert("Please check your springboot localhost is running");
      return;
    }

    if (response.error) {
      if (response.error == "ErrorUserDoesntExist") {
        errorMessage.style.display = "block";
      }
      console.log("Error occured: " + response.message + "\n" + response.help);
    } else if (response.status) {
      props.app.setFirstRunDone(true);
      console.log("Success: " + response.message + "\n" + response.info);

      const uProf = await connection.getUserProfile();
      const userProfile = JSON.parse(uProf);

      if (!userProfile) {
        alert("Please check your springboot localhost is running");
        return;
      }

      if (userProfile.error) {
        if (userProfile.error === "ErrorPendingActivation") {
          navigate("/" + Pages.EnterCodeUI);
        } else {
          alert(userProfile.message + "\n" + userProfile.help);
        }
      } else {
        errorMessage.style.backgroundColor = "#45ab26";
        errorMessage.style.display = "block";
        errorMessage.innerHTML = response.message;
        errorMessage.style.height = "auto";
        props.app.setUserProfile(userProfile);
        navigate("/" + Pages.DashboardUI);
      }
    } else {
      alert("Error: Unknown");
    }
    setLoading(false);
  };

  useEffect(() => {
    try {
      let connection = new ConnectionManager();
      connection.getUserProfile().then((uProf) => {
        const userProfile = JSON.parse(uProf);

        if (!userProfile) {
          alert("Please check your springboot localhost is running");
          return;
        }

        if (userProfile.error) {
          if (userProfile.error === "ErrorPendingActivation") {
            navigate("/" + Pages.EnterCodeUI);
          } else if (userProfile.error === "ErrorNotSignedIn") {
            //
          } else {
            alert(userProfile.message + "\n" + userProfile.help);
          }
        } else {
          props.app.setUserProfile(userProfile);
          navigate("/" + Pages.DashboardUI);
        }
      });
    } catch (exp) {}
  }, []);

  return (
    <>
      {loading && <LoadingScreen />}
      <div id="login-error" className="error-message">
        The login details that you've entered doesn't match any account.{" "}
        <a onClick={() => setModalShow(true)}>Sign up for an new account</a>
      </div>
      <div className="login-container">
        <img src={logo} alt="logo"></img>

        <form action="#" method="post">
          <input
            type="text"
            id="username"
            name="username"
            placeholder="Username"
          />
          <input
            type="password"
            id="password"
            name="password"
            placeholder="Password"
          />

          <label htmlFor="keep-me-signed-in">
            <input
              type="checkbox"
              id="keep-me-signed-in"
              name="already_signed_in"
            />
            Keep Me Signed In
          </label>

          <button onClick={handleSubmit}>Sign In</button>
        </form>
        <div className="forget-password">
          <a
            style={{ color: "#085bd4" }}
            onClick={() => {
              handleClick(Pages.ForgotPasswordUI);
            }}
          >
            Forgotten password?
          </a>
        </div>
        <div className="sign-up">
          Don't have an account?{" "}
          <a style={{ color: "#085bd4" }} onClick={() => setModalShow(true)}>
            sign up
          </a>
        </div>
      </div>
      <SignUpModal
        app={props.app}
        show={modalShow}
        onHide={() => {
          setModalShow(false);
          if (props.app.getUserProfile()) {
            navigate("/" + Pages.EnterCodeUI);
          }
        }}
      />
      {/* <button onClick={() => { handleClick(Pages.SignUpUI) }}>Dont you have an account?</button>
            <br></br>
            <button onClick={() => { handleClick(Pages.ForgotPasswordUI) }}>Forgot Password?</button>
            <br></br> */}
      <div className="login-footer">
        <div className="languages">
          <button
            id="bt-sinhala"
            // className={activeLanguage === 'sinhala' ? 'active' : ''}
            // onClick={() => handleLanguageChange('sinhala')}
          >
            සිංහල
          </button>
          <button
            id="bt-en"
            // className={activeLanguage === 'english' ? 'active' : ''}
            // onClick={() => handleLanguageChange('english')}
          >
            English
          </button>
          <button
            id="bt-tamil"
            // className={activeLanguage === 'tamil' ? 'active' : ''}
            // onClick={() => handleLanguageChange('tamil')}
          >
            தமிழ்
          </button>
        </div>
        <div className="copyright">MechaMate © 2024</div>
      </div>
    </>
  );
}

export default SignIn;
