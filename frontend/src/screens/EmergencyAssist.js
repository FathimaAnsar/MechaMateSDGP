import React, { useState, useEffect } from "react";
import {
  Container,
  Row,
  Col,
  Card,
  Button,
  Form,
  Alert,
} from "react-bootstrap";
import ConnectionManager from "../services/ConnectionManager";
import { Pages } from "../Pages";
import Header from "./components/Header";
import "./styles/ParkingFinder.css";

function EmergencyAssist(props) {
  return (
    <>
      <Header />
      <div>EmergencyAssist</div>
    </>
  );
}

export default EmergencyAssist;
