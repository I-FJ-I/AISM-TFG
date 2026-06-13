import React, { useState, useEffect, useCallback } from 'react';
import { useFhir } from '../context/FhirContext';
import PatientCard from '../components/PatientCard.jsx';

const PatientList = () => {
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const { client } = useFhir();

  const handleSyncOmop = async () => {
    if (!client) return;

    setLoading(true);
    try {
      const res = await client.request("Patient/$sync-omop");
      const pacientesSincronizados = res.entry || [];
      setPatients(pacientesSincronizados);

    } catch (error) {
      console.error("Error al sincronizar con OMOP:", error);
      alert("Hubo un error durante la sincronización.");
    } finally {
      setLoading(false);
    }
  };

  const fetchPatients = useCallback(async () => {
    if (!client) return; 

    setLoading(true);
    setError(null);
    try {
      const bundle = await client.request('Patient');
      
      setPatients(bundle.entry || []);
    } catch (err) {
      setError('Error al cargar la lista de pacientes: ' + err.message);
    } finally {
      setLoading(false);
    }
  }, [client]);

  useEffect(() => {
    fetchPatients();
  }, [fetchPatients]);

  if (loading) return <div className="container mt-4"><p>Cargando pacientes...</p></div>;
  if (error) return <div className="container mt-4"><p className="text-danger">{error}</p></div>;

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Lista de Pacientes (FHIR)</h2>
        <div>
          <button className="btn btn-primary" onClick={handleSyncOmop}>
            <i className="bi bi-arrow-clockwise me-1"></i> Recargar Pacientes
          </button>
        </div>
      </div>

      {patients.length === 0 ? (
        <p className="text-muted">No hay pacientes registrados.</p>
      ) : (
        <div className="row">
          {patients.map((entry) => (
            <div key={entry.resource.id} className="col-12 col-md-6 col-lg-4 mb-4">
              <PatientCard patient={entry.resource} />
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default PatientList;