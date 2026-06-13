import React, { useState } from 'react';

const EtlModal = ({ show, onClose }) => {
  const [files, setFiles] = useState({ patientsFile: null, conditionsFile: null, observationsFile: null });
  const [isLoading, setIsLoading] = useState(false);

  const handleUpload = async () => {
    if (!files.patientsFile || !files.conditionsFile || !files.observationsFile) {
      alert("Por favor, selecciona los tres archivos.");
      return;
    }

    setIsLoading(true);
    const formData = new FormData();
    formData.append('patientsFile', files.patientsFile);
    formData.append('conditionsFile', files.conditionsFile);
    formData.append('observationsFile', files.observationsFile);

    try {
      const res = await fetch('/omop/etl/load', { method: 'POST', body: formData });
      if (!res.ok) throw new Error('Error en el ETL');
      alert('Proceso ETL completado.');
      onClose();
    } catch (err) {
      alert('Error: ' + err.message);
    } finally {
      setIsLoading(false);
    }
  };

  if (!show) return null;

  return (
    <div className="modal d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content">
          <div className="modal-header"><h5>Carga de datos (ETL)</h5></div>
          <div className="modal-body">
            <div className="mb-3">
              <label>Pacientes (CSV):</label>
              <input type="file" className="form-control" onChange={(e) => setFiles({...files, patientsFile: e.target.files[0]})} />
            </div>
            <div className="mb-3">
              <label>Condiciones (CSV):</label>
              <input type="file" className="form-control" onChange={(e) => setFiles({...files, conditionsFile: e.target.files[0]})} />
            </div>
            <div className="mb-3">
              <label>Observaciones (CSV):</label>
              <input type="file" className="form-control" onChange={(e) => setFiles({...files, observationsFile: e.target.files[0]})} />
            </div>
          </div>
          <div className="modal-footer">
            <button className="btn btn-secondary" onClick={onClose}>Cancelar</button>
            <button className="btn btn-primary" onClick={handleUpload} disabled={isLoading}>
              {isLoading ? 'Procesando...' : 'Iniciar Carga'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EtlModal;