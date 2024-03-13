import React, { useState, useContext } from "react"
import { main } from "./MechaMate.js"
import { Pages } from "./Pages.js"
import GetStarted from "./screens/GetStarted.js";
import SignIn from "./screens/SignIn.js";
import SignUp from "./screens/SignUp.js";
import EnterCode from "./screens/EnterCode.js";
import ForgotPassword from "./screens/ForgotPassword.js";
import Dashboard from "./screens/Dashboard.js";
import ResetPassword from "./screens/ResetPassword.js";
import Settings from "./screens/Settings.js";
import Notifications from "./screens/Notifications.js";
import AboutUs from "./screens/AboutUs.js";
import MyVehicles from "./screens/MyVehicles.js";
import PredictMaintenance from "./screens/PredictMaintenance.js";
import ShowPredictions from "./screens/ShowPredictions.js";
import TrackVehicle from "./screens/TrackVehicle.js";
import AutoMobSearch from "./screens/AutoMobSearch.js";
import AutoMobDetails from "./screens/AutoMobDetails.js";
import EmergencyAssistance from "./screens/EmergencyAssistance.js";
import ManageDocuments from "./screens/ManageDocuments.js";
import AddSRecords from "./screens/AddSRecords.js";
//import AddServiceRecordByQR from "./screens/AddServiceRecordByQR.js";
//import AddServiceRecordByServiceProvider from "./screens/AddServiceRecordByServiceProvider.js";
import ShowServiceRecordRequest from "./screens/ShowServiceRecordRequest.js";
import ParkingFinder from "./screens/ParkingFinder.js";
import ParkingInfo from "./screens/ParkingInfo.js";
import './styles/App.css';
import ThemeContext from './screens/components/ThemeContext.js';
import ViewVehicle from "./screens/ViewVehicle.js";
import { BrowserRouter, Routes, Route, Navigate, Link } from 'react-router-dom';

//main.reset();

