package gale.old.medium;

import java.util.*;

public class Proposer {

    public String identifier;
    public Queue<Proposed> interests;

    public Proposer(String identifier) {
        this.identifier = identifier;
        this.interests = new LinkedList<>();
    }

    public Proposer(String identifier, Queue<Proposed> interests) {
        this.identifier = identifier;
        this.interests = interests;
    }

    public void addAllInterests(List<Proposed> proposedList){
        this.interests.addAll(proposedList);
    }

    public boolean isMoreInterestedInNewMatch(Proposed currentMatch, Proposed newMatch) throws Exception {

        for (Proposed proposed : this.interests){
            if (proposed.equals(currentMatch))
                return false;
            if (proposed.equals(newMatch))
                return true;
        }
        throw new Exception("None of the matches is preferred");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposer proposer = (Proposer) o;
        return Objects.equals(identifier, proposer.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
