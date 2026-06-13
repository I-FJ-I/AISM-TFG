import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import EtlModal from '../views/EtlModal.jsx';
import { useFhir } from '../context/FhirContext';

const Navbar = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);
  
  const { client, logout } = useFhir(); 
  const navigate = useNavigate();

  const handleExport = async () => {
    setIsLoading(true);

    try {
      const token = localStorage.getItem('jwt_token');

      const response = await fetch('/omop/etl/export', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Authorization': `Bearer ${token}` 
        },
      });

      if (!response.ok) {
        throw new Error(`Error HTTP: ${response.status}`);
      }

      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
      
      const a = document.createElement('a');
      a.style.display = 'none';
      a.href = url;
      a.download = 'omop_bundle_export.json';
      
      document.body.appendChild(a);
      a.click();

      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);

    } catch (err) {
      console.error(err);
      alert('Error al exportar los datos. Comprueba la conexión con el servidor ETL o tu sesión.');
    } finally {
      setIsLoading(false);
    }
  };

  const handleLogout = () => {
    logout(); 
    navigate('/login'); 
  };

  return (
    <>
    <nav
      className="navbar navbar-expand-lg navbar-dark sticky-top"
      style={{ backgroundColor: 'var(--color-texto)' }}
    >
      <div className="container-fluid">
        <Link className="navbar-brand fw-bold" to="/">
          <span style={{ fontVariant: 'small-caps' }}>AISM</span>
        </Link>

        {client && (
          <Link className="navbar-brand fw-bold" to="/search">
            <span style={{ fontVariant: 'small-caps' }}>Buscar Pacientes</span>
          </Link>
        )}

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto gap-2">

            {!client && (
              <li className="nav-item">
                <Link className="btn btn-outline-light btn-sm w-100" to="/login">
                  <i className="bi bi-box-arrow-in-right me-1"></i> Iniciar Sesión
                </Link>
              </li>
            )}
            
            {client && (
              <>
                <li className="nav-item">
                  <button 
                    onClick={handleExport}
                    disabled={isLoading}
                    className="btn btn-outline-light btn-sm w-100"
                  >
                    {isLoading ? (
                      <>
                        <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                        Exportando...
                      </>
                    ) : (
                      <>
                        <i className="bi bi-download me-1"></i> Exportar Datos
                      </>
                    )}
                  </button>
                </li>
                
                <li className="nav-item">
                  <button className="btn btn-outline-light btn-sm w-100" onClick={() => setShowModal(true)}>
                    <i className="bi bi-upload"></i> Cargar Datos ETL
                  </button>
                </li>

                <li className="nav-item ms-lg-3 border-start border-secondary ps-lg-3">
                  <button className="btn btn-danger btn-sm w-100" onClick={handleLogout}>
                    <i className="bi bi-box-arrow-right me-1"></i> Salir
                  </button>
                </li>
              </>
            )}
            
          </ul>
        </div>
      </div>
    </nav>
      <EtlModal show={showModal} onClose={() => setShowModal(false)} />
    </>
  );
};

export default Navbar;