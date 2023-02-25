package gale.old.medium;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Proposed {

    public String identifier;
    public final Map<Proposer, Integer> interests;
    private int size = 0;
    public Proposer match;

    public Proposed(String identifier) {
        this.identifier = identifier;
        this.interests = new HashMap<>();
    }

    public void addAllInterests(List<Proposer> proposerList){

        for (Proposer proposer : proposerList){
            this.interests.put(proposer, size);
            size++;
        }
    }

    public void match(Proposer match){
        this.match = match;
    }

    public void unMatch(){
        this.match = null;
    }

    public boolean isMatched(){
        return !(this.match == null);
    }

    public boolean isMoreInterested(Proposer proposer){
        return this.interests.get(proposer) < this.interests.get(this.match);
    }

    @Override
    public String toString() {
        return "Proposed: " + this.identifier + " Proposer: " + this.match.identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposed proposed = (Proposed) o;
        return Objects.equals(identifier, proposed.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
