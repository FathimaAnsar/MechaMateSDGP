import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { Button, Form } from 'react-bootstrap';
import "./styles/PredictiveMaintenance.css";
const dummyVehicles = [
  { id: 'v1', name: 'Car  A' },
  { id: 'v2', name: 'Car  B' },
  { id: 'v3', name: 'Car C' },
];

const dummyPredictions = [
  { id: 'p1', name: 'Engine Health' },
  { id: 'p2', name: 'Tire Healthh' },
  { id: 'p3', name: 'Battery health' },
];

function PredictiveMaintenance() {
  const [selectedVehicle, setSelectedVehicle] = useState('');
  const [selectedPrediction, setSelectedPrediction] = useState('');
  const [isPredicting, setIsPredicting] = useState(false);
  const [predictionResult, setPredictionResult] = useState(null);

  const handleVehicleChange = (e) => {
    setSelectedVehicle(e.target.value);
  };

  const handlePredictionChange = (e) => {
    setSelectedPrediction(e.target.value);
  };

  const startPrediction = () => {
    if (!selectedVehicle || !selectedPrediction) {
      alert('please select a vehicle and a prediction type.');
      return;
    }
    setIsPredicting(true);
    setTimeout(() => {
      setPredictionResult(`predicted result for ${selectedPrediction} of ${selectedVehicle}`);
      setIsPredicting(false);
    }, 2000);
  };

  if (predictionResult) {
    return (
        <div>
          <h3>Prediction Result</h3>
          <p>{predictionResult}</p>
        </div>
    );
  }

  return (
      <div className="predictive-maintenance">
        <h1>Predictive Vehicle Maintenance</h1>

        <Form>
          <Form.Group>
            <Form.Label>Select your vehicle</Form.Label>
            <Form.Control as="select" value={selectedVehicle} onChange={handleVehicleChange}>
              <option value="">Select a vehicle</option>
              {dummyVehicles.map((vehicle) => (
                  <option key={vehicle.id} value={vehicle.name}>{vehicle.name}</option>
              ))}
            </Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Select prediction</Form.Label>
            <Form.Control as="select" value={selectedPrediction} onChange={handlePredictionChange}>
              <option value="">Select a prediction type</option>
              {dummyPredictions.map((prediction) => (
                  <option key={prediction.id} value={prediction.name}>{prediction.name}</option>
              ))}
            </Form.Control>
          </Form.Group>
          <motion.div whileTap={{scale: 0.9}} className="start-button-container">
            <motion.button
                className="start-button"
                onClick={startPrediction}
                disabled={isPredicting}
                whileHover={{scale: 1.1}}
                whileTap={{scale: 0.8}}
            >
              {isPredicting ? (
                  <motion.div
                      animate={{rotate: 360}}
                      transition={{duration: 1, loop: Infinity}}
                  >
                    Loading...
                  </motion.div>
              ) : (
                  'Start'
              )}
            </motion.button>
          </motion.div>
        </Form>
      </div>
  );
}

export default PredictiveMaintenance;
