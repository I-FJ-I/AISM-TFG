import React from 'react';
import { Link } from 'react-router-dom';

const NotFound = () => (
  <section className="container my-5 text-center">
    <h1 className="display-3 mb-3">404</h1>
    <p className="lead text-muted mb-4">La página que buscas no existe o ha sido movida.</p>
    <Link to="/" className="btn btn-primary">Volver al inicio</Link>
  </section>
);

export default NotFound;
