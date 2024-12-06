import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {Container, Card, CardContent, Typography, Button,Box, Grid, CardMedia, TextField} from '@mui/material';

interface Character {
    id: string;
    characterName: string;
    species: string;
    profession: string;
}

const CharacterMenu: React.FC = () => {
    const navigate = useNavigate();
    const [characters, setCharacters] = useState<Character[]>([]);
    const [campaignIds, setCampaignIds] = useState<{ [key: string]: string }>({});
    const [characterId, setCharacterId] = useState('');
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // Fetch characters from the backend API
        // THIS NEEDS TO GET BY USERID ONCE IMPLEMENTED
        fetch('http://localhost:8081/character/all')
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
    const switchToCampaignMenu = () => {
        navigate('/campaign-menu');
    }
    const handleCampaignIdChange = (characterId: string, value: string) => {
        setCampaignIds((prev) => ({
            ...prev,
            [characterId]: value,
        }));
    };

    const handleCharacterClick = (characterId: string) => {
        navigate(`/character/${characterId}`);
    };
    const handleCampaignSubmit = async (e: React.FormEvent, characterId: string) => {
        e.preventDefault()
        const addCharacterToCampaign = {
            characterId,
            campaignId: campaignIds[characterId] || '', // Use the campaignId specific to the character
        };
        console.log("characterId" + addCharacterToCampaign.characterId);
        console.log("campaignId" + addCharacterToCampaign.campaignId);

        try{
            console.log("WTF")
            const response = await fetch('http://localhost:8085/campaign/character/add', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(addCharacterToCampaign)
            });
            if(!response.ok){
                throw new Error('Failed to add to campaign')
            }
            //navigate('/character-menu')
        }catch (err){
            setError(err instanceof Error ? err.message: 'An unknown error occured');
            console.error("Error adding to campaign", err);
        }
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
            <Button variant="contained" color="primary" fullWidth onClick={switchToCampaignMenu} sx={{ mb: 3 }}>
                Campaign Menu
            </Button>
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
                            //onClick={() => handleCharacterClick(character.id)}
                        >
                            <CardMedia
                                component="img"
                                image="./src/assets/elf.png"
                                alt="Character Image"
                                sx={{ width: '100%', height: 150, borderRadius: 1 }}
                                onClick={() => handleCharacterClick(character.id)}
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
                                <Box
                                    component="form"
                                    onSubmit={(event) => handleCampaignSubmit(event, character.id)}
                                    sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}
                                >
                                    <TextField
                                        color="primary"
                                        label="Campaign ID"
                                        value={campaignIds[character.id] || ''} // Use the campaignId specific to the character
                                        onChange={(e) => handleCampaignIdChange(character.id, e.target.value)}
                                        required
                                    />
                                    <Button variant="contained" color="primary" type="submit">
                                        Add To Campaign
                                    </Button>
                                </Box>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default CharacterMenu;
