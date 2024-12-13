import React, { useState} from 'react';
import {useNavigate} from "react-router-dom";
import { Box, Button, Paper, TextField, Typography} from "@mui/material";




export interface User {
    id?: number;
    username: string;
    password: string;
}

const CreateUser: React.FC = () => {
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        try {
            const response = await fetch("http://localhost:8087/users/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ username, password }),
            });

            if (!response.ok) {
                throw new Error("Invalid username or password");
            }

            navigate("/character-menu");
        } catch (err) {
            setError(err instanceof Error ? err.message : "An unexpected error occurred");
        }
    };

    return (
        <Box
            sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh',
                backgroundColor: 'rgba(163,64,64,0)',
            }}
        >
            <Paper
                elevation={3}
                sx={{
                    padding: 4,
                    width: 400,
                    textAlign: 'center',
                }}
            >
                <Typography
                    variant="h4"
                    component="h1"
                    gutterBottom
                    sx={{ marginBottom: 3 }}
                >
                    Login
                </Typography>
                <form onSubmit={handleLogin}>
                    <TextField
                        label="Username"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <TextField
                        label="Password"
                        type="password"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    {error && (
                        <Typography
                            color="error"
                            variant="body2"
                            sx={{ marginTop: 1, marginBottom: 2 }}
                        >
                            {error}
                        </Typography>
                    )}
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        fullWidth
                        sx={{ marginTop: 2 }}
                    >
                        Login
                    </Button>
                </form>
            </Paper>
        </Box>
    );
};
 //tog er moro
export default CreateUser;
