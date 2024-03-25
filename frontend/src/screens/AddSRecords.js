import React, { useState, useEffect } from 'react';
import Header from "./components/Header";
import { Form, Button, Container, Card } from 'react-bootstrap';
import { useNavigate } from "react-router-dom";


import ConnectionManager from '../services/ConnectionManager.js';
import LoadingScreen from './components/LoadingScreen.js';
import {Pages} from '../Pages.js'


function AddServiceRecordByServiceProvider(props) {
    const [serviceCounter, setServiceCounter] = useState(2);
    const [serviceFields, setServiceFields] = useState([]);
    const [alertInfo, setAlertInfo] = useState({ show: false, error: { heading: '', message: '' } });
    const [services, setServices] = useState('');
    const [description, setDescription] = useState('');
    const [date, setDate] = useState(new Date());
    const [mileage, setMileage] = useState('');
    const [vehicles, setVehicles] = useState([]);

    const [loading, setLoading] = useState(true);

    async function addSRecord(event) {}

    let connection = new ConnectionManager();
    const navigate = useNavigate();



    useEffect(() => {
        const fetchData = async () => {
            try {
                const fetchedVehicles = await connection.getVehicleList();
                const vehicles = JSON.parse(fetchedVehicles);
                setVehicles(vehicles);
                setLoading(false);
            } catch (error) {
                console.error("Error fetching vehicles:", error);
            } finally {

            }
        };
        fetchData();
    }, []);
    
    const addService = () => {
        const newServiceField = {
            id: serviceCounter,
            description: '',
            appliedMaintenanceId: '',
            nextServiceInKMs: '',
            serviceQuality: 'High' 
        };
        setServiceFields([...serviceFields, newServiceField]);
        setServiceCounter(serviceCounter + 1);
    };

    const removeServiceField = id => {
        setServiceFields(serviceFields.filter(field => field.id !== id));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const formData = {
                vehicle: document.getElementById('services').value,
                description: document.getElementById('description').value,
                date: document.getElementById('date').value,
                mileage: document.getElementById('mileage').value,
                services: serviceFields.map(field => ({
                    appliedMaintenanceId: document.getElementById(`serviceType${field.id}`).value,
                    description: document.getElementById(`serviceDescription${field.id}`).value,
                    nextServiceInKMs: document.getElementById(`nextServiceInKMs${field.id}`).value,
                    quality: document.getElementById(`serviceQuality${field.id}`).value
                }))
            };
            console.log(formData.vehicle.split(' ')[0])
            const resp = await connection.addServiceRecord(formData, formData.vehicle.split(' ')[0] );
            const response = JSON.parse(resp);

            console.log(response);

            if (response.error) {
                setAlertInfo({
                    show: true,
                    error: { heading: 'Error', message: response.message },
                });
            } else {
                navigate("/" + Pages.DashboardUI)
            }
        } catch (error) {
            setAlertInfo({
                show: true,
                error: { heading: 'Error adding service record', message: error.message || "Server error" }
            });
        } finally {
            setLoading(false);
        }
    };


    return (
        <div className='g-4'>
            <div>
                <Header app={props.app} />
                {loading ? (
                    <LoadingScreen />
                ) : (
                    <>
                        <Container fluid='sm' className='g-4' style={{ maxWidth: '400px', marginTop: '20px' }}>
                            <h2>Add Service Record</h2>
                            {vehicles ? (
                                    <Form className='g-4'>
                                        <Form.Group controlId="services">
                                            <Form.Label>Vehicle:</Form.Label>
                                            <Form.Control as="select">
                                                {vehicles.map(vehicle => (
                                                    <option key={vehicle.id} value={vehicle.id}>
                                                        {vehicle.registrationNumber} {vehicle.vehicleModel} 
                                                    </option>
                                                ))}
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

                                        <fieldset>
                                            <hr />
                                            <legend>Services</legend>
                                            <div className="service-record">
                                                {serviceFields.map(field => (
                                                    <div key={field.id} className="g-2 service">
                                                        <Form.Group controlId={`serviceType${field.id}`}>
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
                                                        <Form.Group controlId={`serviceDescription${field.id}`}>
                                                            <Form.Label>Description:</Form.Label>
                                                            <Form.Control type="text" name={`serviceDescription${field.id}`} required />
                                                        </Form.Group>
                                                        <Form.Group controlId={`nextServiceInKMs${field.id}`}>
                                                            <Form.Label>Next Service in KMs:</Form.Label>
                                                            <Form.Control type="number" name={`nextServiceInKMs${field.id}`} required />
                                                        </Form.Group>
                                                        <Form.Group controlId={`serviceQuality${field.id}`}>
                                                            <Form.Label>Service Quality:</Form.Label>
                                                            <Form.Control as="select" defaultValue="High" required>
                                                                <option value="High">High</option>
                                                                <option value="Medium">Medium</option>
                                                                <option value="Low">Low</option>
                                                            </Form.Control>
                                                        </Form.Group>
                                                        <br />
                                                        <Button variant='dark' type="button" id="remove-service-btn" onClick={() => removeServiceField(field.id)}>Remove</Button>
                                                        <br /><br />
                                                    </div>
                                                ))}
                                            </div>
                                        </fieldset>

                                        <Button type="button" id="add-btn" onClick={addService}>+ Add Serviced Task</Button>
                                        <br /><br />

                                        <Button variant="success" type="submit" id="btn" className="submit-button" onClick={handleSubmit}>Submit</Button>
                                    </Form>


                            ) : (
                                'No vehicles found'
                            )}
                        </Container>
                    </>

                )}



            </div>
            
            
            <form>
                
                
            </form>
        </div>
    );
}

