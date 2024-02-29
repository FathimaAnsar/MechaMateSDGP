import React from 'react'

function Header() {
    return (
        <>
            <div id="main-header">
                <div id="main-logo">Logo Mechamate</div>

                <input type="checkbox" id="notify-checkbox"></input>
                <label for="notify-checkbox">Notifications</label>

                <input type="checkbox" id="menu-checkbox"></input>
                <label for="menu-checkbox">Menu button</label>

            </div>

        </>
    )
}

export default Header