import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { Box, Button, Paper, TextField, Typography } from "@mui/material";

export interface User {
    id?: number;
    username: string;
    password: string;
}

const UserAuth: React.FC = () => {
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [error, setError] = useState<string | null>(null);
    const [isCreating, setIsCreating] = useState<boolean>(false);
    const navigate = useNavigate();

    const setCookie = (name: string, value: string, days: number) => {
        const expires = new Date();
        expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000);
        document.cookie = `${name}=${value};expires=${expires.toUTCString()};path=/`;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        const endpoint = isCreating
            ? "http://localhost:8087/users/create"
            : "http://localhost:8087/users/login"; // Assuming login API exists

        try {
            const response = await fetch(endpoint, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ username, password }),
            });

            if (!response.ok) {
                throw new Error(isCreating ? "Error creating user" : "Invalid login credentials");
            }

            const result = await response.json();
            console.log(isCreating ? "User created:" : "Login successful:", result);

            // Save userId and username as cookies
            setCookie("userId", result.id, 7); // Save for 7 days
            setCookie("userName", result.username, 7);
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
                    {isCreating ? "Create User" : "Login"}
                </Typography>
                <form onSubmit={handleSubmit}>
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
                        {isCreating ? "Create Account" : "Login"}
                    </Button>
                </form>
                <Button
                    onClick={() => setIsCreating(!isCreating)}
                    variant="text"
                    color="primary"
                    sx={{ marginTop: 2 }}
                >
                    {isCreating ? "Already have an account? Login" : "Don't have an account? Create one"}
                </Button>
            </Paper>
        </Box>
    );
};

export default UserAuth;
