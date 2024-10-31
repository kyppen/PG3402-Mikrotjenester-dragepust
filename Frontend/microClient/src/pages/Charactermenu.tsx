import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

interface Character {
    id: string;
    name: string;
    species: string;
    profession: string;
}

const CharacterMenu: React.FC = () => {
    const navigate = useNavigate();
    const [characters, setCharacters] = useState<Character[]>([]);

    useEffect(() => {
        const savedCharacters = localStorage.getItem('characters');
        if (savedCharacters) {
            setCharacters(JSON.parse(savedCharacters));
        }
    }, []);

    const handleNewCharacter = () => {
        navigate('/new-character');
    };

    const handleCharacterClick = (characterId: string) => {
        navigate(`/character/${characterId}`);
    };

    return (
        <div className="character-menu">
            <h2>Your Characters</h2>
            <button onClick={handleNewCharacter}>Create New Character</button>
            <div className="character-list">
                {characters.map((character) => (
                    <div
                        key={character.id}
                        className="character-card"
                        onClick={() => handleCharacterClick(character.id)}
                    >
                        <h3>{character.name}</h3>
                        <p><strong>Species:</strong> {character.species}</p>
                        <p><strong>Profession:</strong> {character.profession}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default CharacterMenu;
