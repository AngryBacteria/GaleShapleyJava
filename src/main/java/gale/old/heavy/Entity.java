package gale.old.heavy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Entity {
    private String identifier;
    private final Map<Entity, Integer> interests;
    public Entity match;
    private int size = 0;

    public Entity(String identifier, int size) {

        this.identifier = identifier;
        this.match = null;
        this.interests = new LinkedHashMap<>(size);
    }

    public void addAllInterests(List<Entity> entityList){

        for (Entity entity : entityList){
            this.interests.put(entity, size);
            size++;
        }
    }

    public void addInterest(Entity entity){
        this.interests.put(entity, size);
        size++;
    }

    public void match(Entity match){
        this.match = match;
        match.match = this;
    }

    public void unMatch(){
        this.match.match = null;
        this.match = null;
    }

    public boolean isMatched(){
        return !(this.match == null);
    }

    public boolean isMoreInterested(Entity proposer){
        return this.interests.get(proposer) < this.interests.get(this.match);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Map<Entity, Integer> getInterests() {
        return interests;
    }

    public Entity getMatch() {
        return match;
    }

    public void setMatch(Entity match) {
        this.match = match;
    }

    public void printInterests(){

        System.out.println("--------------Interests--------------");
        for (Map.Entry<Entity, Integer> integerEntry : this.interests.entrySet()) {
            System.out.println(integerEntry.getKey().getIdentifier() + ": " + integerEntry.getValue());
        }
        System.out.println("--------------Interests--------------");
    }

    public String toStringWithMatch(){
        return "Person{" +
                "identifier='" + identifier + '\'' +
                ", Match=" + this.match.getIdentifier() +
                '}';
    }

    @Override
    public String toString() {
        return "Person{" +
                "identifier='" + identifier + '\'' +
                ", isMatched=" + this.isMatched() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(identifier, entity.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
