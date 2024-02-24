import React, {useContext, useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import SignIn from './pages/SignIn';
import NoPage from './pages/NoPage';
import Documents from './pages/Documents';
import Contact from './pages/Contact';
import Vehicles from './pages/Vehicles';
import AddVehicle from './pages/AddVehicle';
import AddServiceRecord from './pages/AddServiceRecord';
import ServiceRecords from './pages/ServiceRecords';
import { CircleLoader, MoonLoader } from 'react-spinners';
import 'bootstrap/dist/css/bootstrap.min.css';
import ThemeContext from './components/ThemeContext';
import './styles/App.css';


function App() {
  const { theme } = useContext(ThemeContext);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setTimeout(() => {
      setLoading(false);
    }, 2000); // Adjust the duration as needed
  }, []);

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
            <Route index element={<Login />} />
            <Route path="/home" element={<Home />} />
            <Route path="/signin" element={<SignIn />} />
            <Route path="/login" element={<Login />} />
            <Route path="/documents" element={<Documents />} />
            <Route path="*" element={<NoPage />} />
            <Route path="/contact" element={<Contact />} />
            <Route path="/vehicles" element={<Vehicles />} />
            <Route path="/addVehicle" element={<AddVehicle />} />
            <Route path="/serviceRecords" element={<ServiceRecords />} />
            <Route path="/addServiceRecord" element={<AddServiceRecord />} />

          </Routes>
        </BrowserRouter>
      )}
    </div>
   
  );
}

export default App;
