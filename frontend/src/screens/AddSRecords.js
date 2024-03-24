import React, { useState } from 'react';
import './public/ServiceRecord.css';
import Header from "./components/Header.js";

function ServiceRecordForm(props) {
    const [serviceCounter, setServiceCounter] = useState(2);
    const [serviceFields, setServiceFields] = useState([]);

    const addService = () => {
        const newServiceField = {
            id: serviceCounter,
            description: '',
            appliedMaintenanceId: '',
            nextServiceInKMs: '',
            serviceQuality: 'High' // default value
        };
        setServiceFields([...serviceFields, newServiceField]);
        setServiceCounter(serviceCounter + 1);
    };

    const removeServiceField = id => {
        setServiceFields(serviceFields.filter(field => field.id !== id));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        
        // Collect form data
        const formData = new FormData(event.target);
        const serviceRecordData = {};
        formData.forEach((value, key) => {
            serviceRecordData[key] = value;
        });

        // Send HTTP POST request to the server endpoint
        try {
            const response = await fetch('your-server-endpoint-url', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(serviceRecordData),
            });

            if (!response.ok) {
                throw new Error('Failed to submit form data.');
            }

            // Handle success response
            console.log('Form data submitted successfully.');
        } catch (error) {
            // Handle error
            console.error('Error submitting form data:', error.message);
        }
    };

    return (
        <div>
          <Header app={props.app} />
            <h1>Vehicle Service Information Form</h1>
            
            <form>
                <label htmlFor="registrationNumber">Registration Number:</label>
                <input type="text" id="registrationNumber" name="registrationNumber" required />

                <label htmlFor="vehicleType">Vehicle Type:</label>
                <select id="vehicleType" name="vehicleType" required>
                    <option value="Car">Car</option>
                    <option value="Truck">Truck</option>
                    <option value="Motorcycle">Motorcycle</option>
                </select>

                <label htmlFor="fuelType">Fuel Type:</label>
                <select id="fuelType" name="fuelType" required>
                    <option value="Petrol">Petrol</option>
                    <option value="Diesel">Diesel</option>
                    <option value="Electric">Electric</option>
                </select>

                <label htmlFor="vehicleMake">Vehicle Make:</label>
                <input type="text" id="vehicleMake" name="vehicleMake" required />

                <label htmlFor="vehicleModel">Vehicle Model:</label>
                <input type="text" id="vehicleModel" name="vehicleModel" required />

                <label htmlFor="insNo">Insurance Number:</label>
                <input type="text" id="insNo" name="insNo" required />

                <label htmlFor="insExpDate">Insurance Expiry Date:</label>
                <input type="date" id="insExpDate" name="insExpDate" required />

                <label htmlFor="regExpDate">Registration Expiry Date:</label>
                <input type="date" id="regExpDate" name="regExpDate" required />

                <label htmlFor="currentMileage">Current Mileage:</label>
                <input type="number" id="currentMileage" name="currentMileage" required />
                
                <fieldset>
                    <legend>Service Records:</legend>
                    <div className="service-record">
                        {serviceFields.map(field => (
                            <div key={field.id} className="service">
                                <label htmlFor={`serviceDescription${field.id}`}>Description:</label>
                                <input type="text" id={`serviceDescription${field.id}`} name={`serviceDescription${field.id}`} required />

                                <label htmlFor={`appliedMaintenanceId${field.id}`}>Applied Maintenance ID:</label>
                                <select id={`serviceType${field.id}`} name={`serviceType${field.id}`} defaultValue="none" required>
                                    <option value="WheelAlignment">Wheel Alignment</option>
                                    <option value="EngineOilChange">Engine Oil Change</option>
                                    <option value="BrakeFluidChange">Brake Fluid Change</option>
                                    <option value="BrakeCaliperChange">Brake Caliper Change</option>
                                    <option value="CoolantChange">Coolant Change</option>
                                    <option value="TireChange">Tire Change</option>
                                    <option value="PistonChange">Piston Change</option>
                                </select>
                                

                                <label htmlFor={`nextServiceInKMs${field.id}`}>Next Service in KMs:</label>
                                <input type="number" id={`nextServiceInKMs${field.id}`} name={`nextServiceInKMs${field.id}`} required />

                                <label htmlFor={`serviceQuality${field.id}`}>Service Quality:</label>
                                <select id={`serviceQuality${field.id}`} name={`serviceQuality${field.id}`} defaultValue="High" required>
                                    <option value="High">High</option>
                                    <option value="Medium">Medium</option>
                                    <option value="Low">Low</option>
                                </select>

                                

                                <button type="button" id="remove-service-btn"  onClick={() => removeServiceField(field.id)}>Remove</button>
                            </div>
                        ))}
                    </div>
                </fieldset>

                <button type="button" id="add-btn"  onClick={addService}>+ Add Serviced Task</button>
                <button type="submit" id="btn"  className="submit-button">Submit</button>
            </form>
        </div>
    );
}

export default ServiceRecordForm;
































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
