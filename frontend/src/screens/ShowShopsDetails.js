import React from 'react';

function ShowShopsDetails(props) {
    return (
        <div>
            <h1>Shop Details</h1>
            {/*shoplist*/}
            <div id="shopsList">
               {/*dynamic data*/}
                <p>Details of the automobile shops will be listed here.</p>
            </div>
            <button onClick={() => props.app.changePage('SearchAutomobileShops')}>Go Back to Search</button>
        </div>
    );
}

export default ShowShopsDetails;
