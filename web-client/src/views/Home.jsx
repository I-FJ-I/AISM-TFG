import React, { useEffect, useMemo, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import PatientList from '../components/PatientList.jsx';

const Home = () => {

  useEffect(() => {
    
  }, []);

  return (
    <main className="container py-4">
      <header className="mb-4 text-center text-md-start">
        <h1 className="display-6 mb-1">
          {'Bienvenido al cliente web del TFG-AISM'}
        </h1>
      </header>

      <section>
        <PatientList />
      </section>
    </main>
  );
};

export default Home;
