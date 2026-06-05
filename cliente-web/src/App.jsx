import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Navbar from './components/Navbar';
import Footer from './components/Footer';

import Home from './views/Home';
import SearchPatients from './views/SearchPatients';
import PatientDetail from './views/PatientDetail';
import NotFound from './views/NotFound';

function App() {
  return (
    <Router>
        <Navbar />
        <main className="flex-shrink-0">
            <Routes>

            <Route path="/" element={<Home />} />
            <Route path="/search" element={<SearchPatients />} />
            <Route path="/patient/:id" element={<PatientDetail />} />
            
            {/* 404 */}
            <Route path="*" element={<NotFound />} />
            </Routes>
        </main>
        <Footer />
    </Router>
  );
}

export default App;
