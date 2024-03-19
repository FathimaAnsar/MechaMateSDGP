import React from 'react'
import Header from "./components/Header";

function PredictMaintenance(props) {

  return (
    
    <div>
      <Header app ={props.app}/>
        PredictMaintenance <br></br>
        <button onClick={() =>{props.app.goBack()}}>Go Back</button>
    </div>
    
  )
}

export default PredictMaintenance