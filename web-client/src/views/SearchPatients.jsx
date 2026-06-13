import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useFhir } from '../context/FhirContext';

const SearchPatients = () => {
  const [params, setParams] = useState({ identifier: '', family: '', gender: '' });
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(false);

  const { client } = useFhir();

  const handleSearch = async (e) => {
    e.preventDefault();
    
    if (!client) {
      alert("Tu sesión ha expirado o no tienes permisos.");
      return;
    }

    setLoading(true);
    
    const query = new URLSearchParams();
    if (params.identifier) query.append('identifier', params.identifier);
    if (params.family) query.append('family', params.family);
    if (params.gender) query.append('gender', params.gender);

    const url = `Patient?${query.toString()}`;

    try {
      const res = await client.request(url);
      
      setPatients(res.entry || []);
    } catch (error) {
      if (error.status === 404) {
        setPatients([]);
        return;
      }
      console.error("Error en la búsqueda:", error);
      alert("Hubo un error al buscar los pacientes");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mt-4">
      <h2>Buscar Pacientes</h2>
      <form onSubmit={handleSearch} className="row g-3 mb-4 align-items-end">
        <div className="col-md-3">
          <label className="form-label">Identificador</label>
          <input 
            className="form-control" 
            value={params.identifier}
            onChange={(e) => setParams({...params, identifier: e.target.value})} 
          />
        </div>
        <div className="col-md-3">
          <label className="form-label">Apellido</label>
          <input 
            className="form-control" 
            value={params.family}
            onChange={(e) => setParams({...params, family: e.target.value})} 
          />
        </div>
        <div className="col-md-3">
          <label className="form-label">Género</label>
          <select 
            className="form-select" 
            value={params.gender}
            onChange={(e) => setParams({...params, gender: e.target.value})}
          >
            <option value="">Cualquiera</option>
            <option value="male">Masculino</option>
            <option value="female">Femenino</option>
            <option value="unknown">Desconocido</option>
          </select>
        </div>
        <div className="col-md-3">
          <button type="submit" className="btn btn-primary w-100" disabled={loading}>
            {loading ? 'Buscando...' : 'Buscar'}
          </button>
        </div>
      </form>

      <div className="list-group">
        {patients.length === 0 && !loading && <p className="text-muted">No se encontraron resultados.</p>}
        {patients.map(p => {
          const resource = p.resource;
          
          const family = resource.name?.[0]?.family || 'Sin apellidos';
          let given = 'Sin nombre';
          if (Array.isArray(resource.name?.[0]?.given)) {
            given = resource.name[0].given.join(' ');
          } else if (typeof resource.name?.[0]?.given === 'string') {
            given = resource.name[0].given;
          }

          return (
            <Link key={resource.id} to={`/patient/${resource.id}`} className="list-group-item list-group-item-action">
              <strong>{family}</strong>, {given} 
              <span className="badge bg-secondary ms-2">{resource.gender || 'Desconocido'}</span>
            </Link>
          );
        })}
      </div>
    </div>
  );
};

export default SearchPatients;