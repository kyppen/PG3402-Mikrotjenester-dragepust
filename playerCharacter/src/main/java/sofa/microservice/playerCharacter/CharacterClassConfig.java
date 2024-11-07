package sofa.microservice.playerCharacter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CharacterClassConfig {

    private static final String CONFIG_FILE_PATH = "playerCharacter/src/main/resources/Classes.json";
    private static Map<String, CharacterClass> characterClassMap;

    static {
        loadConfig();
    }

    // Nested class to represent a character class
    public static class CharacterClass {
        private int baseHP;
        private int baseWillpower;
        private Integer baseMagic; // Nullable in case some classes don't have magic
        private List<String> skills;
        private List<String> magic;

        // Getters for each attribute
        public int getBaseHP() { return baseHP; }
        public int getBaseWillpower() { return baseWillpower; }
        public Integer getBaseMagic() { return baseMagic; }
        public List<String> getSkills() { return skills; }
        public List<String> getMagic() { return magic; }

        @Override
        public String toString() {
            return "CharacterClass{" +
                    "baseHP=" + baseHP +
                    ", baseWillpower=" + baseWillpower +
                    ", baseMagic=" + baseMagic +
                    ", skills=" + skills +
                    ", magic=" + magic +
                    '}';
        }
    }

    // Load and parse the JSON file
    private static void loadConfig() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(new File(CONFIG_FILE_PATH));
            characterClassMap = mapper.convertValue(rootNode, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load character class configuration");
        }
    }

    // Get configuration for a specific character class
    public static CharacterClass getClassConfig(String className) {
        if (characterClassMap.containsKey(className)) {
            return characterClassMap.get(className);
        } else {
            System.err.println("Class " + className + " not found in configuration.");
            return null;
        }
    }
}
