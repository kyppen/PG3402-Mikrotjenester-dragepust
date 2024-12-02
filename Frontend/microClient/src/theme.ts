import { createTheme } from '@mui/material/styles';

const theme = createTheme({
    palette: {
        primary: {
            main: '#8B4513', // A warm brown color for primary actions
        },
        secondary: {
            main: '#4682B4', // Deep steel blue for secondary accents
        },
        background: {
            default: '#f4ecd8', // Light parchment background
            paper: '#fffaf0',   // Slightly off-white for paper sections
        },
        text: {
            primary: '#4b2e1e', // Dark brown for primary text
            secondary: '#8b6c42', // Softer brown for secondary text
        },
    },
    typography: {
        fontFamily: `'Cinzel', serif`,
        h4: {
            fontFamily: `'MedievalSharp', cursive`,
            fontWeight: 700,
            color: '#8b6c42',
        },
        h5: {
            fontFamily: `'Cinzel', serif`,
            fontWeight: 700,
            color: '#4b2e1e',
        },
        body1: {
            fontFamily: `'Cinzel', serif`,
            color: '#4b2e1e',
        },
        body2: {
            fontFamily: `'Cinzel', serif`,
            color: '#8b6c42',
        },
    },
    components: {
        MuiButton: {
            styleOverrides: {
                root: {
                    borderRadius: '12px',
                    fontWeight: 600,
                    textTransform: 'none',
                    padding: '8px 16px',
                    boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.2)',
                },
                containedPrimary: {
                    backgroundColor: '#8B4513',
                    color: '#fff',
                    '&:hover': {
                        backgroundColor: '#A0522D',
                    },
                },
            },
        },
        MuiCard: {
            styleOverrides: {
                root: {
                    backgroundColor: '#fffaf0',
                    borderRadius: '10px',
                    boxShadow: '0px 6px 12px rgba(0, 0, 0, 0.15)',
                    border: '1px solid #d2b48c', // light brown border
                },
            },
        },
        MuiDivider: {
            styleOverrides: {
                root: {
                    borderBottomWidth: '2px',
                    borderColor: '#d2b48c',
                    margin: '12px 0',
                },
            },
        },
    },
});

export default theme;
