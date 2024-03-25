import React, { useContext, useEffect } from "react";
import { main } from "./MechaMate.js";
import { Pages } from "./Pages.js";
import GetStarted from "./screens/GetStarted.js";
import SignIn from "./screens/SignIn.js";
import EnterCode from "./screens/EnterCode.js";
import ForgotPassword from "./screens/ForgotPassword.js";
import Dashboard from "./screens/Dashboard.js";
import ResetPassword from "./screens/ResetPassword.js";
import Settings from "./screens/Settings.js";
import Notifications from "./screens/Notifications.js";
import AboutUs from "./screens/AboutUs.js";
import AddVehicles from "./screens/AddVehicles.js";
import PredictMaintenance from "./screens/PredictMaintenance.js";
import ShowPredictions from "./screens/ShowPredictions.js";
import TrackVehicle from "./screens/TrackVehicle.js";
import AutoMobSearch from "./screens/AutoMobSearch.js";
import BreakdownAssist from "./screens/BreakdownAssist.js";
import CookiesPolicy from "./screens/TOS/CookiesPolicy.js";
import PrivacyPolicy from "./screens/TOS/PrivacyPolicy.js";
import Terms from "./screens/TOS/Terms.js";
import ManageDocuments from "./screens/ManageDocuments.js";
import AddSRecords from "./screens/AddSRecords.js";
//import AddServiceRecordByQR from "./screens/AddServiceRecordByQR.js";
//import AddServiceRecordByServiceProvider from "./screens/AddServiceRecordByServiceProvider.js";
//import ShowServiceRecordRequest from "./screens/ShowServiceRecordRequest.js";
import ParkingFinder from "./screens/ParkingFinder.js";
import ThemeContext from "./screens/components/ThemeContext.js";
import ViewVehicle from "./screens/ViewVehicle.js";
import { Routes, Route, Navigate, useNavigate } from "react-router-dom";
import PaymentSuccess from "./screens/PaymentSuccess";
import PaymentCancelled from "./screens/PaymentCancelled";
import "./styles/App.css";
import QrPage from "./screens/QrUi.js";
import EmergencyAssist from "./screens/EmergencyAssist";

// main.reset();

function App() {
  const { theme } = useContext(ThemeContext);
  main.currentPage = window.location.pathname.length > 1 ? window.location.pathname.substring(1) : Pages.GetStartedUI;
  if (!main.getUserProfile()) {
    if ((main.currentPage != Pages.GetStartedUI && main.currentPage != Pages.SignInUI && main.currentPage != Pages.ForgotPasswordUI && main.currentPage != Pages.EnterCodeUI) || window.location.pathname.length < 2) {
      if (main.isFirstRunDone()) {
        if (main.isAppLoaded()) alert("Please sign in to continue!");
        main.currentPage = Pages.SignInUI;
        window.location.href = "/" + main.currentPage;
        return (<></>);
      } else {
        main.currentPage = Pages.GetStartedUI;
        window.location.href = "/" + main.currentPage;
        return (<></>);
      }
    }
  } else {
    if (main.currentPage === Pages.GetStartedUI) {
      main.currentPage = Pages.DashboardUI;
      window.location.href = "/" + main.currentPage;
      return (<></>);
    } else if (main.currentPage === Pages.SignInUI) {
      //
    }
  }


  if (!main.isAppLoaded()) main.setAppLoaded(true);

  return (
    <div className={`App ${theme}`}>
      <Routes>
        <Route path={Pages.GetStartedUI} element={<GetStarted app={main} />} />
        <Route path={Pages.SignInUI} element={<SignIn app={main} />} />
        <Route path={Pages.EnterCodeUI} element={<EnterCode app={main} />} />
        <Route
          path={Pages.ForgotPasswordUI}
          element={<ForgotPassword app={main} />}
        />
        <Route
          path={Pages.ResetPasswordUI}
          element={<ResetPassword app={main} />}
        />
        <Route path={Pages.DashboardUI} element={<Dashboard app={main} />} />
        <Route path={Pages.SettingsUI} element={<Settings app={main} />} />
        <Route
          path={Pages.NotificationsUI}
          element={<Notifications app={main} />}
        />
        <Route path={Pages.AboutUsUI} element={<AboutUs app={main} />} />
        <Route
          path={Pages.AddVehiclesUI}
          element={<AddVehicles app={main} />}
        />
        <Route
          path={Pages.PredictMaintenanceUI}
          element={<PredictMaintenance app={main} />}
        />
        <Route
          path={Pages.ShowPredictionsUI}
          element={<ShowPredictions app={main} />}
        />
        <Route
          path={Pages.TrackVehicleUI}
          element={<TrackVehicle app={main} />}
        />
        <Route
          path={Pages.ShowPredictionsUI}
          element={<ShowPredictions app={main} />}
        />
        <Route
          path={Pages.AutoMobSearchUI}
          element={<AutoMobSearch app={main} />}
        />
        <Route
          path={Pages.BreakdownAssistUI}
          element={<BreakdownAssist app={main} />}
        />
        <Route
          path={Pages.ManageDocumentsUI}
          element={<ManageDocuments app={main} />}
        />
        <Route
          path={Pages.AddSRecordManualUI}
          element={<AddSRecords app={main} />}
        />
        <Route
          path={Pages.ParkingFinderUI}
          element={<ParkingFinder app={main} />}
        />
        <Route
            path={Pages.EmergencyAssistUI}
            element={<EmergencyAssist app={main} />}
        />
        <Route path={Pages.QrUI} element={<QrPage app={main} />} />
        <Route path={Pages.ViewVehicle} element={<ViewVehicle app={main} />} />
        <Route path={Pages.PrivacyPolicyUI} element={<PrivacyPolicy />} />
        <Route path={Pages.CookiesPolicyUI} element={<CookiesPolicy />} />
        <Route path={Pages.TermsUI} element={<Terms />} />
        {/*these two for testing payhere*/}
        <Route path="*" element={<Dashboard app={main} />} />
      </Routes>
    </div>
  );
}

export default App;
