import React, { createContext, useContext, useState } from 'react';
import FHIR from 'fhirclient';

const FhirContext = createContext();

export const useFhir = () => useContext(FhirContext);

export const FhirProvider = ({ children }) => {
  const [client, setClient] = useState(() => {
    const token = localStorage.getItem('jwt_token');
    if (token) {
      return FHIR.client({
        serverUrl: "http://localhost:8091/fhir", 
        tokenResponse: { access_token: token }
      });
    }
    return null;
  });

  const authenticate = (token) => {
    localStorage.setItem('jwt_token', token);
    const newClient = FHIR.client({
      serverUrl: "http://localhost:8091/fhir",
      tokenResponse: { access_token: token }
    });
    setClient(newClient);
  };

  const logout = () => {
    localStorage.removeItem('jwt_token');
    setClient(null);
  };

  return (
    <FhirContext.Provider value={{ client, authenticate, logout }}>
      {children}
    </FhirContext.Provider>
  );
};