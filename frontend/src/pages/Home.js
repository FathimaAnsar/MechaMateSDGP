import Header from "../components/Header";
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Card from 'react-bootstrap/Card';
import Image from 'react-bootstrap/Image';
import './css/Home.css'

function Home() {
    return (
        <div>
        <Header/>
        <Container className="cards-container">
        <Row>
        <Col>
        <Card style={{ width: '20rem', height: '20rem' }}  bg={'Secondary'}  border="primary" className="cards">
        <Card.Body>
            <Card.Title>Vehicles</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">View Your Vehicle Documents</Card.Subtitle>
            <Card.Link href="/vehicles"><Image src={require('../images/car-insurance (2).png')} thumbnail /></Card.Link>
        </Card.Body>
        </Card>
        </Col>
        
        <Col sm>
        <Card style={{ width: '20rem', height: '20rem' }} bg={'primary'} className="cards">
        <Card.Body>
            <Card.Title>Accident assisatance</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">Accident Assistance</Card.Subtitle>
            <Card.Link href="/accidentassistance"><Image src={require('../images/tow-truck.png')} thumbnail /></Card.Link>
        </Card.Body>
        </Card>
        </Col>
       
      </Row>
      <Row>
      <Col sm>
        <Card style={{ width: '20rem', height: '20rem' }} bg={'primary'} className="cards">
        <Card.Body>
            <Card.Title>Service Records</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">Add New Vehicle Service Records</Card.Subtitle>
            <Card.Link href="/serviceRecords"><Image src={require('../images/add.png')} thumbnail /></Card.Link>
        </Card.Body>
        </Card>
        </Col>
       
        <Col sm>
        <Card style={{ width: '20rem', height: '20rem' }} bg={'primary'} className="cards">
        <Card.Body>
            <Card.Title>Spare Parts</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">Find Car Spare Parts</Card.Subtitle>
            <Card.Link href="/spareParts"><Image src={require('../images/brake.png')} thumbnail /></Card.Link>
        </Card.Body>
        </Card>
        </Col>
        <Col sm>
            <Card style={{ width: '20rem', height: '20rem' }} bg={'primary'} className="cards">
            <Card.Body>
            <Card.Title> Find Mechanic</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">Find Car Mechanic</Card.Subtitle>
            <Card.Link href="/findMechanic"><Image src={require('../images/maintenance.png')} thumbnail /></Card.Link>
        </Card.Body>
        </Card>
        </Col>
      </Row>
    </Container>

        </div>
    );
}

export default Home;
