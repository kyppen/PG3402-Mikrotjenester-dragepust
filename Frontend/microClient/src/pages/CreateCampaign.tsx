import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
    Container,
    TextField,
    MenuItem,
    Select,
    Button,
    Typography,
    Box,
    Card,
    FormControl,
    InputLabel,
    CardContent
} from '@mui/material';


const CreateCampaign: React.FC = () => {
    const navigate = useNavigate();
    const [campaignName, setCampaignName] = useState('');
    const [campaignDescription, setCampaignDescription] = useState('');
    // This will be gotten from user login
    const [userId, setUserId] = useState('');
    const [error, setError] = useState<string | null>(null);
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const newCampaign = {
            campaignName,
            campaignDescription,
            userId,
        };
        try {
            const response = await fetch('http://localhost:8087/campaign/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newCampaign),
            });
            if (!response.ok) {
                throw new Error("Failed to create campaign")
            }
            navigate("/campaign-menu");
        } catch(err) {
            setError(err instanceof Error ? err.message: "an error or something");
            console.error("Error creating character:", err);
        }
    };
    return(
        <Container maxWidth="sm" sx={{ mt: 4 }}>
            <Card>
                <CardContent>
                    <Typography variant="h5" align="center" gutterBottom>
                        Create a new Campaign
                    </Typography>
                    {error && (
                        <Typography color="error" align="center">
                            {error}
                        </Typography>
                    )}
                    <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>

                        <TextField
                            label="Campaign Name"
                            value={campaignName}
                            onChange={(e) => setCampaignName(e.target.value)}
                            required
                        />
                        <TextField
                            label="Campaign Description"
                            value={campaignDescription}
                            onChange={(e) => setCampaignDescription(e.target.value)}
                            required
                        />
                        <TextField
                            label="User id"
                            value={userId}
                            onChange={(e) => setUserId(e.target.value)}
                            required
                        >

                        </TextField>
                        <Button variant="contained" color="primary" type="submit">
                            Create Campaign
                        </Button>
                    </Box>
                </CardContent>
            </Card>
         </Container>
    );
}
export default CreateCampaign;