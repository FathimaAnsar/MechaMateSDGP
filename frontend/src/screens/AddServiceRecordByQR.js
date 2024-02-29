import React from 'react';

function AddServiceRecordByQR(props) {
    return (
        <>
            <h2>Add Service Record by QR</h2>
            // Content and form for adding service records by scanning QR
            <button onClick={() => props.app.changePage('ManageMyDocuments')}>Go Back</button>
        </>
    );
}

export default AddServiceRecordByQR;