export default AddServiceRecordByServiceProvider;
































// // import React, { useState } from 'react';
// // import Header from "./components/Header";
// // import { Form, Button } from 'react-bootstrap';
// // import axios from 'axios';
// // import { API_BASE_URL } from "../Common.js";
// // import './styles/Form.css';

// // function AddServiceRecordByServiceProvider(props) {
// //     const [services, setServices] = useState('');
// //     const [description, setDescription] = useState('');
// //     const [date, setDate] = useState(new Date());
// //     const [mileage, setMileage] = useState('');

// //     async function addSRecord(event) {
// //       event.preventDefault();
  
// //       const requestBody = {
// //           description: description,
// //           date: date,
// //           mileage: mileage
// //       };
  
// //       try {
// //           const response = await axios.post( `${API_BASE_URL}/api/v1/general/add-service-record`, requestBody, {
// //             headers: {
// //                 'Content-Type': 'application/json'
// //             },
// //             withCredentials: true // Use withCredentials instead of credentials
// //         });
// //           // Check if response is successful (status code 2xx)
// //           if (response.status >= 200 && response.status < 300) {
// //             const responseData = response.data;
// //             const message = responseData.message;
// //             alert(`Service Record Status: ${message}`);
// //               // Clear input fields after successful addition
// //             clearInputFields();
// //             return responseData; // Returning data might be useful if you need it elsewhere
// //           } else {
// //             throw new Error('Failed to add service record'); // Throw an error if response status is not in the success range
// //           }
// //       } catch (err) {
// //           console.error('Error adding service record:', err);
// //           alert("Failed to Add Service Record");
// //       }
// //   }
  
// //   function clearInputFields() {
// //       // Clear input fields
// //       setServices('');
// //       setDescription('');
// //       setDate(new Date());
// //       setMileage('');
// //   }


// //   return (
// //     <div>
// //         <Header app ={props.app}/>
// //       <h2>Add Service Records Manually</h2>
// //       <div className="form-container">
// //       <Form>
// //         <Form.Group controlId="services">--
// //           <Form.Label>Service Type:</Form.Label>
// //           <Form.Control as="select">
// //             <option value="WheelAlignment">Wheel Alignment</option>
// //             <option value="EngineOilChange">Engine Oil Change</option>
// //             <option value="BrakeFluidChange">Brake Fluid Change</option>
// //             <option value="BrakeCaliperChange">Brake Caliper Change</option>
// //             <option value="CoolantChange">Coolant Change</option>
// //             <option value="TireChange">Tire Change</option>
// //             <option value="PistonRingsChange">Piston Rings Change</option>
// //             <option value="PistonChange">Piston Change</option>
// //             <option value="DieselFilterChange">Diesel Filter Change</option>
// //             <option value="VipersChange">Vipers Change</option>
// //           </Form.Control>
// //         </Form.Group>

// //         <Form.Group controlId="description">
// //           <Form.Label>Description:</Form.Label>
// //           <Form.Control as="textarea" rows={4} />
// //         </Form.Group>

// //         <Form.Group controlId="date">
// //           <Form.Label>Service Date:</Form.Label>
// //           <Form.Control type="date" />
// //         </Form.Group>

// //         <Form.Group controlId="mileage">
// //           <Form.Label>Mileage:</Form.Label>
// //           <Form.Control type="number" />
// //         </Form.Group>

// //         <Button variant="primary" onClick={addSRecord}>Submit</Button> 
// //       </Form>
// //       </div>

// //       <Button variant="secondary" onClick={() => props.app.changePage('ManageMyDocuments')}>
// //         Go Back
// //       </Button>
// //     </div>
    
// //   );
// // }

// // export default AddServiceRecordByServiceProvider;
