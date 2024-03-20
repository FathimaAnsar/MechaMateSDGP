import React, { useState, useEffect } from "react";
import { Pages } from "../Pages.js";
import ConnectionManager from "../services/ConnectionManager.js";
import logo from "../images/logo-black.png";
import "./styles/signIn.css";
import SignUpModal from "./SignUpModal.js";
import { useNavigate } from "react-router-dom";
import LoadingScreen from "./components/LoadingScreen.js";
import { Formik } from "formik";
import * as yup from "yup";
import { Form, Col, InputGroup, Alert, Button, Modal } from "react-bootstrap";
import CustomAlert from "./components/CustomAlert.js";

function SignIn(props) {
  const navigate = useNavigate();
  const [modalShow, setModalShow] = useState(false);
  const [loading, setLoading] = useState(false);
  const [showAlert, setShowAlert] = useState(false);
  const [error, setError] = useState({ message: "", heading: "" });

  const handleSubmit = async (values, { setSubmitting, setErrors }) => {
    const errorMessage = document.getElementById("login-error");
    setLoading(true);

    let connection = new ConnectionManager();

    const resp = await connection.signin(
      values.username,
      values.password,
      values.keepMeSignedIn[0] ? 1 : 0
    );
    const response = JSON.parse(resp);

    if (!response) {
      alert("Please check your springboot localhost is running");
      return;
    }

    if (response.error) {
      if (response.error == "ErrorUserDoesntExist") {
        errorMessage.style.display = "block";
        setErrors({ username: " ", password: " " });
      } else if (response.error == "ErrorLoginFailed") {
        setErrors({ password: "Incorrect Password" });
      } else if (response.error == "ErrorMaxAttemptCountExceeded") {
        setError({
          message: response.help,
          heading: "Too Many Login Attempts",
        });
        setShowAlert(true);
        setErrors({ username: " ", password: " " });
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
  const schema = yup.object().shape({
    username: yup
      .string()
      .required("Username is required")
      .matches(/^[a-zA-Z0-9_.]{5,20}$/, "Invalid Username!"),
    password: yup.string().required("Password is required").matches(
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!)(|}{?><,/.:;\"'$%^&+=])(?=\\S+$).{6,}$",

      "Invalid Password!"
    ),
  });

  const handleClose = () => {
    setShowAlert(false);
  };

  return (
    <>
      {loading && <LoadingScreen />}
      <div id="login-error" className="error-message">
        The login details that you've entered doesn't match any account.{" "}
        <a onClick={() => setModalShow(true)}>Sign up for an new account</a>
      </div>
      <div className="login-container">
        <img src={logo} alt="logo"></img>

        <Formik
          validationSchema={schema}
          onSubmit={handleSubmit}
          initialValues={{
            username: "",
            password: "",
            keepMeSignedIn: false,
          }}
        >
          {({
            handleSubmit,
            handleChange,
            values,
            touched,
            errors,
            setErrors,
          }) => (
            <Form noValidate onSubmit={handleSubmit} id="signup-modal-form">
              <Form.Group as={Col} md="12" hasValidation>
                <InputGroup>
                  <Form.Control
                    id="username-signin"
                    type="text"
                    placeholder="Username"
                    aria-describedby="inputGroupPrepend"
                    name="username"
                    value={values.username}
                    onChange={handleChange}
                    isValid={touched.username && !errors.username}
                    isInvalid={touched.username && errors.username}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.username}
                  </Form.Control.Feedback>
                </InputGroup>
              </Form.Group>

              <Form.Group
                as={Col}
                md="12"
                controlId="validationFormikPassword"
                hasValidation
              >
                {/* <Form.Label id="signup-password">Password</Form.Label> */}
                <Form.Control
                  type="password"
                  name="password"
                  placeholder="Password"
                  value={values.password}
                  onChange={handleChange}
                  isValid={touched.password && !errors.password}
                  isInvalid={touched.password && errors.password}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.password}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className="mb-3 g-3" id="sign-in-check">
                <Form.Check
                  required
                  name="keepMeSignedIn"
                  label="Keep Me Signed In"
                  onChange={handleChange}
                  id="keep-me-signed-in"
                  isValid={touched.terms && !errors.terms}
                  isInvalid={touched.terms && errors.terms}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.terms}
                </Form.Control.Feedback>
              </Form.Group>

              <button type="submit" onClick={handleSubmit}>
                Sign In
              </button>
            </Form>
          )}
        </Formik>

        <div className="forget-password">
          <a
            style={{ color: "#085bd4" }}
            onClick={() => {
              navigate(Pages.ForgotPasswordUI);
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
      <CustomAlert show={showAlert} handleClose={handleClose} error={error} />

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
