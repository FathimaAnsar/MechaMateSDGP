import React from 'react'

function PredictMaintenance(props) {

  return (
    
    <div>
        PredictMaintenance <br></br>
        <button onClick={() =>{props.app.goBack()}}>Go Back</button>
    </div>
    
  )
}

export default PredictMaintenance