import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {Button, Container, Grid, Card, CardContent, Typography, CardMedia, Divider} from "@mui/material";
import campaign from "./Campaign.tsx";

interface Campaign {
    id: string;
    campaignName: string;
    campaignDescription: string;
}
interface Character {
    characterName: string;
    species: string;
    profession: string;
}


const CampaignSheet: React.FC = () => {
    const navigate = useNavigate();
    const { campaignId } = useParams<{ campaignId: string }>();
    const [campaign, setCampaign] = useState<Campaign| null>(null);
    const [error, setError] = useState<string | null>(null);
    const [characters, setCharacters] = useState<Character[]>([]);

    useEffect(() => {
        // Fetch characters from the backend API
        //fetches characters in a certain
        fetch(`http://localhost:8085/campaign/characterinfo/${campaignId}`)
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
                console.log(`http://localhost:8085/campaign/campaign/${campaignId}`);
                const response = await fetch(`http://localhost:8085/campaign/campaign/${encodeURIComponent(campaignId)}`);
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

    const handleCharacterClick = (characterId: string) => {
        navigate(`/character/${characterId}`);
    };

    return (
        <Container maxWidth="sm" sx={{ textAlign: 'center', mt: 10 }}>
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
    );
};
export default CampaignSheet;
