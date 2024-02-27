import React, {useContext, useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Home from './pages/Home';
import NoPage from './pages/NoPage';
import Documents from './pages/Documents';
import Contact from './pages/Contact';
import Vehicles from './pages/Vehicles';
import AddVehicle from './pages/AddVehicle';
import AddRecord from './pages/AddRecord';
import ServiceRecords from './pages/ServiceRecords';
import { CircleLoader } from 'react-spinners';
import 'bootstrap/dist/css/bootstrap.min.css';
import ThemeContext from './components/ThemeContext';
import './styles/App.css';
import SignIn from './pages/SignIn';
import SignUp from './pages/SignUp';
import ProtectedRoute from './services/ProtectedRoute';


function App() {
  const { theme } = useContext(ThemeContext);
  const [loading, setLoading] = useState(true);
  const [isSessionAvailable, setIsSessionAvailable] = useState(false);

  useEffect(() => {
    const session = localStorage.getItem('userProfile');
    setIsSessionAvailable(session);

    setTimeout(() => {
      setLoading(false);
    }, 1500); // Adjust the duration as needed
  }, []);

  const qrCodeUrl = "google.com"; //link of the QR code

  return (
    <div className={`App ${theme}`}>
      {loading ? (
        <div className="loader-container">
          <CircleLoader color="#085bd4" size={50} loading={loading} />
        </div>
      ) : (
        <BrowserRouter>
          <Routes>
            {/* Route for Home */}
            <Route
              path="/home"
              element={isSessionAvailable ? <Home /> : <Navigate to="/signin" />}
            />
            {/* Route for SignIn */}
            <Route
              path="/signin"
              element={!isSessionAvailable ? <SignIn /> : <Navigate to="/home" />}
            />
            {/* Route for SignUp */}
            <Route
              path="/signup"
              element={!isSessionAvailable ? <SignUp /> : <Navigate to="/home" />}
            />
            {/* Protected Routes */}
            <Route
              path="/documents"
              element={isSessionAvailable ? <Documents /> : <Navigate to="/signin" />}
            />
            <Route
              path="/contact"
              element={isSessionAvailable ? <Contact /> : <Navigate to="/signin" />}
            />
            <Route
              path="/vehicles"
              element={isSessionAvailable ? <Vehicles /> : <Navigate to="/signin" />}
            />
            <Route
              path="/addVehicle"
              element={isSessionAvailable ? <AddVehicle /> : <Navigate to="/signin" />}
            />
            <Route
              path="/serviceRecords"
              element={isSessionAvailable ? <ServiceRecords /> : <Navigate to="/signin" />}
            />
            <Route
              path="/AddRecord"
              element={isSessionAvailable ? <AddRecord url={qrCodeUrl} /> : <Navigate to="/signin" />}
            />
            {/* Route for unknown paths */}
            <Route path="*" element={<NoPage />} />
          </Routes>
        </BrowserRouter>
      )}
    </div>
  );
}

export default App;
