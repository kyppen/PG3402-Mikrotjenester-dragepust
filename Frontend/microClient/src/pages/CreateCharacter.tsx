import React, {useState} from "react";
import "../assets/css/CreateCharacter.css";

const CreateCharacter: React.FC = function(){

     interface FormData {
        name: string;
        species: string;
        profession: string;
    }

    const [formData, setFormData] = useState<FormData>({
        name: "",
        species: "",
        profession: "",
    });

    const handleChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
    ): void => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: name === "" ? Number(value) : value,
        }));
    };
    const handleSubmit = (e: React.FormEvent<HTMLFormElement>): void => {
        e.preventDefault();

        // Clear form
        setFormData({
            name: "",
            species: "",
            profession: "",

        });
        alert(`Data saved successfully to the ${formData.species} species!`);
    };

    return (
        <>
        <div>
            <h2>Character creator</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Navn:</label>
                    <input
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Choose species:</label>
                    <select
                        name="species"
                        value={formData.species}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Velg Art</option>
                        <option value="Menneske">Menneske</option>
                        <option value="Alv">Alv</option>
                        <option value="Dverg">Dverg</option>

                    </select>
                </div>
                <div>
                    <label>Choose profession:</label>
                    <select
                        name="profession"
                        value={formData.profession}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Velg Yrke</option>
                        <option value="Jeger">Jeger</option>
                        <option value="Shaman">Shaman</option>
                        <option value="Trubadur">Trubadur</option>
                    </select>
                </div>
                <button className="CreateCharacterButton" type="submit">
                    Save
                </button>
            </form>
        </div>
        </>
    )
}
export default CreateCharacter