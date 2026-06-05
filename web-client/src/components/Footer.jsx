import React from 'react';

const Footer = () => (
  <footer
    className="py-4 text-white mt-auto"
    style={{ backgroundColor: 'var(--color-texto)' }}
  >
    <div className="container">
      <div className="row gy-3 align-items-center">
        <div className="col-12 col-md-6 text-center text-md-start">
          <h5 className="mb-1" style={{ fontVariant: 'small-caps' }}>AISM</h5>
          <p className="mb-0 small text-white-50">
            Arquitectura de Interoperabilidad en Sistemas Médicos
          </p>
        </div>
        <div className="col-12 col-md-6 text-center text-md-end small text-white-50">
          <p className="mb-1">Facultad de Informática · Universidad de Murcia</p>
          <p className="mb-0">© {new Date().getFullYear()} Trabajo Fin de Grado</p>
        </div>
      </div>
    </div>
  </footer>
);

export default Footer;
