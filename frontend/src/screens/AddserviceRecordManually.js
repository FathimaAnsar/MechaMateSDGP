import React from 'react';

function AddServiceRecordsManually(props) {
    return (
        <>
            <h2>Add Service Records Manually</h2>
            // Content and form for adding service records manually
            <button onClick={() => props.app.changePage('ManageMyDocuments')}>Go Back</button>
        </>
    );
}
export default AddServiceRecordsManually;