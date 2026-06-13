import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useFhir } from '../context/FhirContext';

const PatientDetail = () => {
  const { id } = useParams();
  
  const { client } = useFhir();

  const [patient, setPatient] = useState(null);
  const [conditions, setConditions] = useState([]);
  const [observations, setObservations] = useState([]);
  
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [isEditing, setIsEditing] = useState(false);
  const [saving, setSaving] = useState(false);
  const [editForm, setEditForm] = useState({
    family: '',
    given: '',
    gender: '',
    birthDate: '',
    active: true
  });

  useEffect(() => {
    const fetchPatientData = async () => {
      if (!client) return; 
      setLoading(true);
      try {
        const [patientData, condBundle, obsBundle] = await Promise.all([
          client.request(`Patient/${id}`),
          client.request(`Condition?patient=${id}`).catch(() => ({ entry: [] })), 
          client.request(`Observation?patient=${id}`).catch(() => ({ entry: [] }))
        ]);

        setPatient(patientData);
        
        setConditions(condBundle.entry ? condBundle.entry.map(e => e.resource) : []);
        setObservations(obsBundle.entry ? obsBundle.entry.map(e => e.resource) : []);

      } catch (err) {
        console.error("Error cargando los datos del paciente:", err);
        setError("Error al cargar el historial clínico del paciente.");
      } finally {
        setLoading(false);
      }
    };

    fetchPatientData();
  }, [id, client]); 


  const handleEditClick = () => {
    setEditForm({
      family: patient.name?.[0]?.family || '',
      given: patient.name?.[0]?.given?.[0] || '',
      gender: patient.gender || '',
      birthDate: patient.birthDate || '',
      active: patient.active !== false
    });
    setIsEditing(true);
  };

  const handleCancelEdit = () => {
    setIsEditing(false);
  };

  const handleSave = async () => {
    setSaving(true);
    try {
      const updatedPatient = { ...patient };

      if (!updatedPatient.name) updatedPatient.name = [{}];
      updatedPatient.name[0].family = editForm.family;
      if (!updatedPatient.name[0].given) updatedPatient.name[0].given = [];
      
      updatedPatient.name[0].given = editForm.given.split(' ').filter(n => n.trim() !== '');

      updatedPatient.gender = editForm.gender;
      updatedPatient.birthDate = editForm.birthDate;
      updatedPatient.active = editForm.active;

      const savedPatient = await client.update(updatedPatient);
      
      setPatient(savedPatient);
      setIsEditing(false);
    } catch (err) {
      console.error(err);
      alert('Error al guardar los cambios del paciente.');
    } finally {
      setSaving(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setEditForm(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  if (loading) return <div className="container mt-5"><h4>Cargando historial clínico...</h4></div>;
  if (error) return <div className="container mt-5"><h4 className="text-danger">{error}</h4></div>;
  if (!patient) return <div className="container mt-5"><h4>Paciente no encontrado</h4></div>;

  let familyName = 'Sin apellidos';
  let givenName = 'Sin nombre';

  if (patient.name && patient.name.length > 0) {
    familyName = patient.name[0].family || familyName;
    
    if (Array.isArray(patient.name[0].given)) {
      givenName = patient.name[0].given.join(' ');
    } else if (typeof patient.name[0].given === 'string') {
      givenName = patient.name[0].given;
    }
  }
  const fullName = `${familyName}, ${givenName}`;

  let race = "Desconocida";
  if (patient.extension && Array.isArray(patient.extension)) {
    const raceExtension = patient.extension.find(
      ext => ext.url === "http://hl7.org/fhir/us/core/StructureDefinition/us-core-race"
    );
    const textExtension = raceExtension?.extension?.find(e => e.url === "text");
    if (textExtension) {
      race = textExtension.valueString || "Desconocida";
    }
  }
  
  const isDeceased = patient.deceasedDateTime != null || patient.active === false;

  return (
    <div className="container mt-5 mb-5">
      
      <div className="card shadow mb-4 border-0">
        <div className={`card-header text-white d-flex justify-content-between align-items-center ${isDeceased ? 'bg-secondary' : 'bg-primary'}`}>
          <h3 className="mb-0">
            <i className="bi bi-person-badge me-2"></i>
            {isEditing ? 'Editar Paciente' : `Paciente: ${fullName}`}
          </h3>
          {!isEditing && (
            <button className="btn btn-light btn-sm fw-bold" onClick={handleEditClick}>
              <i className="bi bi-pencil-square me-1"></i> Editar
            </button>
          )}
        </div>
        
        <div className="card-body">
          {isEditing ? (
            <div className="row g-3">
              <div className="col-md-6">
                <label className="form-label">Apellidos (Family)</label>
                <input type="text" className="form-control" name="family" value={editForm.family} onChange={handleInputChange} />
              </div>
              <div className="col-md-6">
                <label className="form-label">Nombre (Given)</label>
                <input type="text" className="form-control" name="given" value={editForm.given} onChange={handleInputChange} />
              </div>
              <div className="col-md-4">
                <label className="form-label">Género</label>
                <select className="form-select" name="gender" value={editForm.gender} onChange={handleInputChange}>
                  <option value="male">Masculino</option>
                  <option value="female">Femenino</option>
                  <option value="unknown">Desconocido</option>
                </select>
              </div>
              <div className="col-md-4">
                <label className="form-label">Fecha de Nacimiento</label>
                <input type="date" className="form-control" name="birthDate" value={editForm.birthDate} onChange={handleInputChange} />
              </div>
              <div className="col-md-4 d-flex align-items-end">
                <div className="form-check mb-2">
                  <input className="form-check-input" type="checkbox" name="active" id="activeCheck" checked={editForm.active} onChange={handleInputChange} />
                  <label className="form-check-label" htmlFor="activeCheck">
                    Paciente Activo
                  </label>
                </div>
              </div>
              <div className="col-12 mt-4 d-flex justify-content-end gap-2">
                <button className="btn btn-secondary" onClick={handleCancelEdit} disabled={saving}>Cancelar</button>
                <button className="btn btn-success" onClick={handleSave} disabled={saving}>
                  {saving ? 'Guardando...' : 'Guardar Cambios'}
                </button>
              </div>
            </div>
          ) : (
            <div className="row">
              <div className="col-md-6">
                <p><strong>ID Sistema:</strong> {patient.id}</p>
                <p><strong>Identificador OMOP:</strong> {patient.identifier?.[0]?.value || 'N/A'}</p>
                <p><strong>Género:</strong> <span className="badge bg-info text-dark">{patient.gender}</span></p>
              </div>
              <div className="col-md-6">
                <p><strong>Fecha de nacimiento:</strong> {patient.birthDate}</p>
                <p><strong>Raza:</strong> {race}</p>
                <p>
                  <strong>Estado: </strong> 
                  {isDeceased ? (
                    <span className="badge bg-danger">Fallecido ({patient.deceasedDateTime})</span>
                  ) : (
                    <span className="badge bg-success">Activo</span>
                  )}
                </p>
              </div>
            </div>
          )}
        </div>
      </div>

      <div className="row">
        <div className="col-md-6 mb-4">
          <div className="card shadow h-100 border-0">
            <div className="card-header bg-warning text-dark fw-bold">
              <i className="bi bi-clipboard2-pulse me-2"></i>
              Diagnósticos Clínicos ({conditions.length})
            </div>
            <ul className="list-group list-group-flush" style={{ maxHeight: '400px', overflowY: 'auto' }}>
              {conditions.length === 0 ? (
                <li className="list-group-item text-muted">No hay diagnósticos registrados.</li>
              ) : (
                conditions.map((cond, index) => (
                  <li key={index} className="list-group-item">
                    <strong>{cond.code?.text || 'Diagnóstico desconocido'}</strong>
                    <br />
                    <small className="text-muted">Fecha de inicio: {cond.onsetDateTime || 'Desconocida'}</small>
                  </li>
                ))
              )}
            </ul>
          </div>
        </div>

        <div className="col-md-6 mb-4">
          <div className="card shadow h-100 border-0">
            <div className="card-header bg-success text-white fw-bold">
              <i className="bi bi-activity me-2"></i>
              Observaciones y Medidas ({observations.length})
            </div>
            <ul className="list-group list-group-flush" style={{ maxHeight: '400px', overflowY: 'auto' }}>
              {observations.length === 0 ? (
                <li className="list-group-item text-muted">No hay medidas registradas.</li>
              ) : (
                observations.map((obs, index) => (
                  <li key={index} className="list-group-item d-flex justify-content-between align-items-center">
                    <div>
                      <strong>{obs.code?.text || 'Observación'}</strong>
                      <br />
                      <small className="text-muted">Fecha: {obs.effectiveDateTime || 'Desconocida'}</small>
                    </div>
                    {obs.valueQuantity && (
                      <span className="badge bg-light text-dark border fs-6">
                        {obs.valueQuantity.value} {obs.valueQuantity.unit}
                      </span>
                    )}
                  </li>
                ))
              )}
            </ul>
          </div>
        </div>
      </div>

    </div>
  );
};

export default PatientDetail;