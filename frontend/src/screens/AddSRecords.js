import React from 'react';

function AddServiceRecordsManually(props) {
    return (
        <>
            <h2>Add Service Records Manually</h2>
            <form action="#" method="POST">
                <label for="serviceType">Service Type:</label>
                <select id="serviceType" name="serviceType">
                    <option value="WheelAlignment">Wheel Alignment</option>
                    <option value="EngineOilChange">Engine Oil Change</option>
                    <option value="BrakeFluidChange">Brake Fluid Change</option>
                    <option value="BrakeCaliperChange">Brake Caliper Change</option>
                    <option value="CoolantChange">Coolant Change</option>
                    <option value="TireChange">Tire Change</option>
                    <option value="PistonRingsChange">Piston Rings Change</option>
                    <option value="PistonChange">Piston Change</option>
                    <option value="DieselFilterChange">Diesel Filter Change</option>
                    <option value="VipersChange">Vipers Change</option>
                </select><br/>
                
                <label for="description">Description:</label><br/>
                <textarea id="description" name="description" rows="4" cols="50"></textarea><br/><br/>

                <label for="date">Service Date:</label>
                <input type="date" id="date" name="date"/><br/><br/>

                <label for="mileage">Mileage:</label>
                <input type="number" id="mileage" name="mileage"/><br/><br/>

                <input type="submit" value="Submit"/>
            </form>

            <button onClick={() => props.app.changePage('ManageMyDocuments')}>Go Back</button>
        </>
    );
}
export default AddServiceRecordsManually;