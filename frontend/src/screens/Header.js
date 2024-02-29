import React from 'react';

function Header() {
  
  const toggleDropdown = () => {
    var dropdown = document.querySelector('.dropdown');
    dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
  };

  
  const handleDropdownItemClick = (action) => {
    
    console.log("Clicked:", action);
    
    var dropdown = document.querySelector('.dropdown');
    dropdown.style.display = 'none';
  };

  return (
    <>
      <div id="main-header">
        <div id="main-logo">Logo Mechamate</div>

        <input type="checkbox" id="notify-checkbox"></input>
        <label htmlFor="notify-checkbox">Notifications</label>

        <input type="checkbox" id="menu-checkbox" onChange={toggleDropdown}></input>
        <label htmlFor="menu-checkbox">Menu button</label>
      </div>

      
      <div className="dropdown">
        <button className="dropdown-item" onClick={() => handleDropdownItemClick('Settings')}>Settings</button>
        <button className="dropdown-item" onClick={() => handleDropdownItemClick('About App')}>About App</button>
        <button className="dropdown-item" onClick={() => handleDropdownItemClick('Sign Out')}>Sign Out</button>
      </div>
    </>
  );
}

export default Header;
