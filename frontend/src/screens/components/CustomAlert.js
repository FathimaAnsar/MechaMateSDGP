import React from "react";
import { Modal, Alert } from "react-bootstrap";

const CustomAlert = ({ show, handleClose, error, variant = "danger" }) => (
  <Modal
    show={show}
    size="md"
    aria-labelledby="contained-modal-title-vcenter"
    style={{ padding: "0", margin: "0", outline: "none" }}
    // centered
  >
    <Alert
      show={show}
      variant={variant}
      onClose={handleClose}
      style={{ margin: "0" }}
      dismissible
    >
      <Alert.Heading>{error.heading}</Alert.Heading>
      <hr />
      <p>{error.message}</p>
    </Alert>
  </Modal>
);

export default CustomAlert;
