
import {Button, Container, Typography} from "@mui/material";


const Campaign: React.FC = () => {
    return (
        <Container maxWidth="sm" sx={{ textAlign: 'center', mt: 10 }}>
            <Typography variant="h4" gutterBottom>
                Welcome to campaign?
            </Typography>
            <Button variant="contained" color="primary">
                Log In
            </Button>
        </Container>
    );
}

export default Campaign;