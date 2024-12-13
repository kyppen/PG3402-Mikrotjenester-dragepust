import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
    Container,
    Typography,
    Card,
    CardContent,
    Grid,
    Box,
    Button,
    Divider,
    TableContainer,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    Paper,
    Select,
    MenuItem, TextField,
} from '@mui/material';

interface Campaign {
    campaignId: string;
    campaignName: string;
    campaignDescription: string;
}
interface Character {
    id: string;
    characterName: string;
    species: string;
    profession: string;
    baseMagic: string;
    baseHP: string;
    baseWillpower: string;
    magic?: string[];
    skills?: string[];
}

function createData(
    attribute: string,
    veldigLett: number,
    lett: number,
    normal: number,
    vanskelig: number,
    veldigVanskelig: number
) {
    return { attribute, veldigLett, lett, normal, vanskelig, veldigVanskelig };
}

interface Item {
    id: string;
    itemName: string;
    itemDescription: string;
}

const initialRows = [
    createData('Smidighet', 3, 4, 6, 8, 9),
    createData('Kløkt', 4, 5, 7, 9, 10),
    createData('Styrke', 4, 5, 7, 9, 10),
    createData('Gunst', 4, 5, 7, 9, 10),
    createData('Intuisjon', 5, 6, 8, 10, 11),
];

const attributeOptions = [
    "Smidighet",
    "Kløkt",
    "Styrke",
    "Gunst",
    "Intuisjon",
];

