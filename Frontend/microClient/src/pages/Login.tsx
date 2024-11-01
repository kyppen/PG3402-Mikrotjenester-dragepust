import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Typography, Button } from '@mui/material';

const LoginPage: React.FC = () => {
    const navigate = useNavigate();

    const handleLogin = () => {
        navigate('/character-menu');
    };


    return (
        <Container maxWidth="sm" sx={{ textAlign: 'center', mt: 10 }}>
            <Typography variant="h4" gutterBottom>
                Welcome to Dragepust
            </Typography>
            <Button variant="contained" color="primary" onClick={handleLogin}>
                Log In
            </Button>
        </Container>
    );
};
 //tog er moro
export default LoginPage;
