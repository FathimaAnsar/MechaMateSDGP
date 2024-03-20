import React, { useState, useEffect } from "react";
import { Container, Row, Col, Card, Button, Alert } from "react-bootstrap";
import ConnectionManager from "../services/ConnectionManager";
import { Pages } from "../Pages";
import Header from "./components/Header";

import { Formik, Form, Field } from "formik";

function EmergencyAssist(props) {
  function validateEmail(value) {
    let error;
    if (!value) {
      error = "Required";
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(value)) {
      error = "Invalid email address";
    }
    return error;
  }

  function validateUsername(value) {
    let error;
    if (value === "admin") {
      error = "Nice try!";
    } else if (!value) {
      error = "Required";
    }
    return error;
  }
  return (
    <>
      <Header />
      <div>EmergencyAssist</div>
      <div>
        <h1>Signup</h1>
        <Formik
          initialValues={{
            username: "",
            email: "",
          }}
          onSubmit={(values) => {
            // same shape as initial values
            console.log(values);
          }}
        >
          {({ errors, touched, isValidating }) => (
            <Form>
              <Field name="email" validate={validateEmail} />
              {errors.email && touched.email && <div>{errors.email}</div>}

              <Field name="username" validate={validateUsername} />
              {errors.username && touched.username && (
                <div>{errors.username}</div>
              )}

              <button type="submit">Submit</button>
            </Form>
          )}
        </Formik>
      </div>
      //{" "}
    </>
  );
}

export default EmergencyAssist;
