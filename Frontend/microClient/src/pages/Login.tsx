import React, { useState} from 'react';




export interface User {
    id?: number;
    username: string;
    password: string;
}

const CreateUser: React.FC = () => {
    const [user, setUser] = useState<User>({ username: "", password: "" });
    const [error, setError] = useState<string | null>(null);


    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUser({ ...user, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        console.log(user);
        try {
            const response = await fetch("http://localhost:8084/api/users", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(user),
            });

            if (!response.ok) {
                throw new Error("Failed to create user");
            }

            const result = await response.json();
            console.log("User created:", result);

        } catch (err) {
            setError(err instanceof Error ? err.message : "An unknown error occurred");
            console.error("Error creating user:", err);
        }
    };

    return (
        <div>
            <h2>Create User</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Username:</label>
                    <input
                        type="text"
                        name="username"
                        value={user.username}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Password:</label>
                    <input
                        type="password"
                        name="password"
                        value={user.password}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Create User</button>
            </form>
            {error && <p style={{ color: "red" }}>{error}</p>}
        </div>
    );
};
 //tog er moro
export default CreateUser;
