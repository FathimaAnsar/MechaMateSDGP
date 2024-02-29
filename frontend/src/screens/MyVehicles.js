import React from 'react'

function MyVehicles() {
  return (
    <div>
        <form>
  <label for="vehicleType">Vehicle Type:</label>
  <select id="vehicleType" name="vehicleType">
    <option value="Car">Car</option>
    <option value="Van">Van</option>
    <option value="Bus">Bus</option>
    <option value="Lorry">Lorry</option>
    <option value="Motorcycle">Motorcycle</option>
  </select><br/>
  
  <label for="fuelType">Fuel Type:</label>
  <select id="fuelType" name="fuelType">
    <option value="Petrol">Petrol</option>
    <option value="Diesel">Diesel</option>
    <option value="LPGas">LPGas</option>
    <option value="Electric">Electric</option>
  </select><br/>
  
  <label for="vehicleMake">Vehicle Make:</label>
  <input type="text" id="vehicleMake" name="vehicleMake" placeholder="Toyota"/><br/>
  
  <label for="vehicleModel">Vehicle Model:</label>
  <input type="text" id="vehicleModel" name="vehicleModel" placeholder="Camry"/><br/>
  
  <label for="regNo">Vehicle Registration Number:</label>
  <input type="text" id="regNo" name="regNo" placeholder="ABC1234"/><br/>
  
  <label for="regExpDate">Vehicle Registration Expiration Date:</label><br/>
  <input type="date" id="regExpDate" name="regExpDate"/>
  
  <label for="insNo">Vehicle Insurance Policy Number:</label>
  <input type="text" id="insNo" name="insNo" placeholder="ABC123456789"/><br/>
  
  <label for="insExpDate">Vehicle Insurance Expiration Date:</label>
  <input type="date" id="insExpDate" name="insExpDate"/><br/>
  
  <button type="submit">Save</button>
</form>

      
    </div>
  )
}

export default MyVehicles
