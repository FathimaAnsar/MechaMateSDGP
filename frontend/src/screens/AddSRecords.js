import React, { useState, useEffect } from 'react';
import Header from "./components/Header";
import { Form, Button, Container } from 'react-bootstrap';
import axios from 'axios';
import ConnectionManager from '../services/ConnectionManager.js';
import LoadingScreen from './components/LoadingScreen.js';
// import './styles/Form.css';

function AddServiceRecordByServiceProvider(props) {
    const [services, setServices] = useState('');
    const [description, setDescription] = useState('');
    const [date, setDate] = useState(new Date());
    const [mileage, setMileage] = useState('');
    const [vehicles, setVehicles] = useState([]);

    const [loading, setLoading] = useState(true);

    const connection = new ConnectionManager();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const fetchedVehicles = await connection.getVehicleList();
                const vehicles = JSON.parse(fetchedVehicles);
                setVehicles(vehicles);
                setLoading(false);

                console.log(vehicles)
            } catch (error) {
                console.error("Error fetching vehicles:", error);
            } finally {

            }
        };
        fetchData();
    }, []);

    async function addSRecord(event) {
        event.preventDefault();

        const requestBody = {
            description: description,
            date: date,
            mileage: mileage
        };
        //http://localhost:8080/api/v1/general/add-service-record
        try {
            const response = await axios.post("https://mechamate.site/api/v1/general/add-service-record", requestBody, {
                headers: {
                    'Content-Type': 'application/json'
                },
                withCredentials: true // Use withCredentials instead of credentials
            });
            // Check if response is successful (status code 2xx)
            if (response.status >= 200 && response.status < 300) {
                const responseData = response.data;
                const message = responseData.message;
                alert(`Service Record Status: ${message}`);
                // Clear input fields after successful addition
                clearInputFields();
                return responseData; // Returning data might be useful if you need it elsewhere
            } else {
                throw new Error('Failed to add service record'); // Throw an error if response status is not in the success range
            }
        } catch (err) {
            console.error('Error adding service record:', err);
            alert("Failed to Add Service Record");
        }
    }

    function clearInputFields() {
        // Clear input fields
        setServices('');
        setDescription('');
        setDate(new Date());
        setMileage('');
    }


    return (
        <div>
            <Header app={props.app} />



            {loading ? (
                <LoadingScreen />
            ) : (
                <>
                    <Container fluid='sm' className='g-4' style={{ maxWidth: '400px', marginTop: '20px' }}>
                        <h2>Add Service Records Manually</h2>
                        {vehicles ? (
                            <Form>
                                <Form.Group controlId="services">
                                    <Form.Label>Vehicle:</Form.Label>
                                    <Form.Control as="select">
                                        {vehicles.map(vehicle => (
                                            <option key={vehicle.id} value={vehicle.id}>
                                                {vehicle.vehicleModel} - {vehicle.registrationNumber}
                                            </option>
                                        ))}
                                    </Form.Control>
                                </Form.Group>

                                <Form.Group controlId="services">
                                    <Form.Label>Service Type:</Form.Label>
                                    <Form.Control as="select">
                                        <option value="WheelAlignment">Wheel Alignment</option>
                                        <option value="EngineOilChange">Engine Oil Change</option>
                                        <option value="BrakeFluidChange">Brake Fluid Change</option>
                                        <option value="BrakeCaliperChange">Brake Caliper Change</option>
                                        <option value="CoolantChange">Coolant Change</option>
                                        <option value="TireChange">Tire Change</option>
                                    </Form.Control>
                                </Form.Group>

                                <Form.Group controlId="description">
                                    <Form.Label>Description:</Form.Label>
                                    <Form.Control as="textarea" rows={4} />
                                </Form.Group>

                                <Form.Group controlId="date">
                                    <Form.Label>Service Date:</Form.Label>
                                    <Form.Control type="date" />
                                </Form.Group>

                                <Form.Group controlId="mileage">
                                    <Form.Label>Mileage:</Form.Label>
                                    <Form.Control type="number" min={0} />
                                </Form.Group>

                                <Form.Group>
                                    <Button variant="primary" onClick={addSRecord}>Submit</Button>
                                </Form.Group>
                            </Form>
                        ) : (
                            'No vehicles found'
                        )}
                    </Container>
                </>

            )}



        </div>

    );
}

export default AddServiceRecordByServiceProvider;
