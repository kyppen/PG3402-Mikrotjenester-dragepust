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
    MenuItem
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

// Sample function to create table rows with dynamic data
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
    const [rows, setRows] = useState(initialRows);  // Manage rows with local state
    const [items, setItems] = useState<Item[]>([]);
    const navigate = useNavigate();

    // Fetch character details
    useEffect(() => {
        const fetchCharacter = async () => {
            try {
                const response = await fetch(`http://localhost:8081/character/${characterId}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch character details');
                }
                const data = await response.json();
                setCharacter(data);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'An unknown error occurred');
            }
        };

        fetchCharacter();
    }, [characterId]);

    useEffect(() => {
        // Fetch data from API
        fetch(`http://localhost:8082/items/${characterId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data: Item[]) => setItems(data)) // Type data as Item[]
            .catch((error) => console.error("Error fetching data:", error));
    }, []);

    const handleAttributeChange = (index: number, newAttribute: string) => {
        const updatedRows = rows.map((row, i) =>
            i === index ? { ...row, attribute: newAttribute } : row
        );
        setRows(updatedRows);
    };

    if (error) {
        return <Typography variant="body1" align="center" color="error">{error}</Typography>;
    }

    if (!character) {
        return <Typography variant="body1" align="center">Loading character...</Typography>;
    }

    return (
        <Container maxWidth="md" sx={{ mt: 4 }}>
            <Card>
                <CardContent>
                    <Typography variant="h4" align="center" gutterBottom>
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

                    <Divider sx={{ my: 2 }} />

                    {/* Attributes Table with Dropdown in Attribute Column */}
                    <Box sx={{ mb: 3 }}>
                        <TableContainer component={Paper}>
                            <Table sx={{ minWidth: 650 }} aria-label="attribute table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Attribute</TableCell>
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

                    <Divider sx={{ my: 2 }} />

                    {/* Equipment Section */}
                    <Box sx={{ mb: 3 }}>
                        <Typography variant="h6" gutterBottom>Equipment</Typography>
                        {items.length > 0 ? (
                            <ul>
                                {items.map((item) => (
                                    <li key={item.id}>
                                        <h2>{item.itemName}</h2>
                                        <p>{item.itemDescription}</p>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <Typography variant="body2">No equipment listed.</Typography>
                        )}
                    </Box>

                    <Divider sx={{ my: 2 }} />

                    {/* Additional Sections */}
                    <Box>
                        <Typography variant="h6" gutterBottom>Skills</Typography>
                        {character.skills && character.skills.length > 0 ? (
                            <ul>
                                {character.skills.map((skill, index) => (
                                    <li key={index}>{skill}</li>
                                ))}
                            </ul>
                        ) : (
                            <Typography variant="body2">No skills  listed.</Typography>
                        )}
                    </Box>

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
        </Container>
    );
};

export default CharacterSheet;
