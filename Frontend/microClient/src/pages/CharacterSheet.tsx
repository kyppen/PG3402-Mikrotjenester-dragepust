import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Container, Typography, Card, CardContent, Grid, Box, Button, Divider } from '@mui/material';

interface Character {
    id: string;
    characterName: string;
    species: string;
    profession: string;
    age?: string;
    health?: string;
    willpower?: string;
    skills?: string[];
    achievements?: string[];
}

const CharacterSheet: React.FC = () => {
    const { characterId } = useParams<{ characterId: string }>();
    const [character, setCharacter] = useState<Character | null>(null);
    const [equipment, setEquipment] = useState<string[]>([]);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    // Fetch character details
    useEffect(() => {
        const fetchCharacter = async () => {
            try {
                const response = await fetch(`http://localhost:8080/character/${characterId}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch character details');
                }
                const data = await response.json();
                setCharacter(data);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'An unknown error occurred');
            }
        };

        fetchCharacter();
    }, [characterId]);

    // Fetch character equipment from items service
    useEffect(() => {
        const fetchEquipment = async () => {
            try {
                const response = await fetch(`http://localhost:8080/items/${characterId}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch character equipment');
                }
                const data = await response.json();
                setEquipment(data);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'An unknown error occurred');
            }
        };

        fetchEquipment();
    }, [characterId]);

    if (error) {
        return <Typography variant="body1" align="center" color="error">{error}</Typography>;
    }

    if (!character) {
        return <Typography variant="body1" align="center">Loading character...</Typography>;
    }

    return (
        <Container maxWidth="md" sx={{ mt: 4 }}>
            <Card>
                <CardContent>
                    <Typography variant="h4" align="center" gutterBottom>
                        Character Sheet
                    </Typography>

                    {/* Header Fields */}
                    <Grid container spacing={2} sx={{ mb: 3 }}>
                        <Grid item xs={12} md={4}>
                            <Typography variant="subtitle1">Name</Typography>
                            <Typography variant="body1">{character.characterName}</Typography>
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <Typography variant="subtitle1">Species</Typography>
                            <Typography variant="body1">{character.species}</Typography>
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <Typography variant="subtitle1">Profession</Typography>
                            <Typography variant="body1">{character.profession || 'Unknown'}</Typography>
                        </Grid>
                    </Grid>

                    <Divider sx={{ my: 2 }} />

                    {/* Attributes Section */}
                    <Box sx={{ mb: 3 }}>
                        <Typography variant="h6" gutterBottom>Attributes</Typography>
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6} md={4}>
                                <Typography variant="subtitle1">Health</Typography>
                                <Typography variant="body1">{character.health || 'Not Set'}</Typography>
                            </Grid>
                            <Grid item xs={12} sm={6} md={4}>
                                <Typography variant="subtitle1">Willpower</Typography>
                                <Typography variant="body1">{character.willpower || 'Not Set'}</Typography>
                            </Grid>
                        </Grid>
                    </Box>

                    <Divider sx={{ my: 2 }} />

                    {/* Skills Section */}
                    <Box sx={{ mb: 3 }}>
                        <Typography variant="h6" gutterBottom>Skills</Typography>
                        {character.skills && character.skills.length > 0 ? (
                            <ul>
                                {character.skills.map((skill, index) => (
                                    <li key={index}>{skill}</li>
                                ))}
                            </ul>
                        ) : (
                            <Typography variant="body2">No skills listed.</Typography>
                        )}
                    </Box>

                    <Divider sx={{ my: 2 }} />

                    {/* Equipment Section */}
                    <Box sx={{ mb: 3 }}>
                        <Typography variant="h6" gutterBottom>Equipment</Typography>
                        {equipment.length > 0 ? (
                            <ul>
                                {equipment.map((item, index) => (
                                    <li key={index}>{item}</li>
                                ))}
                            </ul>
                        ) : (
                            <Typography variant="body2">No equipment listed.</Typography>
                        )}
                    </Box>

                    <Divider sx={{ my: 2 }} />

                    {/* Achievements Section */}
                    <Box>
                        <Typography variant="h6" gutterBottom>Achievements</Typography>
                        {character.achievements && character.achievements.length > 0 ? (
                            <ul>
                                {character.achievements.map((achievement, index) => (
                                    <li key={index}>{achievement}</li>
                                ))}
                            </ul>
                        ) : (
                            <Typography variant="body2">No achievements listed.</Typography>
                        )}
                    </Box>

                    <Button
                        variant="contained"
                        color="primary"
                        fullWidth
                        sx={{ mt: 3 }}
                        onClick={() => navigate('/character-menu')}
                    >
                        Back to Menu
                    </Button>
                </CardContent>
            </Card>
        </Container>
    );
};

export default CharacterSheet;
