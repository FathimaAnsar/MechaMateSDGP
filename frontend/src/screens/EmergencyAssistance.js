import React from 'react'
import Header from "./components/Header";
function EmergencyAssistance(props) {
    const handleGoBack = () => { props.app.goBack(); }
    
  return (
    <div>
      <Header app ={props.app}/>
        <h2>EmergencyAssistance</h2>
        <button onClick={handleGoBack}>Go Back</button>
    </div>
  )
}

export default EmergencyAssistance;
