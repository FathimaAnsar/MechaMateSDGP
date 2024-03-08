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
import AddServiceRecordByQR from "./screens/AddServiceRecordByQR.js";
import AddServiceRecordByServiceProvider from "./screens/AddServiceRecordByServiceProvider.js";
import ShowServiceRecordRequest from "./screens/ShowServiceRecordRequest.js";
import ParkingFinder from "./screens/ParkingFinder.js";
import ParkingInfo from "./screens/ParkingInfo.js"; 
import './styles/App.css';
import ThemeContext from './screens/components/ThemeContext.js';


//main.reset();

function App() {

  const [currentPage, setCurrentPage] = useState("");
  const { theme } = useContext(ThemeContext);
  const changeCurrentPage = () => setCurrentPage(main.currentPage);
  main.setRefreshCaller(changeCurrentPage);





  if(!main.getUserProfile()) {
    if(main.currentPage != Pages.GetStartedUI &&
      main.currentPage != Pages.SignInUI &&
      main.currentPage != Pages.SignUpUI &&
      main.currentPage != Pages.ForgotPasswordUI &&
      main.currentPage != Pages.EnterCodeUI) {
      if(main.isFirstRunDone()) {
        if(main.isAppLoaded()) alert("Please sign in to continue!");
        main.currentPage = Pages.SignInUI;
      } else {
        main.currentPage = Pages.GetStartedUI;
      }
    }
  } else {
    if(main.currentPage === Pages.GetStartedUI || main.currentPage === Pages.SignUpUI) {
      main.currentPage = Pages.DashboardUI;
    } else if(main.currentPage === Pages.SignInUI) {

    }
  }

  if(!main.isAppLoaded()) main.setAppLoaded(true);

  if (main.currentPage === Pages.GetStartedUI) return (<><GetStarted app={main} /></>);

  if (main.currentPage === Pages.SignInUI) return (<><SignIn app={main} /></>);
  if (main.currentPage === Pages.SignUpUI) return (<><SignUp app={main} /></>);
  if (main.currentPage === Pages.EnterCodeUI) return (<><EnterCode app={main} /></>);

  if (main.currentPage === Pages.ForgotPasswordUI) return (<><ForgotPassword app={main} /></>);
  if (main.currentPage === Pages.ResetPasswordUI) return (<><ResetPassword app={main} /></>);

  if (main.currentPage === Pages.DashboardUI) return (<><Dashboard app={main} /></>);

  if (main.currentPage === Pages.SettingsUI) return (<><Settings app={main} /></>);

  if (main.currentPage === Pages.NotificationsUI) return (<><Notifications app={main} /></>);

  if (main.currentPage === Pages.AboutUsUI) return (<><AboutUs app={main} /></>);

  if (main.currentPage === Pages.MyVehiclesUI) return (<><MyVehicles app={main} /></>);

  if (main.currentPage === Pages.PredictMaintenanceUI) return (<><PredictMaintenance app={main} /></>);
  if (main.currentPage === Pages.ShowPredictionsUI) return (<><ShowPredictions app={main} /></>);

  if (main.currentPage === Pages.TrackVehicleUI) return (<><TrackVehicle app={main} /></>);

  if (main.currentPage === Pages.ShowPredictionsUI) return (<><ShowPredictions app={main} /></>);

  if (main.currentPage === Pages.AutoMobSearchUI) return (<><AutoMobSearch app={main} /></>);
  if (main.currentPage === Pages.AutoMobDetailsUI) return (<><AutoMobDetails app={main} /></>);

  if (main.currentPage === Pages.EmergencyAssistUI) return (<><EmergencyAssistance app={main} /></>);

  if (main.currentPage === Pages.ManageDocumentsUI) return (<><ManageDocuments app={main} /></>);
  if (main.currentPage === Pages.AddSRecordManualUI) return (<><AddSRecords app={main} /></>);
  if (main.currentPage === Pages.AddSRecordQRUI) return (<><AddServiceRecordByQR app={main} /></>);
  if (main.currentPage === Pages.AddRecordBySPUI) return (<><AddServiceRecordByServiceProvider app={main} /></>);
  if (main.currentPage === Pages.ShowRecordReqUI) return (<><ShowServiceRecordRequest app={main} /></>);

  if (main.currentPage === Pages.ParkingFinderUI) return (<><ParkingFinder app={main} /></>);
  if (main.currentPage === Pages.ParkingInfoUI) return (<><ParkingInfo app={main} /></>);

  return (<div className={`App ${theme}`}>
    <><SignIn app={main} /></>
    </div>
    );

}

export default App;