import { Container, TextField, MenuItem, Select, Button, Typography, Box, FormControl, InputLabel } from '@mui/material';

import React, {FormEvent, useState} from 'react';

const CreateCampaign: React.FC = () => {
    const [campaignName, setCampaignName] = useState('');
    const [description, setDescription] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const newCampaign = {
            campaignName,
            description,
        }
    };
    return(
        <Container maxWidth="sm" sx={{ mt: 4 }}>
        <Button>Hello</Button>
            this is where form goes
            <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <TextField
                    label="Campaign Name"
                    value={campaignName}
                    onChange={(e) => setCampaignName(e.target.value)}
                    required
                />
                <TextField
                    label="Campaign Description"
                    value={description}
                    onChange={(e) => setSetDescription(e.target.value)}
                    required
                />
            </Box>
         </Container>
    );
}
export default CreateCampaign;