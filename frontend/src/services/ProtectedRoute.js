import React from 'react';
import { Route, Navigate } from 'react-router-dom';

const ProtectedRoute = ({ component: Component, isSessionAvailable, ...rest }) => (
  <Route
    {...rest}
    element={isSessionAvailable ? <Component /> : <Navigate to="/signin" />}
  />
);

export default ProtectedRoute;
