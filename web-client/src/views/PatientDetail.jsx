import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const PatientDetail = () => {
  const { id } = useParams();
  const [patient, setPatient] = useState(null);

  useEffect(() => {
    axios.get(`/api/fhir/Patient/${id}`)
      .then(res => setPatient(res.data));
  }, [id]);

  if (!patient) return <div>Cargando...</div>;

  return (
    <div className="container mt-5">
      <div className="card shadow">
        <div className="card-header bg-primary text-white">
          <h3>Detalle del Paciente: {patient.id}</h3>
        </div>
        <div className="card-body">
          <p><strong>Nombre:</strong> {patient.name?.[0]?.family}, {patient.name?.[0]?.given?.[0]}</p>
          <p><strong>Género:</strong> {patient.gender}</p>
          <p><strong>Fecha de nacimiento:</strong> {patient.birthDate}</p>
        </div>
      </div>
    </div>
  );
};

export default PatientDetail;