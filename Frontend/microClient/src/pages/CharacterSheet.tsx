import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

interface Character {
    id: string;
    name: string;
    species: string;
    profession: string;
}

const CharacterSheet: React.FC = () => {
    const { characterId } = useParams<{ characterId: string }>();
    const [character, setCharacter] = useState<Character | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        const savedCharacters = localStorage.getItem('characters');
        if (savedCharacters) {
            const characters = JSON.parse(savedCharacters);
            const foundCharacter = characters.find((c: Character) => c.id === characterId);
            setCharacter(foundCharacter || null);
        }
    }, [characterId]);

    if (!character) {
        return <p>Character not found</p>;
    }

    return (
        <div className="character-sheet">
            <h2>Character Sheet</h2>
            <p><strong>Name:</strong> {character.name}</p>
            <p><strong>Species:</strong> {character.species}</p>
            <p><strong>Profession:</strong> {character.profession}</p>
            <button onClick={() => navigate('/character-menu')}>Back to Menu</button>
        </div>
    );
};

export default CharacterSheet;
