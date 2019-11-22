package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

public class cards  {
    private String userId;
    private String name;
    private String story;

    public cards(String userId, String name, String story){
        this.userId = userId;
        this.name = name;
        this.story = story;
    }

    public String getUserId(){
        return  userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getName(){
        return  name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
