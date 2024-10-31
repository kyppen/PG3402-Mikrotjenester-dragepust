import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const CreateCharacter: React.FC = () => {
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [species, setSpecies] = useState('');
    const [profession, setProfession] = useState('');

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const newCharacter = {
            id: Date.now().toString(),
            name,
            species,
            profession,
        };

        const savedCharacters = localStorage.getItem('characters');
        const characters = savedCharacters ? JSON.parse(savedCharacters) : [];
        characters.push(newCharacter);
        localStorage.setItem('characters', JSON.stringify(characters));

        navigate('/character-menu');
    };

    return (
        <div className="character-form">
            <h2>Create New Character</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />

                <select value={species} onChange={(e) => setSpecies(e.target.value)} required>
                    <option value="">Please choose a species</option>
                    <option value="Menneske">Menneske</option>
                    <option value="Alv">Alv</option>
                    <option value="Dverg">Dverg</option>
                </select>

                <select value={profession} onChange={(e) => setProfession(e.target.value)} required>
                    <option value="">Please choose a profession</option>
                    <option value="Jeger">Jeger</option>
                    <option value="Shaman">Shaman</option>
                    <option value="Trubadur">Trubadur</option>
                </select>

                <button type="submit">Save Character</button>
            </form>
        </div>
    );
};


export default CreateCharacter;
