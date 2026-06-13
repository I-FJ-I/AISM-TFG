import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import { FhirProvider, useFhir } from './context/FhirContext';

import Navbar from './components/Navbar';
import Footer from './components/Footer';

import Home from './views/Home';
import SearchPatients from './views/SearchPatients';
import PatientDetail from './views/PatientDetail';
import NotFound from './views/NotFound';
import Login from './views/Login'; 

const PrivateRoute = ({ children }) => {
  const { client } = useFhir();
  return client ? children : <Navigate to="/login" />;
};

function App() {
  return (
    <FhirProvider>
      <Router>
          <Navbar />
          <main className="flex-shrink-0">
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                
                <Route 
                  path="/search" 
                  element={
                    <PrivateRoute>
                      <SearchPatients />
                    </PrivateRoute>
                  } 
                />
                
                <Route 
                  path="/patient/:id" 
                  element={
                    <PrivateRoute>
                      <PatientDetail />
                    </PrivateRoute>
                  } 
                />
                
                <Route path="*" element={<NotFound />} />
              </Routes>
          </main>
          <Footer />
      </Router>
    </FhirProvider>
  );
}

export default App;