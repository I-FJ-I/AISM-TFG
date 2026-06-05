import React, { useState, useEffect } from 'react';
import axios from 'axios';

import PatientCard from '../components/PatientCard.jsx';

const PatientList = () => {
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get('/api/fhir/Patient')
      .then(response => {
        const entries = response.data.entry || [];
        setPatients(entries);
        setLoading(false);
      })
      .catch(err => {
        setError('Error al cargar los pacientes: ' + err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Cargando pacientes...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div>
      <h2>Lista de Pacientes (FHIR)</h2>
      {patients.length === 0 ? (
        <p>No hay pacientes registrados.</p>
      ) : (
        <ul>
          {patients.map((entry) => {
            return (
            <div className="container mt-4">
                {patients.map((entry) => (
                    <div key={entry.resource.id} className="col-12 col-md-6 col-lg-4">
                    <PatientCard patient={entry.resource} />
                    </div>
                ))}
            </div>
            );
          })}
        </ul>
      )}
    </div>
  );
};

export default PatientList;