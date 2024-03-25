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
import { FaUser, FaStar } from 'react-icons/fa';
import ConnectionManager from "../services/ConnectionManager";
import { Pages } from "../Pages";
import Header from "./components/Header";
import { useNavigate } from "react-router-dom";

function BreakdownAssist(props) {

  const navigate = useNavigate();

  const [selectedCard, setSelectedCard] = useState(null);
  const [currentLocation, setCurrentLocation] = useState(null);

  const [parkingSpaces, setParkingSpaces] = useState([]);
  const [varCap, setVarCap] = useState("");
  const [loading, setLoading] = useState(false);
  const [alertInfo, setAlertInfo] = useState({ show: false, message: "" });
  const [selectedMapUri, setSelectedMapUri] = useState("");
  const [expandedInfo, setExpandedInfo] = useState({});

  useEffect(() => {
    props.app
      .getCurrentLocation()
      .then((location) => {
        setCurrentLocation(location);
      })
      .catch((exp) => {
        displayAlert(exp.message || "Failed to get location information");
      });
  }, [props.app]);

  const displayAlert = (message) => {
    setAlertInfo({ show: true, message });
    setTimeout(() => setAlertInfo({ show: false, message: "" }), 5000);
  };

  const fetchNearbyParking = async () => {
    setLoading(true);
    const limit = document.getElementById("limit").value;
    const radius = document.getElementById("radius").value;

    let connection = new ConnectionManager();

    try {
      const resp = await connection.getNearbyParking(
        currentLocation.latitude,
        currentLocation.longitude,
        radius,
        limit
      );
      const response = JSON.parse(resp);
      console.log(response);

      if (response.error) {
        displayAlert(`Error occurred: ${response.message}`);
      } else {
        setVarCap("Parking Spaces");
        setParkingSpaces(response.places);
      }
    } catch (error) {
      displayAlert("Please check your Spring Boot service is running");
    } finally {
      setLoading(false);
    }
  };
  const showOnMap = (location) => {
    if (location && location.latitude && location.longitude) {
      const mapsUrl = `https://maps.google.com/maps?q=${location.latitude},${location.longitude}&z=15&output=embed`;
      setSelectedMapUri(mapsUrl);
    } else {
      displayAlert("Invalid location data.");
    }
  };

  const handleCardClick = (card) => {
    setSelectedCard(card);
    // Set the current location when a card is clicked
    setCurrentLocation({
      latitude: card.latitude,
      longitude: card.longitude
    });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };


  const toggleDetails = (index) => {
    setExpandedInfo((prevState) => ({
      ...prevState,
      [index]: !prevState[index],
    }));
  };

  const cardsData = [
    {
      id: 1,
      title: 'John Silva',
      subtitle: '1',
      text: 'Experienced mechanic specializing in engine repairs and diagnostics.',
      latitude: 6.9044,
      longitude: 79.8540
    },
    {
      id: 2,
      title: 'Emily Perera',
      subtitle: '2',
      text: 'Skilled technician with expertise in electrical systems and brake repairs.',
      latitude: 6.9000,
      longitude: 79.8535
    },
    {
      id: 3,
      title: 'Michael Fernando',
      subtitle: '3',
      text: 'Leading a specialized workshop and a team of engineers focused on automotive engineering.',
      latitude: 6.9050,
      longitude: 79.8580
    },
    {
      id: 4,
      title: 'Daniel Hill',
      subtitle: '4',
      text: 'Providing consultancy for the required infrastructure for special projects.',
      latitude: 6.9040,
      longitude: 79.8520
    },
    {
      id: 5,
      title: 'Sophia Woods',
      subtitle: '5',
      text: 'Offering expertise in road transport route optimization.',
      latitude: 6.9035,
      longitude: 79.8535
    }
  ];




  return (
    <>
      <Header />
      <Container id="top"
        style={{
          width: "100vw",
          zIndex: 0,
          margin: 0,
          padding: 0,

        }}
        fluid
      >
        <iframe
          title="map"
          src={
            selectedMapUri ||
            `https://maps.google.com/maps?q=${currentLocation ? currentLocation.latitude : ""
            },${currentLocation ? currentLocation.longitude : ""
            }&z=15&output=embed`
          }
          style={{
            // border: 0,
            width: "100%",
            height: "80vh",
          }}
        ></iframe>

        <div className="shadow"
          style={{
            backgroundColor: 'white',
            bottom: '0',
            zIndex: '999',
            width: '100%',
            borderRadius: '40px 40px 0 0',
            position: 'relative',
            marginTop: '-150px',
            boxShadow: '0px -10px 10px rgba(0, 0, 0, 0.5)',
            display: 'flex',
            paddingTop: '15px',

          }}>


          <Container>
            <Row className="text-center">
              <div>
                Choose a mechanic
              </div>
            </Row>
            <hr />
            <Row className="text-center g-3">

              {selectedCard && (
                <>
                  <Card className="shadow g-4" style={{ borderRadius: '20px', border: '3px solid black', padding: '10px 0', position: 'relative' }}>
                    <div style={{ position: 'absolute', top: '10px', right: '10px', display: 'flex', alignItems: 'center' }}>
                      <span style={{ marginRight: '5px' }}>4.5</span>
                      <FaStar size={15} color="outline" />
                    </div>
                    <Card.Body>
                      <Card.Title>
                        <FaUser size={24} className="mr-2" />
                      </Card.Title>
                      <Card.Title className="mb-2">{selectedCard.title}</Card.Title>
                      <Card.Text>
                        {selectedCard.text}
                      </Card.Text>
                    </Card.Body>
                  </Card>
                </>
              )}

            </Row>
            <Row className="text-center g-2">
              <Row className="text-center g-2">
                {cardsData.map(card => (
                  <Card
                    key={card.id}
                    className="shadow"
                    style={{ borderRadius: '20px', padding: '10px 0', position: 'relative', cursor: 'pointer' }}
                    href="top"
                    onClick={() => handleCardClick(card)}
                  >
                    <div style={{ position: 'absolute', top: '50%', right: '10px', transform: 'translateY(-50%)' }}>
                      <FaUser size={18} />
                    </div>
                    <Card.Body className="text-left">
                      <Card.Title>{card.title}</Card.Title>
                      <Card.Text>{card.text}</Card.Text>
                    </Card.Body>
                  </Card>
                ))}
              </Row>
            </Row>
          </Container>
        </div >
      </Container >
    </>
  );
}

export default BreakdownAssist;