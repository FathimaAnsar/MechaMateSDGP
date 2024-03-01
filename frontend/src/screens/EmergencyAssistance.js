import React from 'react'

function EmergencyAssistance(props) {
    const handleGoBack = () => { props.app.goBack(); }
    
  return (
    <div>
        <h2>EmergencyAssistance</h2>
        <button onClick={handleGoBack}>Go Back</button>
    </div>
  )
}

export default EmergencyAssistance;
