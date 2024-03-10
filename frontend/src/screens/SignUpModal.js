import React from 'react';
import { Modal, Button, Form, Row, Col, InputGroup } from 'react-bootstrap';
import { Formik } from 'formik';
import * as yup from 'yup';
import ConnectionManager from '../services/ConnectionManager';
import { Pages } from '../Pages';
import { useState } from "react";

function SignUpModal(props) {
  const connection = new ConnectionManager();
  const schema = yup.object().shape({
    firstName: yup.string().required(),
    lastName: yup.string().required(),
    username: yup.string().required(),
    password: yup.string().required(),
    confirmpassword: yup.string().required().oneOf([yup.ref('password'), null], 'Passwords must match'),
    email: yup.string().email().required(),
    telephone: yup.string().required(),
    terms: yup.boolean().oneOf([true], 'Terms must be accepted').required(),
  });

  const handleSubmit = async (values) => {
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
        alert("Success: " + response.message + "\n" + response.info);
        props.app.changePage(Pages.EnterCodeUI);
      } else {
        alert("Error: Unknown");
      }
      props.app.setFirstRunDone(true);
            console.log("Success: " + response.message + "\n" + response.info);
            
            const uProf = await connection.getUserProfile();
            const userProfile = JSON.parse(uProf);

            props.app.setUserProfile(userProfile);
      props.onHide();
      
    } catch (error) {
      console.error("Signup failed:", error);

    }
  };

  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
      scrollable='true'
      // fullscreen='md-down'
    >
      <Modal.Header closeButton >
        <Modal.Title id="contained-modal-title-vcenter">
          <h3>Sign Up</h3>
          {/* <p style={{ fontSize: '10pt', marginBottom: '-10px' }}>It's quick and easy</p> */}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Formik
          validationSchema={schema}
          onSubmit={handleSubmit}
          initialValues={{
            firstName: '',
            lastName: '',
            username: '',
            password: '',
            confirmpassword: '',
            email: '',
            telephone: '',
            terms: false,
          }}
        >
          {({ handleSubmit, handleChange, values, touched, errors }) => (
            <Form noValidate onSubmit={handleSubmit}>
              <Row className="mb-3">
                <Form.Group as={Col} md="6" controlId="validationFormik01" hasValidation>
                  <Form.Label>First name</Form.Label>
                  <Form.Control
                    type="text"
                    name="firstName"
                    value={values.firstName}
                    onChange={handleChange}
                    isValid={touched.firstName && !errors.firstName}
                  />
                  <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                  <Form.Control.Feedback type="invalid">{errors.firstName}</Form.Control.Feedback>

                </Form.Group>
                <Form.Group as={Col} md="6" controlId="validationFormik02" hasValidation>
                  <Form.Label>Last name</Form.Label>
                  <Form.Control
                    type="text"
                    name="lastName"
                    value={values.lastName}
                    onChange={handleChange}
                    isValid={touched.lastName && !errors.lastName}
                  />
                  <Form.Control.Feedback type="invalid">{errors.lastName}</Form.Control.Feedback>
                  <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                </Form.Group>
              </Row>
              <Row className="mb-3">
                <Form.Group as={Col} md="12" controlId="validationFormikUsername" hasValidation>
                  <Form.Label>Username</Form.Label>
                  <InputGroup>
                    <InputGroup.Text id="inputGroupPrepend">@</InputGroup.Text>
                    <Form.Control
                      type="text"
                      placeholder="Username"
                      aria-describedby="inputGroupPrepend"
                      name="username"
                      value={values.username}
                      onChange={handleChange}
                      isInvalid={!!errors.username}
                    />
                    <Form.Control.Feedback type="invalid">{errors.username}</Form.Control.Feedback>
                  </InputGroup>
                </Form.Group>
              </Row>
              <Row className="mb-3">
                <Form.Group as={Col} md="6" controlId="validationFormikPassword" hasValidation>
                  <Form.Label>Password</Form.Label>
                  <Form.Control
                    type="password"
                    name="password"
                    value={values.password}
                    onChange={handleChange}
                    isInvalid={!!errors.password}
                  />
                  <Form.Control.Feedback type="invalid">{errors.password}</Form.Control.Feedback>
                </Form.Group>
                <Form.Group as={Col} md="6" controlId="validationFormikConfirmPassword" hasValidation>
                  <Form.Label>Confirm password</Form.Label>
                  <Form.Control
                    type="password"
                    name="confirmpassword"
                    value={values.confirmpassword}
                    onChange={handleChange}
                    isInvalid={!!errors.confirmpassword}
                  />
                  <Form.Control.Feedback type="invalid">{errors.confirmpassword}</Form.Control.Feedback>
                </Form.Group>
              </Row>
              <Row className="mb-3">
                <Form.Group as={Col} md="6" controlId="validationFormikEmail" hasValidation>
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    type="email"
                    name="email"
                    value={values.email}
                    onChange={handleChange}
                    isInvalid={!!errors.email}
                  />
                  <Form.Control.Feedback type="invalid">{errors.email}</Form.Control.Feedback>
                </Form.Group>
                <Form.Group as={Col} md="6" controlId="validationFormikTelephone" hasValidation>
                  <Form.Label>Telephone</Form.Label>
                  <Form.Control
                    type="text"
                    name="telephone"
                    value={values.telephone}
                    onChange={handleChange}
                    isInvalid={!!errors.telephone}
                  />
                  <Form.Control.Feedback type="invalid">{errors.telephone}</Form.Control.Feedback>
                </Form.Group>
              </Row>
              <Form.Group className="mb-3">
                <Form.Check
                  required
                  name="terms"
                  label="Agree to terms and conditions"
                  onChange={handleChange}
                  isInvalid={!!errors.terms}
                  feedback={errors.terms}
                  feedbackType="invalid"
                  id="validationFormikTerms"
                />
              </Form.Group>
              <Modal.Footer >
                <Button variant='success' type="submit" style={{ width: '100%', fontSize: '12pt',marginBottom:'-10px', fontWeight: '500', borderRadius: '30px', padding: '8px' }}>
                  Sign Up</Button>
              </Modal.Footer>
            </Form>
          )}
        </Formik>
      </Modal.Body>
    </Modal>


  );
}

export default SignUpModal;