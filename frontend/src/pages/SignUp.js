import { useState } from 'react';
import { Form, Button, InputGroup, Row, Col } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import ConnectionManager from '../services/ConnectionManager';

function SignUp() {
  const [validated, setValidated] = useState(false);
  const connectionManager = new ConnectionManager();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.stopPropagation();
    } else {
      const formData = {
        username: form.elements.username.value,
        password: form.elements.password.value,
        email: form.elements.email.value,
        telephone: form.elements.telephone.value,
        firstname: form.elements.firstname.value,
        lastname: form.elements.lastname.value,
        agreedTOS: form.elements.agreedTOS.checked,
      };
      try {
        await connectionManager.signUp(formData);
        // Optionally, handle successful sign-up (e.g., redirect to a new page)
      } catch (error) {
        // Optionally, handle sign-up error (e.g., display error message)
        console.error('Sign-up failed:', error);
      }
    }
    setValidated(true);
  };

  return (
    <>
      <a href="/signin">back</a>
      <div className='signup-container'>
        <h2>Sign Up</h2>
        <Form noValidate validated={validated} onSubmit={handleSubmit}>
          <Row className="mb-3">
            <Form.Group as={Col} controlId="formFirstName">
              <Form.Label>First Name</Form.Label>
              <Form.Control
                name="firstname"
                required
                type="text"
                placeholder="First Name"
              />
              <Form.Control.Feedback type="invalid">
                Please provide a valid first name.
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group as={Col} controlId="formLastName">
              <Form.Label>Last Name</Form.Label>
              <Form.Control
                name="lastname"
                required
                type="text"
                placeholder="Last Name"
              />
              <Form.Control.Feedback type="invalid">
                Please provide a valid last name.
              </Form.Control.Feedback>
            </Form.Group>
          </Row>
          <Form.Group controlId="formEmail">
            <Form.Label>Email</Form.Label>
            <Form.Control
              name="email"
              required
              type="email"
              placeholder="Email"
            />
            <Form.Control.Feedback type="invalid">
              Please provide a valid email address.
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="formPhoneNumber">
            <Form.Label>Telephone Number</Form.Label>
            <InputGroup hasValidation>
              <Form.Control
                name="telephone"
                type="text"
                placeholder="Telephone Number"
                required
              />
              <Form.Control.Feedback type="invalid">
                Please provide a valid telephone number.
              </Form.Control.Feedback>
            </InputGroup>
          </Form.Group>
          <Form.Group controlId="formUsername">
            <Form.Label>Username</Form.Label>
            <InputGroup hasValidation>
              <Form.Control
                name="username"
                type="text"
                placeholder="Username"
                required
              />
              <Form.Control.Feedback type="invalid">
                Please choose a username.
              </Form.Control.Feedback>
            </InputGroup>
          </Form.Group>
          <Form.Group controlId="formPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control
              name="password"
              type="password"
              placeholder="Password"
              required
            />
            <Form.Control.Feedback type="invalid">
              Please provide a valid password.
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="formConfirmPassword">
            <Form.Label>Confirm Password</Form.Label>
            <Form.Control
              name="confirmPassword"
              type="password"
              placeholder="Confirm Password"
              required
            />
            <Form.Control.Feedback type="invalid">
              Passwords must match.
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group className="mb-3" controlId="formAgreeTOS">
            <Form.Check
              name="agreedTOS"
              required
              label="Agree to terms and conditions"
              feedback="You must agree before submitting."
              feedbackType="invalid"
            />
          </Form.Group>
          <Button type="submit" className='signup-btn'>Sign Up</Button>
        </Form>
      </div>
    </>
  );
}

export default SignUp;
