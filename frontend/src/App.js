import React, {useContext, useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
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




function App() {
  const { theme } = useContext(ThemeContext);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setTimeout(() => {
      setLoading(false);
    }, 1500); // Adjust the duration as needed
  }, []);

  const qrCodeUrl = "google.com"; //link of the QR code

  return (
    
    <div className={`App ${theme}`}>
      {loading ? (
        <div className="loader-container">
          <CircleLoader 
          color="#085bd4" 
          size={50}
          loading={loading}/>
        </div>
      ) : (
        <BrowserRouter>
          <Routes>
            {/*default login page opens*/}
           {/* { <Route index element={<SignIn />} />  } */}

            {/* if a session is avalable we need to redirect user to home page
            else to signup page */}

            <Route path="/home" element={<Home />} />
            <Route path="/signin" element={<SignIn />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/documents" element={<Documents />} />
            <Route path="*" element={<NoPage />} />
            <Route path="/contact" element={<Contact />} />
            <Route path="/vehicles" element={<Vehicles />} />
            <Route path="/addVehicle" element={<AddVehicle />} />
            <Route path="/serviceRecords" element={<ServiceRecords />} />
            <Route path="/AddRecord" element={<AddRecord url={qrCodeUrl} />} />

          </Routes>
        </BrowserRouter>
      )}
    </div>
   
  );
}

export default App;
