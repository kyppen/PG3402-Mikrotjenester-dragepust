import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Card, CardContent, Typography, Button, Grid, CardMedia } from '@mui/material';

interface Campaign {
    id: string;
    campaignName: string;
    campaignDescription: string;
}

const CampaignMenu: React.FC = () => {
    const navigate = useNavigate();
    const [campaigns, setCampaigns] = useState<Campaign[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // Fetch characters from the backend API
        // Change "1" to a userId from login
        fetch('http://localhost:8087/campaign/' + "1")
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to fetch characters');
                }
                return response.json();
            })
            .then((data) => {
                setCampaigns(data);
                localStorage.setItem('characters', JSON.stringify(data)); // Cache data in localStorage
            })
            .catch((err) => {
                setError(err.message);
                console.error('Error fetching characters:', err);
            });
    }, []);

    const handleNewCampaign = () => {
        navigate('/new-campaign');
    }
    const switchToCharacterMenu = () => {
        navigate('/character-menu');
    }
    const handleCampaignClick = (campaignId: string) => {
        navigate(`/campaign/${campaignId}`);
    };

    return (
        <Container maxWidth="md">
            <Typography variant="h5" align="center" gutterBottom>
                Your Campaigns
            </Typography>
            {error && (
                <Typography color="error" align="center">
                    {error}
                </Typography>
            )}
            <Button variant="contained" color="primary" fullWidth onClick={switchToCharacterMenu} sx={{ mb: 3 }}>
                Character Menu
            </Button>
            <Button variant="contained" color="primary"  fullWidth onClick={handleNewCampaign} sx={{ mb:3}}>
                Create New Campaign
            </Button>
            <Grid container spacing={2}>
                {campaigns.map((campaign) => (
                    <Grid item xs={12} sm={6} md={4} lg={3} key={campaign.id}>
                        <Card
                            sx={{
                                cursor: 'pointer',
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                padding: 2
                            }}
                            onClick={() => handleCampaignClick(campaign.id)}
                        >
                            <CardMedia
                                component="img"
                                image="./src/assets/campaign.png"
                                alt="Campaign landscape image"
                                sx={{ width: '100%', height: 150, borderRadius: 1 }}
                            />
                            <CardContent sx={{ textAlign: 'center' }}>
                                <Typography variant="h6" gutterBottom>
                                    {campaign.campaignName}
                                </Typography>
                                <Typography color="textSecondary">
                                    {campaign.campaignDescription}
                                </Typography>
                                <Typography color="textSecondary">
                                    {campaign.id}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default CampaignMenu;
