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

  const handleSubmit = async (values) => {
    setLoading(true);
    try {
      const resp = await connection.signup(
        values.username,
        values.password,
        values.email,
        values.firstName,
        values.lastName,
        values.telephone,
        values.terms
      );

      const response = JSON.parse(resp);

      if (!response) {
        alert("Please check your springboot localhost is running");
        return;
      }

      if (response.error) {
        alert("Error occurred: " + response.message + "\n" + response.help);
      } else if (response.status) {
        props.app.setFirstRunDone(true);
        // alert("Success: " + response.message + "\n" + response.info);
        navigate("/" + Pages.EnterCodeUI);
      } else {
        alert("Error: Unknown");
      }
      props.app.setFirstRunDone(true);
      console.log("Success: " + response.message + "\n" + response.info);

      const uProf = await connection.getUserProfile();
      const userProfile = JSON.parse(uProf);
      props.onHide();
    } catch (error) {
      console.error("Signup failed:", error);
    }
    setLoading(false);
  };

  const schema = yup.object().shape({
    firstName: yup.string().required(),
    lastName: yup.string().required(),
    username: yup.string().required(),
    password: yup.string().required(),
    confirmpassword: yup
      .string()
      .required()
      .oneOf([yup.ref("password"), null], "Passwords must match"),
    email: yup.string().email().required(),
    telephone: yup.string().required().min(9),
    terms: yup.boolean().oneOf([true], "Terms must be accepted").required(),
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
          }}
        >
          {({ handleSubmit, handleChange, values, touched, errors }) => (
            <Form noValidate onSubmit={handleSubmit} id="signup-modal-form">
              <Row className="mb-3 g-3">
                <Form.Group as={Col} md="6" hasValidation>
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
                <Form.Group as={Col} md="6" hasValidation>
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
                    <InputGroup.Text id="inputGroupPrepend">@</InputGroup.Text>
                    <Form.Control
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
                  md="6"
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
                <Form.Group
                  as={Col}
                  md="6"
                  controlId="validationFormikConfirmPassword"
                  hasValidation
                >
                  {/* <Form.Label id="signup-confpassword">
                    Confirm password
                  </Form.Label> */}
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
                  md="6"
                  controlId="validationFormikEmail"
                  hasValidation
                >
                  {/* <Form.Label id="signup-email">Email</Form.Label> */}
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
                <Form.Group
                  as={Col}
                  md="6"
                  controlId="validationFormikTelephone"
                  hasValidation
                >
                  {/* <Form.Label id="signup-telephone">Telephone</Form.Label> */}
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
              </Row>
              <Form.Group className="mb-3 g-3">
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
      </Modal.Body>
    </Modal>
  );
}

export default SignUpModal;
