public class gameObject {
    private String objectName;
    private String description;

    public gameObject(String objName, String objDescription) {
        this.objectName = objName;
        this.description = objDescription;
    }

    public gameObject(String objName) {
        this.objectName = objName;
        this.description = " an object";
    }

    public String getObjectName() {
        return objectName;
    }

    public String getDescription() {
        return description;
    }



}