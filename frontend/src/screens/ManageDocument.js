import React from 'react';

function ManageMyDocuments(props) {
    return (
        <>
            <h2>Manage My Documents</h2>
            <button onClick={() => props.app.changePage('AddServiceRecordsManually')}>Add Service Records Manually</button>
            <button onClick={() => props.app.changePage('AddServiceRecordByQR')}>Add Service Record by QR</button>
            <button onClick={() => props.app.changePage('Dashboard')}>Go Back</button>
        </>
    );
}

export default ManageMyDocuments;