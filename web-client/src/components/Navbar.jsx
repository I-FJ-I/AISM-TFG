import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  
  const handleExport = () => {
    alert("Exportando datos...");
    window.location.href = '/omop/etl/export';
  };

  return (
    <nav
      className="navbar navbar-expand-lg navbar-dark sticky-top"
      style={{ backgroundColor: 'var(--color-texto)' }}
    >
      <div className="container-fluid">
        <Link className="navbar-brand fw-bold" to="/">
          <span style={{ fontVariant: 'small-caps' }}>AISM</span>
        </Link>

        <Link className="navbar-brand fw-bold" to="/search">
          <span style={{ fontVariant: 'small-caps' }}>Buscar Pacientes</span>
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <button 
                onClick={handleExport}
                className="btn btn-outline-light btn-sm mt-2 mt-lg-0"
              >
                <i className="bi bi-download me-1"></i> Exportar Datos
              </button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;