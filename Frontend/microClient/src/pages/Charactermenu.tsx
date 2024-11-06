import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Card, CardContent, Typography, Button, Grid, CardMedia } from '@mui/material';

interface Character {
    id: string;
    characterName: string;
    species: string;
    profession: string;
}

const CharacterMenu: React.FC = () => {
    const navigate = useNavigate();
    const [characters, setCharacters] = useState<Character[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // Fetch characters from the backend API
        fetch('http://localhost:8080/character/all')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to fetch characters');
                }
                return response.json();
            })
            .then((data) => {
                setCharacters(data);
                localStorage.setItem('characters', JSON.stringify(data)); // Cache data in localStorage
            })
            .catch((err) => {
                setError(err.message);
                console.error('Error fetching characters:', err);
            });
    }, []);

    const handleNewCharacter = () => {
        navigate('/new-character');
    };

    const handleCharacterClick = (characterId: string) => {
        navigate(`/character/${characterId}`);
    };

    return (
        <Container maxWidth="md">
            <Typography variant="h5" align="center" gutterBottom>
                Your Characters
            </Typography>
            {error && (
                <Typography color="error" align="center">
                    {error}
                </Typography>
            )}
            <Button variant="contained" color="primary" fullWidth onClick={handleNewCharacter} sx={{ mb: 3 }}>
                Create New Character
            </Button>
            <Grid container spacing={2}>
                {characters.map((character) => (
                    <Grid item xs={12} sm={6} md={4} lg={3} key={character.id}>
                        <Card
                            sx={{
                                cursor: 'pointer',
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                padding: 2
                            }}
                            onClick={() => handleCharacterClick(character.id)}
                        >
                            <CardMedia
                                component="img"
                                image="./src/assets/elf.png"
                                alt="Character Image"
                                sx={{ width: '100%', height: 150, borderRadius: 1 }}
                            />
                            <CardContent sx={{ textAlign: 'center' }}>
                                <Typography variant="h6" gutterBottom>
                                    {character.characterName}
                                </Typography>
                                <Typography color="textSecondary">
                                    {character.profession}
                                </Typography>
                                <Typography color="textSecondary">
                                    {character.species}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default CharacterMenu;
