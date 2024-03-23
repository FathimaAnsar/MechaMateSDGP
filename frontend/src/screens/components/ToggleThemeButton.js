import React from 'react';
import { Button } from 'react-bootstrap';
import ThemeContext from './ThemeContext';

function ToggleThemeButton() {
  const { theme, toggleTheme } = React.useContext(ThemeContext);

  return (
    <Button
      variant={theme === 'light' ? 'light' : 'dark'}
      onClick={toggleTheme}
    >
      {theme === 'light' ? '☀️' : '🌑'}
    </Button>
  );
}

export default ToggleThemeButton;