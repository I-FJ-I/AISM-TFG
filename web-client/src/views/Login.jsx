import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useFhir } from '../context/FhirContext'; 

const Login = () => {
  const [credentials, setCredentials] = useState({ username: '', password: '' });
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  
  const { authenticate } = useFhir(); 

  const handleChange = (e) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setError(null);

    try {
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(credentials)
      });

      if (!response.ok) throw new Error('Credenciales incorrectas');

      const token = await response.text();
      
      authenticate(token);
      
      navigate('/'); 
      
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="container mt-5" style={{ maxWidth: '400px' }}>
      <div className="card shadow border-0">
        <div className="card-header bg-primary text-white text-center">
          <h4>Acceso AISM</h4>
        </div>
        <div className="card-body">
          {error && <div className="alert alert-danger">{error}</div>}
          <form onSubmit={handleLogin}>
            <div className="mb-3">
              <label>Usuario</label>
              <input type="text" name="username" className="form-control" onChange={handleChange} required />
            </div>
            <div className="mb-3">
              <label>Contraseña</label>
              <input type="password" name="password" className="form-control" onChange={handleChange} required />
            </div>
            <button type="submit" className="btn btn-primary w-100">Iniciar Sesión</button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;