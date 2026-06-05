import React from 'react';
import { useNavigate } from 'react-router-dom';


const PatientCard = ({ patient }) => {
    const navigate = useNavigate();
    
    const handleViewDetails = () => {
        navigate(`/patient/${patient.id}`);
    };

    const hospitalId = patient.identifier?.find(
        id => id.system === "http://TFG/AISM"
    )?.value || "No disponible";
    return (
        <div className="card h-100 shadow-sm border-0">
        <div className="card-body">
            <h5 className="card-title text-primary">
            <i className="bi bi-person-circle me-2"></i>
            {patient.id}
            </h5>
            <hr />
            <p className="card-text mb-1">
            <strong>Género:</strong> {patient.gender || 'No especificado'}
            </p>
            <p className="card-text mb-1">
            <strong>Nacimiento:</strong> {patient.birthDate || 'N/A'}
            </p>
            <p className="card-text">
            <strong>Estado:</strong> 
            <span className={`badge ${patient.active ? 'bg-success' : 'bg-secondary'} ms-2`}>
                {patient.active ? 'Activo' : 'Inactivo'}
            </span>
            </p>
        </div>
        <div className="card-footer bg-transparent border-0" onClick={handleViewDetails} style={{ cursor: 'pointer' }}>
            <button className="btn btn-outline-primary btn-sm w-100">
            Ver Detalles
            </button>
        </div>
        </div>
    );
};

export default PatientCard;