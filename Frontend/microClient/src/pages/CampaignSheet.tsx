import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {Button, Container, Grid, Card, CardContent, Typography,TextField, CardMedia, Divider} from "@mui/material";


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
        //fetches characters in a certain
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
                message: newMessage.trim(), // Trim the message to remove extra spaces
            };

            const response = await fetch(`http://localhost:8087/messages/message`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(newMessageObj),
            });

            if (!response.ok) {
                throw new Error(`Failed to send message. Status: ${response.status}`);
            }

            // Parse the backend's response
            const savedMessage = await response.json();

            // Update the chat messages state with the saved message from the backend
            //setChatMessages((prevMessages) => [...prevMessages, savedMessage]);
            setNewMessage(""); // Clear the input field after successful sending
        } catch (err) {
            console.error("Error sending message:", err);
        }
    };
    return (
        <Container maxWidth="lg" sx={{ textAlign: 'center', mt: 10, display: 'flex' }}>
            <Container maxWidth="sm">
                <Typography variant="h4" gutterBottom>
                    {campaign?.campaignName}
                </Typography>
                <Typography variant="h4" gutterBottom>
                    {campaign?.campaignDescription}
                </Typography>

                {/* Header Fields */}
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
                                <Divider sx={{ my: 2, borderBottomWidth: '2px', borderColor: '#d2b48c' }} />
                                <CardMedia
                                    component="img"
                                    image="/src/assets/elf.png"
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

            {/* Chat Log */}
            <Container maxWidth="sm" sx={{ ml: 4, display: 'flex', flexDirection: 'column', height: '600px', border: '1px solid #ccc', borderRadius: '8px', padding: 2 }}>
                <Typography variant="h5" gutterBottom>Chat Log</Typography>
                <div style={{ flexGrow: 1, overflowY: 'auto', marginBottom: '16px', padding: '8px', backgroundColor: '#f9f9f9', borderRadius: '4px' }}>
                    {chatMessages.map((message) => (
                        <Typography key={message.id} variant="body1" sx={{ mb: 1 }}>
                            {message.message}
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
                <Button variant="contained" color="primary" onClick={handleSendMessage}>Send</Button>
            </Container>
        </Container>
    );
};

export default CampaignSheet;