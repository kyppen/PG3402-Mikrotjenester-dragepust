import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, TextField, MenuItem, Select, Button, Typography, Box, FormControl, InputLabel } from '@mui/material';


const CreateCharacter: React.FC = () => {
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [species, setSpecies] = useState('');
    const [profession, setProfession] = useState('');
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const newCharacter = {
            name,
            species,
            profession,
        };

        try {
            const response = await fetch('http://localhost:8080/character/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newCharacter),
            });
            if (!response.ok) {
                throw new Error('Failed to create character');
            }
            navigate('/character-menu');
        } catch (err) {
            setError(err instanceof Error ? err.message : 'An unknown error occurred');
            console.error('Error creating character:', err);
        }
    };

    return (
        <Container maxWidth="sm" sx={{ mt: 4 }}>
            <Typography variant="h5" align="center" gutterBottom>
                Create New Character
            </Typography>
            {error && (
                <Typography color="error" align="center">
                    {error}
                </Typography>
            )}
            <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <TextField
                    label="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />

                <FormControl fullWidth required>
                    <InputLabel>Species</InputLabel>
                    <Select
                        value={species}
                        onChange={(e) => setSpecies(e.target.value)}

                    >
                        <MenuItem value="Menneske">Menneske</MenuItem>
                        <MenuItem value="Alv">Alv</MenuItem>
                        <MenuItem value="Dverg">Dverg</MenuItem>
                    </Select>
                </FormControl>

                <FormControl fullWidth required>
                    <InputLabel>Profession</InputLabel>
                    <Select
                        value={profession}
                        onChange={(e) => setProfession(e.target.value)}
                    >
                        <MenuItem value="Jeger">Jeger</MenuItem>
                        <MenuItem value="Shaman">Shaman</MenuItem>
                        <MenuItem value="Trubadur">Trubadur</MenuItem>
                    </Select>
                </FormControl>

                <Button variant="contained" color="primary" type="submit">
                    Save Character
                </Button>
            </Box>
        </Container>
    );
};

export default CreateCharacter;

