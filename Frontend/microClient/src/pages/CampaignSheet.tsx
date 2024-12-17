import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {Button, Container, Grid, Card, CardContent, Typography, TextField, CardMedia, Divider} from "@mui/material";


interface Campaign {
    id: string;
    campaignName: string;
    campaignDescription: string;
}
interface Character {
    id: string;
    characterName: string;
    species: string;
    profession: string;
}
interface Message {
    id: string;
    campaignId: string;
    characterId: string;
    message: string;
}


const CampaignSheet: React.FC = () => {
    const navigate = useNavigate();
    const { campaignId } = useParams<{ campaignId: string }>();
    const [campaign, setCampaign] = useState<Campaign| null>(null);
    const [error, setError] = useState<string | null>(null);
    const [characters, setCharacters] = useState<Character[]>([]);
    const [chatMessages, setChatMessages] = useState<Message[]>([]);
    const [newMessage, setNewMessage] = useState<string>("");

    useEffect(() => {
        // Fetch characters from the backend API
        fetch(`http://localhost:8087/campaign/characterinfo/${campaignId}`)
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
    useEffect(() => {
        const fetchCampaign = async () => {
            if (!campaignId) {
                console.error("No campaignId provided");
                return;
            }
            try{
                console.log("idk");
                console.log(campaignId)
                console.log(`http://localhost:8087/campaign/campaign/${campaignId}`);
                const response = await fetch(`http://localhost:8087/campaign/campaign/${encodeURIComponent(campaignId)}`);
                console.log("response");
                if (!response.ok) throw new Error('Failed to fetch campaign details')
                const data = await response.json();
                console.log(data);
                setCampaign(data);
            }catch (err){
                setError(err instanceof Error ? err.message : "An unknown error occured");
            }
        };
        fetchCampaign()
    }, [campaignId]);

    useEffect(() => {
        // Fetch chat messages from the backend API
        const fetchChatMessages = async () => {
            try {
                const response = await fetch(`http://localhost:8087/campaign/chat/${campaignId}`);
                if (!response.ok) throw new Error('Failed to fetch chat messages');
                const data = await response.json();
                setChatMessages(data);
                console.log(data);
            } catch (err) {
                console.error('Error fetching chat messages:', err);
            }
        };
        const interval = setInterval(fetchChatMessages, 20000);
        fetchChatMessages();
        return () => clearInterval(interval);
    }, [campaignId]);


    const handleCharacterClick = (characterId: string) => {
        navigate(`/character/${characterId}`);
    };
    const handleSendMessage = async () => {
        if (newMessage.trim() === "") return;

        try {
            const newMessageObj = {
                campaignId: campaignId || "",
                message: newMessage.trim(),
            };

            const response = await fetch(`http://localhost:8087/messages/message`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(newMessageObj),
            });
            window.location.reload();
            if (!response.ok) {
                throw new Error(`Failed to send message. Status: ${response.status}`);
            }

            setNewMessage("");
        } catch (err) {
            console.error("Error sending message:", err);
        }
    };
    const getCharacterImage = (species: string): string => {
        const speciesImages: { [key: string]: string } = {
            alv: '../src/assets/elf1.jpg',
            menneske: '../src/assets/human1.jpg',
            dverg: '../src/assets/dwarf1.jpg',

        };
        return speciesImages[species.toLowerCase()] || '../src/assets/elf.png'; // Default image fallback
    };
    const getCookie = (name: string): string | null => {
        const match = document.cookie.match(new RegExp(`(^| )${name}=([^;]+)`));
        return match ? match[2] : null;
    };console.log(getCookie("userName"));
    const userName = getCookie("userName");
    const handleRoll = async () => {
        try {
            const payload = {
                characterId: characters[0].id || '',
                campaignId: campaign?.id || '',
                action: 'roll',
            };
            const response = await fetch('http://localhost:8087/messages/roll', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload),
            });
            window.location.reload();
            if (!response.ok) throw new Error('Failed to send roll');
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <Container maxWidth="lg" sx={{ mt: 10, display: 'flex', justifyContent: 'space-between' }}>
            {/* Info Box */}
            <Container
                maxWidth="sm"
                sx={{
                    flex: 1,
                    mr: 2,
                    padding: 2,
                    backgroundColor: '#E0C9A6',
                    borderRadius: '8px',
                    boxShadow: '0 2px 8px rgba(0,0,0,0.1)'
                }}
            >
                <Typography variant="h4" gutterBottom fontWeight="bold">
                     Campaign name: {campaign?.campaignName || 'No Campaign Available'}
                </Typography>
                <Typography variant="body1" gutterBottom color="text.secondary" fontWeight="bold">
                    {campaign?.campaignDescription || 'Join a campaign to see details here.'}
                </Typography>
                <Typography variant="h5" gutterBottom>
                    User: {userName}
                </Typography>

                {/* Character Grid */}
                <Grid container spacing={2}>
                    {characters.map((character) => (
                        <Grid item xs={12} sm={6} md={4} key={character.id}>
                            <Card
                                sx={{
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
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        sx={{ mt: 2 }}
                                        onClick={() => handleRoll()}
                                    >
                                        Roll
                                    </Button>
                                </CardContent>
                            </Card>
                        </Grid>
                    ))}
                </Grid>
                <Divider sx={{ my: 2, borderBottomWidth: '2px', borderColor: '#d2b48c' }} />
                <Button
                    variant="contained"
                    color="primary"
                    fullWidth
                    sx={{ mt: 3 }}
                    onClick={() => navigate('/campaign-menu')}
                >
                    Back to Menu
                </Button>
            </Container>

            {/* Chat Box */}
            <Container
                maxWidth="sm"
                sx={{
                    flex: 1,
                    ml: 2,
                    display: 'flex',
                    flexDirection: 'column',
                    height: '600px',
                    border: '1px solid #ccc',
                    borderRadius: '8px',
                    padding: 2,
                    backgroundColor: '#E0C9A6',
                    boxShadow: '0 2px 8px rgba(0,0,0,0.1)'
                }}
            >
                <Typography variant="h5" gutterBottom>
                    Chat Log
                </Typography>
                <div
                    style={{
                        flexGrow: 1,
                        overflowY: 'auto',
                        marginBottom: '16px',
                        padding: '8px',
                        backgroundColor: '#f9f9f9',
                        borderRadius: '4px'
                    }}
                >
                    {chatMessages.map((message, index) => (
                        <Typography
                            key={message.id}
                            variant="body1"
                            sx={{
                                mb: 1,
                                backgroundColor: index % 2 === 0 ? '#e8eaf6' : '#fff',
                                padding: 1,
                                borderRadius: 1,
                            }}
                        >
                            <strong>{userName}:</strong> {message.message}
                        </Typography>
                    ))}
                </div>
                <TextField
                    fullWidth
                    variant="outlined"
                    placeholder="Enter your message"
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                    sx={{ marginBottom: 1 }}
                />
                <Button variant="contained" color="primary" onClick={handleSendMessage}>
                    Send
                </Button>
            </Container>
        </Container>

    );
};

export default CampaignSheet;