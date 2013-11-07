package pl.edu.uw.dsk.dev.farel.entites;


public class Project {
    
    public Project(String name) {
        this.name = name;
    }

    String name;
    
    public Project() { }

    public String getName() {
        return name;
    }
}