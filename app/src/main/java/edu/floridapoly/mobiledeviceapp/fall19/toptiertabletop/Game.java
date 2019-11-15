package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

public class Game
{
    private int id;
    private String name;
    private String story;
    private String character;
    private String pictureUrl;


    public static final Game[] campaigns = {
        new Game(1, "FLPolyD&D", "Survive college!", "Penguin Lord", "PenguinLord.png"),
        new Game(2, "Flame Ember Finder", "Save the world from the evil penguins!", "Tiki", "Tiki.png")

    };


    public Game() {

    }

    public Game(int id, String name, String story, String character, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.story = story;
        this.character = character;
        this.pictureUrl = pictureUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public static Game[] getCampaigns() {
        return campaigns;
    }
}