const CharacterSheet: React.FC = () => {
    const { characterId } = useParams<{ characterId: string }>();
    const [character, setCharacter] = useState<Character | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [rows, setRows] = useState(initialRows);
    const [items, setItems] = useState<Item[]>([]);
    const [skills, setSkills] = useState<string[]>([]);
    const [spells, setSpells] = useState<string[]>([]);
    const [hpChange, setHpChange] = useState<number>(0);
    const [magicChange, setMagicChange] = useState<number>(0);
    const [willpowerChange, setWillpowerChange] = useState<number>(0);
    const [rollAmount, setRollAmount] = useState<number>(0);
    const [rollLog, setRollLog] = useState<string[]>([]);
    const [campaign, setCampaign] = useState<Campaign | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCharacter = async () => {
            try {
                const response = await fetch(`http://localhost:8087/character/${characterId}`);
                if (!response.ok) throw new Error('Failed to fetch character details');
                const data = await response.json();
                setCharacter(data);
                setSpells(data.magic || []); // Assuming "magic" is the key for spells
                setSkills(data.skills || []);


            } catch (err) {
                setError(err instanceof Error ? err.message : 'An unknown error occurred');
            }
        };
        fetchCharacter();
    }, [characterId]);

    useEffect(() => {
        const fetchCampaignInfo = async () => {
            try {
                const response = await fetch(`http://localhost:8087/character/campaigninfo/${characterId}`);
                if (!response.ok) {
                    //throw new Error('Failed to fetch campaign info');
                    console.log("Character is not in a campaign")
                }else{
                    const data: Campaign = await response.json();
                    setCampaign(data);
                }

            } catch (err) {
                setError(err instanceof Error ? err.message : 'An unknown error occurred');
            }
        };

        if (characterId) fetchCampaignInfo();
    }, [characterId]);

    useEffect(() => {
        fetch(`http://localhost:8087/items/${characterId}`)
            .then((response) => {
                if (!response.ok) throw new Error("Network response was not ok");
                return response.json();
            })
            .then((data: Item[]) => setItems(data))
            .catch((error) => console.error("Error fetching data:", error));
    }, [characterId]);

    const updateHp = async (hpChange: number) => {
        console.log(`Send health on: http://localhost:8087/stats/update/hp`);
        await fetch(`http://localhost:8087/stats/update/hp`, {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ value: hpChange, characterId: characterId }),
        });
        console.log(`Health sent: ${hpChange}`)
    };

    const updateMagic = async (magicChange: number) => {
        await fetch(`http://localhost:8089/stats/${characterId}/magic`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ value: magicChange }),
        });
    };

    const updateWillpower = async (willpowerChange: number) => {
        await fetch(`http://localhost:8089/stats/${characterId}/willpower`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ value: willpowerChange }),
        });
    };


    const handleAttributeChange = (index: number, newAttribute: string) => {
        const updatedRows = rows.map((row, i) => i === index ? { ...row, attribute: newAttribute } : row);
        setRows(updatedRows);
    };
    const handleRoll = async () => {
        try {
            const payload = {
                characterId: character?.id || '',
                campaignId: campaign?.campaignId || '',
                action: 'roll',
            };
            const response = await fetch('http://localhost:8087/messages/roll', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload),
            });
            if (!response.ok) throw new Error('Failed to send roll');
            const result = await response.json(); // Assuming the roll result is returned as JSON
            const rollMessage = `Roll result: ${result.rollValue}`; // Customize as needed
            setRollLog((prevLog) => [rollMessage, ...prevLog]); // Add to log
        } catch (err) {
            console.error(err);
        }
    };

    if (error) return <Typography variant="body1" align="center" color="error">{error}</Typography>;
    if (!character) return <Typography variant="body1" align="center">Loading character...</Typography>;

    return (
        <Container
            maxWidth="xl"
            sx={{
                height: '100vh',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center',
                overflow: 'hidden',
                padding: 2,
            }}
        >

            <Card
                sx={{
                    width: '100%',
                    maxHeight: '100%', // Contain the card within the viewport
                    overflow: 'auto', // Enable scrolling for overflow content
                    backgroundColor: '#fffaf0',
                    boxShadow: '0 8px 16px rgba(0, 0, 0, 0.2)',
                }}
            >
                <CardContent>
                    <Typography
                        variant="h4"
                        align="center"
                        sx={{ fontFamily: 'MedievalSharp, cursive', color: '#8b6c42' }}
                    >
                        Character Sheet
                    </Typography>
                    {/* Campaign Info Section */}
                    {campaign && (
                        <Box sx={{ mt: 2, p: 2, backgroundColor: '#f4f1e0', borderRadius: 2 }}>
                            <Typography variant="h6" sx={{ color: '#8b6c42' }}>
                                Campaign Information
                            </Typography>
                            <Typography variant="body1">
                                <strong>Name:</strong> {campaign.campaignName}
                            </Typography>
                            <Typography variant="body1">
                                <strong>Description:</strong> {campaign.campaignDescription}
                            </Typography>
                            <Typography variant="body1">
                                <strong>ID:</strong> {campaign.campaignId}
                            </Typography>
                        </Box>
                    )}
                    {/* Roll Section */}
                    <Box sx={{ width: '100%', mb: 2, textAlign: 'center' }}>
                        <TextField
                            label="Roll Amount"
                            type="number"
                            value={rollAmount}
                            onChange={(e) => setRollAmount(Number(e.target.value))}
                            sx={{ mr: 2 }}
                        />
                        <Button
                            variant="contained"
                            color="secondary"
                            onClick={handleRoll}
                        >
                            Roll
                        </Button>
                    </Box>

                    {/* New Section for Stat Updates */}
                    <Box sx={{ my: 3 }}>
                        <Typography variant="h6" sx={{ mb: 1 }}>Update Stats</Typography>
                        <Grid container spacing={2}>
                            <Grid item xs={6}>
                                <TextField
                                    label="HP Change"
                                    type="number"
                                    value={hpChange}
                                    onChange={(e) => setHpChange(Number(e.target.value))}

                                />
                            </Grid>
                            <Grid item xs={6}>
                                <TextField
                                    label="Magic Change"
                                    type="number"
                                    value={magicChange}
                                    onChange={(e) => setMagicChange(Number(e.target.value))}

                                />
                            </Grid>
                            <Grid item xs={6}>
                                <TextField
                                    label="Willpower Change"
                                    type="number"
                                    value={willpowerChange}
                                    onChange={(e) => setWillpowerChange(Number(e.target.value))}

                                />
                            </Grid>
                        </Grid>
                        <Button
                            variant="contained"
                            color="primary"
                            sx={{ mt: 2 }}
                            onClick={() => updateHp(hpChange)}
                        >
                            Apply HP
                        </Button>
                        <Button
                            variant="contained"
                            color="primary"
                            sx={{ mt: 2 }}
                            onClick={() => updateMagic(magicChange)}
                        >
                            Apply Magic
                        </Button>
                        <Button
                            variant="contained"
                            color="primary"
                            sx={{ mt: 2 }}
                            onClick={() => updateWillpower(willpowerChange)}
                        >
                            Apply Willpower
                        </Button>
                    </Box>

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

                    <Divider sx={{ my: 2, borderBottomWidth: '2px', borderColor: '#d2b48c' }} />
                    <Grid container spacing={2} sx={{ mb: 3 }}>
                        <Grid item xs={12} md={4}>
                            <Typography variant="subtitle1">Health:</Typography>
                            <Typography variant="body1">{character.baseHP || 'Unknown'}</Typography>
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <Typography variant="subtitle1">Willpower:</Typography>
                            <Typography variant="body1">{character.baseWillpower || 'Unknown'}</Typography>
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <Typography variant="subtitle1">Mana:</Typography>
                            <Typography variant="body1">{character.baseMagic || 'Unknown'}</Typography>
                        </Grid>
                        <Button />
                    </Grid>

                    {/* Attributes Table */}
                    <Box sx={{ mb: 3 }}>
                        <TableContainer
                            component={Paper}
                            sx={{
                                maxHeight: '300px', // Limit height for scrollable table
                                overflow: 'auto',
                                backgroundColor: '#fffaf0',
                            }}
                        >
                            <Table aria-label="attribute table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell sx={{ fontWeight: 'bold' }}>Attribute</TableCell>
                                        <TableCell align="right">Veldig Lett</TableCell>
                                        <TableCell align="right">Lett</TableCell>
                                        <TableCell align="right">Normal</TableCell>
                                        <TableCell align="right">Vanskelig</TableCell>
                                        <TableCell align="right">Veldig Vanskelig</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {rows.map((row, index) => (
                                        <TableRow key={index}>
                                            <TableCell>
                                                <Select
                                                    value={row.attribute}
                                                    onChange={(e) => handleAttributeChange(index, e.target.value)}
                                                    fullWidth
                                                    sx={{ fontFamily: 'Cinzel, serif' }}
                                                >
                                                    {attributeOptions.map((option) => (
                                                        <MenuItem key={option} value={option}>
                                                            {option}
                                                        </MenuItem>
                                                    ))}
                                                </Select>
                                            </TableCell>
                                            <TableCell align="right">{row.veldigLett}</TableCell>
                                            <TableCell align="right">{row.lett}</TableCell>
                                            <TableCell align="right">{row.normal}</TableCell>
                                            <TableCell align="right">{row.vanskelig}</TableCell>
                                            <TableCell align="right">{row.veldigVanskelig}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </Box>
                    {/* Spells Section */}
                    <Box sx={{ mb: 3 }}>
                        <Typography variant="h6" sx={{ fontFamily: 'Cinzel, serif' }}>Spells</Typography>
                        {spells.length > 0 ? (
                            <ul style={{ paddingLeft: '20px', listStyle: 'circle', color: '#8b6c42' }}>
                                {spells.map((spell, index) => (
                                    <li key={index} style={{ marginBottom: '8px' }}>
                                        <Typography variant="body1">{spell}</Typography>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <Typography variant="body2">No spells listed.</Typography>
                        )}
                    </Box>

                    {/* Skills Section */}
                    <Box sx={{ mb: 3 }}>
                        <Typography variant="h6" sx={{ fontFamily: 'Cinzel, serif' }}>Skills</Typography>
                        {skills.length > 0 ? (
                            <ul style={{ paddingLeft: '20px', listStyle: 'disc', color: '#8b6c42' }}>
                                {skills.map((skill, index) => (
                                    <li key={index} style={{ marginBottom: '8px' }}>
                                        <Typography variant="body1">{skill}</Typography>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <Typography variant="body2">No skills listed.</Typography>
                        )}
                    </Box>
                    {/* Equipment Section */}
                    <Box sx={{ mb: 3 }}>
                        <Typography variant="h6" sx={{ fontFamily: 'Cinzel, serif' }}>Equipment</Typography>
                        {items.length > 0 ? (
                            <ul style={{ paddingLeft: '20px', listStyle: 'square', color: '#8b6c42' }}>
                                {items.map((item) => (
                                    <li key={item.id} style={{ marginBottom: '8px' }}>
                                        <Typography variant="body1" sx={{ fontWeight: 'bold' }}>{item.itemName}</Typography>
                                        <Typography variant="body2">{item.itemDescription}</Typography>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <Typography variant="body2">No equipment listed.</Typography>
                        )}
                    </Box>

                    <Divider sx={{ my: 2, borderBottomWidth: '2px', borderColor: '#d2b48c' }} />
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
            {/* Roll Log Overlay */}
            <Paper
                elevation={3}
                sx={{
                    position: 'fixed',
                    bottom: 16,
                    right: 16,
                    width: 300,
                    maxHeight: 200,
                    overflowY: 'auto',
                    padding: 2,
                    backgroundColor: '#bc9b59',
                    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
                }}
            >
                <Typography variant="h6" sx={{ mb: 1, textAlign: 'center' }}>
                    Roll Log
                </Typography>
                {rollLog.length > 0 ? (
                    rollLog.map((log, index) => (
                        <Typography
                            key={index}
                            variant="body2"
                            sx={{
                                marginBottom: 0.5,
                                color: '#8b6c42',
                            }}
                        >
                            {log}
                        </Typography>
                    ))
                ) : (
                    <Typography variant="body2" sx={{ textAlign: 'center', color: '#8b6c42' }}>
                        No rolls yet.
                    </Typography>
                )}
            </Paper>
        </Container>
    );
};

export default CharacterSheet;
