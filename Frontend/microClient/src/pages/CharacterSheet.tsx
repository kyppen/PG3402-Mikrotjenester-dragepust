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
    MenuItem,
} from '@mui/material';

interface Character {
    id: string;
    characterName: string;
    species: string;
    profession: string;
    age?: string;
    health?: string;
    willpower?: string;
    skills?: string[];
    achievements?: string[];
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
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCharacter = async () => {
            try {
                const response = await fetch(`http://localhost:8081/character/${characterId}`);
                if (!response.ok) throw new Error('Failed to fetch character details');
                const data = await response.json();
                setCharacter(data);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'An unknown error occurred');
            }
        };
        fetchCharacter();
    }, [characterId]);

    useEffect(() => {
        fetch(`http://localhost:8082/items/${characterId}`)
            .then((response) => {
                if (!response.ok) throw new Error("Network response was not ok");
                return response.json();
            })
            .then((data: Item[]) => setItems(data))
            .catch((error) => console.error("Error fetching data:", error));
    }, [characterId]);

    const handleAttributeChange = (index: number, newAttribute: string) => {
        const updatedRows = rows.map((row, i) => i === index ? { ...row, attribute: newAttribute } : row);
        setRows(updatedRows);
    };

    if (error) return <Typography variant="body1" align="center" color="error">{error}</Typography>;
    if (!character) return <Typography variant="body1" align="center">Loading character...</Typography>;

    return (
        <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
            <Card sx={{ backgroundColor: '#fffaf0', padding: 3, boxShadow: '0 8px 16px rgba(0, 0, 0, 0.2)' }}>
                <CardContent>
                    <Typography variant="h4" align="center" sx={{ fontFamily: 'MedievalSharp, cursive', color: '#8b6c42' }}>
                        Character Sheet
                    </Typography>

                    {/* Header Fields */}
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

                    {/* Attributes Table with Dropdown in Attribute Column */}
                    <Box sx={{ mb: 3, maxHeight: '300px', overflow: 'auto' }}>
                        <TableContainer component={Paper} sx={{ backgroundColor: '#fffaf0' }}>
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
                                        <TableRow key={index} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                                            <TableCell component="th" scope="row">
                                                <Select
                                                    value={row.attribute}
                                                    onChange={(e) => handleAttributeChange(index, e.target.value as string)}
                                                    fullWidth
                                                    sx={{ fontFamily: 'Cinzel, serif' }}
                                                >
                                                    {attributeOptions.map((option) => (
                                                        <MenuItem key={option} value={option}>{option}</MenuItem>
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

                    <Divider sx={{ my: 2, borderBottomWidth: '2px', borderColor: '#d2b48c' }} />

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
                        sx={{ mt: 3, fontWeight: 'bold' }}
                        onClick={() => navigate('/character-menu')}
                    >
                        Back to Menu
                    </Button>
                </CardContent>
            </Card>
        </Container>
    );
};

export default CharacterSheet;
