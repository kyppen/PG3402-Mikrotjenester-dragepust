import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Card, CardContent, Typography, Button, Grid, CardMedia } from '@mui/material';

interface Character {
    id: string;
    name: string;
    species: string;
    profession: string;
}

const CharacterMenu: React.FC = () => {
    const navigate = useNavigate();
    const [characters, setCharacters] = useState<Character[]>([]);

    useEffect(() => {
        const savedCharacters = localStorage.getItem('characters');
        if (savedCharacters) {
            setCharacters(JSON.parse(savedCharacters));
        }
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
                                image="https://via.placeholder.com/150"  // Placeholder image URL
                                alt="Character Image"
                                sx={{ width: '100%', height: 150, borderRadius: 1 }}
                            />
                            <CardContent sx={{ textAlign: 'center' }}>
                                <Typography variant="h6" gutterBottom>
                                    {character.name}
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
