import React, { useState } from "react"
import { main } from "./MechaMate.js"
import GetStarted from "./screens/GetStarted.js";
import SignIn from "./screens/SignIn.js";
import SignUp from "./screens/SignUp.js";
import ForgotPassword from "./screens/ForgotPassword.js";
import { Pages } from "./Pages.js" 



function App() {
  const [currentPage, setCurrentPage] = useState("");

  const changeCurrentPage = () => setCurrentPage(main.currentPage);

  main.setRefreshCaller(changeCurrentPage);

  // if(!main.getUserProfile()) {
  //   if(main.isFirstRunDone()) {
  //     main.currentPage = "signin";
  //   } else {
  //     main.currentPage = "get-started";
  //   }
  // }

  if(main.currentPage == Pages.GetStartedUI) return (<><GetStarted app={main}/></>);
  if(main.currentPage == Pages.SignInUI) return (<><SignIn app={main}/></>);  
  if(main.currentPage == Pages.SignUpUI) return (<><SignUp app={main}/></>);  
  if(main.currentPage == Pages.ForgotPasswordUI) return (<><ForgotPassword app={main}/></>);  
  return (<><GetStarted app={main}/></>);

}

export default App;