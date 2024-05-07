import React from 'react';
import { useState,useEffect } from 'react'
import { Navigate, useNavigate } from 'react-router-dom';

const ProtectedRoute = ({ component: Component, token, ...rest }) => {
  return (
    token ? <Component {...rest} /> : <Navigate to={"/login"} replace />
  );
};

export default ProtectedRoute;