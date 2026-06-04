import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Navbar from './components/Navbar';
import Footer from './components/Footer';

import Home from './views/Home';
import NotFound from './views/NotFound';

function App() {
  return (
    <Router>
        <Navbar />
        <main className="flex-shrink-0">
            <Routes>

            <Route path="/" element={<Home />} />
            
            {/* 404 */}
            <Route path="*" element={<NotFound />} />
            </Routes>
        </main>
        <Footer />
    </Router>
  );
}

export default App;