function App() {

  const [currentPage, setCurrentPage] = useState("");
  const { theme } = useContext(ThemeContext);
  const changeCurrentPage = () => setCurrentPage(main.currentPage);
  main.setRefreshCaller(changeCurrentPage);


  if (!main.getUserProfile()) {

    if (main.currentPage != Pages.GetStartedUI &&
      main.currentPage != Pages.SignInUI &&
      main.currentPage != Pages.SignUpUI &&
      main.currentPage != Pages.ForgotPasswordUI &&
      main.currentPage != Pages.EnterCodeUI) {
      if (main.isFirstRunDone()) {
        if (main.isAppLoaded()) alert("Please sign in to continue!");
        main.currentPage = Pages.SignInUI;
        window.location.href = "/" + main.currentPage;
        return(<></>);
      } else {
        main.currentPage = Pages.GetStartedUI;
        window.location.href = "/" + main.currentPage;
        return(<></>);
      }
    }
  } else {
    if (main.currentPage === Pages.GetStartedUI || main.currentPage === Pages.SignUpUI) {
      main.currentPage = Pages.DashboardUI;
      window.location.href = "/" + main.currentPage;
      return(<></>);
  } else if (main.currentPage === Pages.SignInUI) {

    }
  }

  

  if (!main.isAppLoaded()) main.setAppLoaded(true);

  //main.currentPage = "";
  return (
  <BrowserRouter>
  <Routes>
    <Route path={Pages.GetStartedUI} element={<GetStarted app={main}/>} />
    <Route path={Pages.SignInUI} element={<SignIn app={main}/>} />
    <Route path={Pages.SignUpUI} element={<SignUp app={main}/>} />
    <Route path={Pages.EnterCodeUI} element={<EnterCode app={main}/>} />
    <Route path={Pages.ForgotPasswordUI} element={<ForgotPassword app={main}/>} />
    <Route path={Pages.ResetPasswordUI} element={<ResetPassword app={main}/>} />
    <Route path={Pages.DashboardUI} element={<Dashboard app={main}/>} />
    <Route path={Pages.SettingsUI} element={<Settings app={main}/>} />
    <Route path={Pages.NotificationsUI} element={<Notifications app={main}/>} />
    <Route path={Pages.AboutUsUI} element={<AboutUs app={main}/>} />
    <Route path={Pages.MyVehiclesUI} element={<MyVehicles app={main}/>} />
    <Route path={Pages.PredictMaintenanceUI} element={<PredictMaintenance app={main}/>} />
    <Route path={Pages.ShowPredictionsUI} element={<ShowPredictions app={main}/>} />
    <Route path={Pages.TrackVehicleUI} element={<TrackVehicle app={main}/>} />
    <Route path={Pages.ShowPredictionsUI} element={<ShowPredictions app={main}/>} />
    <Route path={Pages.AutoMobSearchUI} element={<AutoMobSearch app={main}/>} />
    <Route path={Pages.AutoMobDetailsUI} element={<AutoMobDetails app={main}/>} />
    <Route path={Pages.EmergencyAssistUI} element={<EmergencyAssistance app={main}/>} />
    <Route path={Pages.ManageDocumentsUI} element={<ManageDocuments app={main}/>} />
    <Route path={Pages.AddSRecordManualUI} element={<AddSRecords app={main}/>} />
    <Route path={Pages.ParkingFinderUI} element={<ParkingFinder app={main}/>} />
    <Route path={Pages.ParkingInfoUI} element={<ParkingInfo app={main}/>} />
    <Route path={Pages.ViewVehicle} element={<ViewVehicle app={main}/>} />
    <Route path="*" element={<GetStarted app={main}/>} />
  </Routes>
</BrowserRouter>

  );

  // if (main.currentPage === Pages.GetStartedUI) return (<><GetStarted app={main} /></>);

  // if (main.currentPage === Pages.SignInUI) return (<><SignIn app={main} /></>);
  // if (main.currentPage === Pages.SignUpUI) return (<><SignUp app={main} /></>);
  // if (main.currentPage === Pages.EnterCodeUI) return (<><EnterCode app={main} /></>);

  // if (main.currentPage === Pages.ForgotPasswordUI) return (<><ForgotPassword app={main} /></>);
  // if (main.currentPage === Pages.ResetPasswordUI) return (<><ResetPassword app={main} /></>);

  // if (main.currentPage === Pages.DashboardUI) return (<><Dashboard app={main} /></>);

  // if (main.currentPage === Pages.SettingsUI) return (<><Settings app={main} /></>);

  // if (main.currentPage === Pages.NotificationsUI) return (<><Notifications app={main} /></>);

  // if (main.currentPage === Pages.AboutUsUI) return (<><AboutUs app={main} /></>);

  // if (main.currentPage === Pages.MyVehiclesUI) return (<><MyVehicles app={main} /></>);

  // if (main.currentPage === Pages.PredictMaintenanceUI) return (<><PredictMaintenance app={main} /></>);
  // if (main.currentPage === Pages.ShowPredictionsUI) return (<><ShowPredictions app={main} /></>);

  // if (main.currentPage === Pages.TrackVehicleUI) return (<><TrackVehicle app={main} /></>);

  // if (main.currentPage === Pages.ShowPredictionsUI) return (<><ShowPredictions app={main} /></>);

  // if (main.currentPage === Pages.AutoMobSearchUI) return (<><AutoMobSearch app={main} /></>);
  // if (main.currentPage === Pages.AutoMobDetailsUI) return (<><AutoMobDetails app={main} /></>);

  // if (main.currentPage === Pages.EmergencyAssistUI) return (<><EmergencyAssistance app={main} /></>);

  // if (main.currentPage === Pages.ManageDocumentsUI) return (<><ManageDocuments app={main} /></>);
  // if (main.currentPage === Pages.AddSRecordManualUI) return (<><AddSRecords app={main} /></>);
  // //if (main.currentPage === Pages.QrUI) return (<><QrUi app={main} /></>);
  // //if (main.currentPage === Pages.AddRecordBySPUI) return (<><AddServiceRecordByServiceProvider app={main} /></>);
  // if (main.currentPage === Pages.ShowRecordReqUI) return (<><ShowServiceRecordRequest app={main} /></>);

  // if (main.currentPage === Pages.ParkingFinderUI) return (<><ParkingFinder app={main} /></>);
  // if (main.currentPage === Pages.ParkingInfoUI) return (<><ParkingInfo app={main} /></>);
  // if (main.currentPage === Pages.ViewVehicle) return (<><ViewVehicle app={main} /></>);


  return (<div className={`App ${theme}`}>
    <><SignIn app={main} /></>
  </div>
  );

}

export default App;