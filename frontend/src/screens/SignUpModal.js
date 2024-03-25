import React, { useState } from "react";
import {
  Modal,
  Button,
  Form,
  Row,
  Col,
  InputGroup,
  Spinner,
} from "react-bootstrap";
import { Formik } from "formik";
import * as yup from "yup";
import ConnectionManager from "../services/ConnectionManager";
import { Pages } from "../Pages";
import { Link, useNavigate } from "react-router-dom";
import "./styles/signIn.css";

function SignUpModal(props) {
  const navigate = useNavigate();
  const connection = new ConnectionManager();
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (values, { setSubmitting, setErrors }) => {
    setLoading(true);
    try {
      const resp = await connection.signup(
        values.username,
        values.password,
        values.email,
        values.firstName,
        values.lastName,
        values.telephone,
        values.terms,
        values.userType
      );

      const response = JSON.parse(resp);

      if (!response) {
        return;
      }

      if (response.error) {
        if (response.error == "ErrorUserExists") {
          setErrors({ username: "This username is already taken" });
        } else if (response.error == "ErrorEmailExists") {
          setErrors({ email: "Email is already in use" });
        }
      } else if (response.status) {
        props.app.setFirstRunDone(true);
        navigate("/" + Pages.EnterCodeUI);
        props.onHide();
      } else {
        console.log(response.error);
      }
      props.app.setFirstRunDone(true);
      console.log("Success: " + response.message + "\n" + response.info);

      const uProf = await connection.getUserProfile();
      const userProfile = JSON.parse(uProf);
    } catch (error) {
      console.error("Signup failed:", error);
    }
    setLoading(false);
  };

  const schema = yup.object().shape({
    firstName: yup
      .string()
      .required("First name is required")
      .matches("^[a-zA-Z]*$", "Invalid Username"),
    lastName: yup
      .string()
      .required("Lastname is required")
      .matches("^[a-zA-Z]*$", "Invalid Lastname"),
    username: yup
      .string()
      .required("Username is required")
      .matches(
        "^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$",
        "Invalid Username"
      ),
    password: yup
      .string()
      .required("Password is required")
      .matches(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!)(|}{?><,/.:;\"'$%^&+=])(?=\\S+$).{6,}$",
        "Invalid Password!"
      ),

    confirmpassword: yup
      .string()
      .required("Required field")
      .oneOf([yup.ref("password"), null], "Passwords must match"),
    email: yup
      .string()
      .email()
      .required("Email is required")
      .matches(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,})?$",
        "Invalid Email!"
      ),
    telephone: yup
      .string()
      .required("Telephone is required")
      .matches(
        "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}",
        "Invalid telephone number!"
      ),

    terms: yup.boolean().oneOf([true], "Terms must be accepted").required(),
    userType: yup.boolean().required(),
  });

  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
      scrollable="true"
    // fullscreen='md-down'
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-center">
          <h3 id="signup-heading">Sign Up</h3>
          <p id="signup-heading-text">It's quick and easy</p>
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Formik
          validationSchema={schema}
          onSubmit={handleSubmit}
          initialValues={{
            firstName: "",
            lastName: "",
            username: "",
            password: "",
            confirmpassword: "",
            email: "",
            telephone: "",
            terms: false,
            userType: false
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
              <Row className="mb-3 g-3">
                <Form.Group as={Col} sm="6" hasValidation>
                  <Form.Control
                    type="text"
                    name="firstName"
                    placeholder="First Name"
                    value={values.firstName}
                    onChange={handleChange}
                    isValid={touched.firstName && !errors.firstName}
                    isInvalid={touched.firstName && errors.firstName}
                  />

                  <Form.Control.Feedback type="invalid">
                    First name is a required field
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group as={Col} sm="6" hasValidation>
                  {/* <Form.Label id="signup-lname">Last name</Form.Label> */}
                  <Form.Control
                    placeholder="Last Name"
                    type="text"
                    name="lastName"
                    value={values.lastName}
                    onChange={handleChange}
                    isValid={touched.lastName && !errors.lastName}
                    isInvalid={touched.lastName && errors.lastName}
                  />

                  <Form.Control.Feedback type="invalid">
                    Last name is a required field
                  </Form.Control.Feedback>
                </Form.Group>
              </Row>
              <Row className="mb-3 g-3">
                <Form.Group as={Col} md="12" hasValidation>
                  {/* <Form.Label id="signup-username">Username</Form.Label> */}
                  <InputGroup>
                    <InputGroup.Text
                      id="inputGroupPrepend"
                      style={{ borderRadius: "10px 0 0 10px", height: 'auto' }}
                    >
                      @
                    </InputGroup.Text>
                    <Form.Control
                      style={{ borderRadius: "0 10px 10px 0" }}
                      id="inputGroupUsername"
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
              </Row>
              <Row className="mb-3 g-3">
                <Form.Group
                  as={Col}
                  sm="6"
                  controlId="validationFormikPassword"
                  hasValidation
                >
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
                <Form.Group
                  as={Col}
                  sm="6"
                  controlId="validationFormikConfirmPassword"
                  hasValidation
                >

                  <Form.Control
                    type="password"
                    name="confirmpassword"
                    placeholder="Confirm Password"
                    value={values.confirmpassword}
                    onChange={handleChange}
                    isValid={touched.confirmpassword && !errors.confirmpassword}
                    isInvalid={
                      touched.confirmpassword && errors.confirmpassword
                    }
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.confirmpassword}
                  </Form.Control.Feedback>
                </Form.Group>
              </Row>
              <Row className="mb-3 g-3">
                <Form.Group
                  as={Col}
                  sm="12"
                  controlId="validationFormikEmail"
                  hasValidation
                >
                  <Form.Control
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={values.email}
                    onChange={handleChange}
                    isValid={touched.email && !errors.email}
                    isInvalid={touched.email && errors.email}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.email}
                  </Form.Control.Feedback>
                </Form.Group>
              </Row>
              <Row>
                <Form.Group
                  as={Col}
                  sm="6"
                  controlId="validationFormikTelephone"
                  hasValidation
                >
                  <Form.Control
                    type="text"
                    name="telephone"
                    placeholder="Telephone"
                    value={values.telephone}
                    onChange={handleChange}
                    isValid={touched.telephone && !errors.telephone}
                    isInvalid={touched.telephone && errors.telephone}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.telephone}
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group as={Col}
                  sm="6" >
                  <Form.Select
                    id="dropdown-signup-modal"
                    required
                    name="userType"
                    onChange={handleChange}
                    isValid={touched.userType && !errors.userType}
                    isInvalid={touched.userType && errors.userType}
                    defaultValue={false}
                  >
                    <option value={false}>User</option>
                    <option value={true}>Service Provider</option>
                  </Form.Select>
                  <Form.Control.Feedback type="invalid">
                    {errors.userType}
                  </Form.Control.Feedback>
                </Form.Group>
              </Row>
              <Row>
                <Form.Group as={Col}
                  sm="12" className="mb-3 g-3">
                  <Form.Check
                    required
                    name="terms"
                    label="Agree to terms and conditions"
                    onChange={handleChange}
                    id="validationFormikTerms"
                    isValid={touched.terms && !errors.terms}
                    isInvalid={touched.terms && errors.terms}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.terms}
                  </Form.Control.Feedback>
                </Form.Group>
              </Row>

              <div
                className=".sign-in-terms"
                style={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "space-evenly",
                  fontSize: "14px",
                  marginBottom: "5px",
                }}
              >
                <Link
                  to={"/" + Pages.PrivacyPolicyUI}
                  style={{ color: "gray", textDecoration: "none" }}
                >
                  Privacy Policy
                </Link>
                <Link
                  to={"/" + Pages.TermsUI}
                  style={{ color: "gray", textDecoration: "none" }}
                >
                  Terms
                </Link>
                <Link
                  to={"/" + Pages.CookiesPolicyUI}
                  style={{ color: "gray", textDecoration: "none" }}
                >
                  Cookies Policy
                </Link>
              </div>

              <Modal.Footer>
                <Button
                  variant="success"
                  type="submit"
                  style={{
                    width: "100%",
                    fontSize: "12pt",
                    marginBottom: "-10px",
                    fontWeight: "500",
                    borderRadius: "30px",
                    padding: "8px",
                  }}
                >
                  {loading ? (
                    <Spinner
                      as="span"
                      animation="border"
                      size="sm"
                      role="status"
                      aria-hidden="true"
                    />
                  ) : (
                    <>Sign Up</>
                  )}
                </Button>
              </Modal.Footer>
            </Form>
          )}
        </Formik>
      </Modal.Body >
    </Modal >
  );
}

export default SignUpModal;
