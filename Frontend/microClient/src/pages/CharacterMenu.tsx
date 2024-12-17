import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
    Container,
    Card,
    CardContent,
    Typography,
    Button,
    Box,
    Grid,
    CardMedia,
    TextField,
    IconButton
} from '@mui/material';
import HighlightOffIcon from '@mui/icons-material/HighlightOff';

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

        fetch(`http://localhost:8087/character/all/${userId}`)
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

    const handleCharacterDelete = async (characterId: string) => {
        console.log("DELETE?");
        console.log(characterId);
        try {
            const response = await fetch(`http://localhost:8087/character/delete/${characterId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },}
            );
            if(!response.ok) {
                throw new Error("IDK");
            }

        }catch (err){
            setError(err instanceof Error ? err.message: 'An unknown error occured');
            console.error("Error deleting character", err);
        }

        window.location.reload();
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
            console.log("Add to campaign");
            const response = await fetch('http://localhost:8087/campaign/character/add', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(addCharacterToCampaign)
            });
            if(!response.ok){
                throw new Error('Failed to add to campaign')
            }

        }catch (err){
            setError(err instanceof Error ? err.message: 'An unknown error occured');
            console.error("Error adding to campaign", err);
        }
    };
    const getCharacterImage = (species: string): string => {
        const speciesImages: { [key: string]: string } = {
            alv: './src/assets/elf1.jpg',
            menneske: './src/assets/human1.jpg',
            dverg: './src/assets/dwarf1.jpg',

        };
        return speciesImages[species.toLowerCase()] || '../src/assets/elf.png'; // Default image fallback
    };
    const getCookie = (name: string): string | null => {
        const match = document.cookie.match(new RegExp(`(^| )${name}=([^;]+)`));
        return match ? match[2] : null;
    };
    const userId = getCookie("userId");

    function removeCookie(name: string) {
        document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/`;
    }
    const handleLogout = () => {

        removeCookie('userName');
        removeCookie('userId');

        navigate('/');
    };

    return (
     <Container maxWidth="lg" sx={{ mt: 10, display: 'flex', flexDirection: 'column' }}>
            {/* Logout Button */}
            <Box sx={{ textAlign: 'right', mb: 2 }}>
                <Button
                    variant="contained"
                    color="secondary"
                    onClick={handleLogout}
                    sx={{ fontWeight: 'bold', borderRadius: 1 }}
                >
                    Log Out
                </Button>
            </Box>
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
                                position: 'relative', // Required to position the button absolutely
                                cursor: 'pointer',
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                padding: 2,
                                '&:hover': {
                                    transform: 'scale(1.05)',
                                    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
                                },
                                transition: 'transform 0.3s, box-shadow 0.3s',
                            }}
                        >
                            {/* Delete Button */}
                            <Box
                                sx={{
                                    position: 'absolute',
                                    top: 8,
                                    right: 8,
                                }}
                            >
                                <IconButton
                                    size="small"
                                    color="error"
                                    onClick={() => {
                                        if (window.confirm("Are you sure you want to delete this character?")) {
                                            handleCharacterDelete(character.id)

                                        }}}
                                >
                                    <HighlightOffIcon />
                                </IconButton>
                            </Box>

                            <CardMedia
                                component="img"
                                image={getCharacterImage(character.species)}
                                alt={`${character.species} Character Image`}
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
                                        value={campaignIds[character.id] || ''}
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
     </Container>
    );
};

export default CharacterMenu;
