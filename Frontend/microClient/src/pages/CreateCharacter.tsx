import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
    Container,
    TextField,
    MenuItem,
    Select,
    Button,
    Typography,
    Box,
    FormControl,
    InputLabel,
    Card, CardContent
} from '@mui/material';


const CreateCharacter: React.FC = () => {
    const navigate = useNavigate();
    const [characterName, setCharacterName] = useState('');
    const [species, setSpecies] = useState('');
    const [profession, setProfession] = useState('');
    const [itemSetId, setItemSetId] = useState('');
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const newCharacter = {
            characterName,
            species,
            profession,
            itemSetId,


        };

        try {
            const response = await fetch('http://localhost:8081/character/create', {
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
            <Card>
                <CardContent>
                    <Typography variant="h5" align="center" gutterBottom>
                        Create New Character
                    </Typography>
                    {error && (
                        <Typography color="error" align="center">
                            {error}
                        </Typography>
                    )}
                    <Box
                        component="form"
                        onSubmit={handleSubmit}
                        sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}
                    >
                        <TextField
                            color="primary"
                            label="Name"
                            value={characterName}
                            onChange={(e) => setCharacterName(e.target.value)}
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
                                <MenuItem value="Skald">Skald</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl fullWidth required>
                            <InputLabel>Equipment</InputLabel>
                            <Select
                                value={itemSetId}
                                onChange={(e) => setItemSetId(e.target.value)}
                            >
                                <MenuItem value="1">Jegerpakke</MenuItem>
                                <MenuItem value="2">Trolldomspakke</MenuItem>
                                <MenuItem value="3">Skaldepakke</MenuItem>
                            </Select>
                        </FormControl>
                        <Button variant="contained" color="primary" type="submit">
                            Save Character
                        </Button>
                    </Box>
                </CardContent>
            </Card>
        </Container>
    );
};

export default CreateCharacter;

