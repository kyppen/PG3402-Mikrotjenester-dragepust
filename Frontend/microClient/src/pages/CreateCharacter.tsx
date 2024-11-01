import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, TextField, MenuItem, Select, Button, Typography, Box } from '@mui/material';

const CreateCharacter: React.FC = () => {
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [species, setSpecies] = useState('Human');
    const [profession, setProfession] = useState('Warrior');

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const newCharacter = {
            id: Date.now().toString(),
            name,
            species,
            profession,
        };

        const savedCharacters = localStorage.getItem('characters');
        const characters = savedCharacters ? JSON.parse(savedCharacters) : [];
        characters.push(newCharacter);
        localStorage.setItem('characters', JSON.stringify(characters));

        navigate('/character-menu');
    };

    return (
        <Container maxWidth="sm" sx={{ mt: 4 }}>
            <Typography variant="h5" align="center" gutterBottom>
                Create New Character
            </Typography>
            <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <TextField
                    label="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />

                <Select
                    value={species}
                    onChange={(e) => setSpecies(e.target.value)}
                    displayEmpty
                    required
                >
                    <MenuItem value="Menneske">Menneske</MenuItem>
                    <MenuItem value="Alv">Alv</MenuItem>
                    <MenuItem value="Dverg">Dverg</MenuItem>

                </Select>

                <Select
                    value={profession}
                    onChange={(e) => setProfession(e.target.value)}
                    displayEmpty
                    required
                >
                    <MenuItem value="Jeger">Jeger</MenuItem>
                    <MenuItem value="Shaman">Shaman</MenuItem>
                    <MenuItem value="Trubadur">Trubadur</MenuItem>

                </Select>

                <Button variant="contained" color="primary" type="submit">
                    Save Character
                </Button>
            </Box>
        </Container>
    );
};

export default CreateCharacter;

