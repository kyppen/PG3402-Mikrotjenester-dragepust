import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {Container, Card, CardContent, Typography, Button, Grid, CardMedia, Box, IconButton} from '@mui/material';
import HighlightOffIcon from "@mui/icons-material/HighlightOff";

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
        // Fetch campaigns from the backend API
        fetch(`http://localhost:8087/campaign/${userId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to fetch characters');
                }
                return response.json();
            })
            .then((data) => {
                setCampaigns(data);
                localStorage.setItem('characters', JSON.stringify(data));
            })
            .catch((err) => {
                setError(err.message);
                console.error('Error fetching characters:', err);
            });
    }, []);

    const handleCampaignDelete = async (campaignId: string) => {
        console.log("DELETE?");
        console.log(campaignId);
        try {
            const response = await fetch(`http://localhost:8087/campaign/delete/${campaignId}`, {
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
            console.error("Error deleting campaign", err);
        }

        window.location.reload();
    };


    const handleNewCampaign = () => {
        navigate('/new-campaign');
    }
    const switchToCharacterMenu = () => {
        navigate('/character-menu');
    }
    const handleCampaignClick = (campaignId: string) => {
        navigate(`/campaign/${campaignId}`);
    };
    const getCookie = (name: string): string | null => {
        const match = document.cookie.match(new RegExp(`(^| )${name}=([^;]+)`));
        return match ? match[2] : null;
    };console.log(getCookie("userId"));
    const userId = getCookie("userId");
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
                                position: 'relative', // Required for positioning the delete button
                                cursor: 'pointer',
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                padding: 2,
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

                                            handleCampaignDelete(campaign.id)
                                        }}}
                                >
                                    <HighlightOffIcon />
                                </IconButton>
                            </Box>
                            <CardMedia
                                component="img"
                                image="./src/assets/campaign.png"
                                alt="Campaign landscape image"
                                sx={{ width: '100%', height: 150, borderRadius: 1 }}
                                onClick={() => handleCampaignClick(campaign.id)}
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
