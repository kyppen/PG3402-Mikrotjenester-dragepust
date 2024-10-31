import React from 'react';
import { useNavigate } from 'react-router-dom';

const LoginPage: React.FC = () => {
    const navigate = useNavigate();

    const handleLogin = () => {
        navigate('/character-menu');
    };

    return (
        <div className="login-page">
            <h1>Welcome to the Dragon Creator</h1>
            <button onClick={handleLogin}>Log In</button>
        </div>
    );
};

export default LoginPage;