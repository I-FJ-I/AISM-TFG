import React, { useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const SearchPatients = () => {
  const [params, setParams] = useState({ identifier: '', family: '', gender: '' });
  const [patients, setPatients] = useState([]);

  const handleSearch = async (e) => {
    e.preventDefault();
    let url = '/api/fhir/Patient';
    if (params.identifier) url += `?identifier=${params.identifier}`;
    else if (params.family) url += `?family=${params.family}`;
    else if (params.gender) url += `?gender=${params.gender}`;

    const res = await axios.get(url);
    console.log(res.data);
    console.log(url);
    setPatients(res.data.entry || []);
  };

  return (
    <div className="container mt-4">
      <h2>Buscar Pacientes</h2>
      <form onSubmit={handleSearch} className="row g-3 mb-4">
        <div className="col-md-3">
          <input className="form-control" placeholder="ID Identificador" 
            onChange={(e) => setParams({...params, identifier: e.target.value})} />
        </div>
        <div className="col-md-3">
          <input className="form-control" placeholder="Apellido (Family)" 
            onChange={(e) => setParams({...params, family: e.target.value})} />
        </div>
        <button type="submit" className="btn btn-primary">Buscar</button>
      </form>

      <div className="list-group">
        {patients.map(p => (
          <Link key={p.resource.id} to={`/patient/${p.resource.id}`} className="list-group-item list-group-item-action">
            {p.resource.name?.[0]?.family}, {p.resource.name?.[0]?.given?.[0]}
          </Link>
        ))}
      </div>
    </div>
  );
};

export default SearchPatients;