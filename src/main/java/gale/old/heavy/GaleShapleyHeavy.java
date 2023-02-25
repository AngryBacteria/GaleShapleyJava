package gale.old.heavy;

import java.util.*;

/**
 * Old version of the medium algorithm. Not using that anymore
 */
public class GaleShapleyHeavy {

    public static void main(String[] args) {

        GaleShapleyHeavy galeShapleyHeavy = new GaleShapleyHeavy();
        galeShapleyHeavy.run2();
    }

    public void findCouplesPrint(Set<Entity> proposers, Set<Entity> proposed){

        System.out.println("----------------------Gale Shapley START----------------------");
        int proposerCount = proposed.size();
        while (proposerCount > 0){

            Entity currentProposer = proposers.iterator().next();
            System.out.println(currentProposer.getIdentifier() + " Is proposing now...................");
            Iterator<Entity> entityIterator = currentProposer.getInterests().keySet().iterator();
            while (entityIterator.hasNext()){
                Entity nextPossibleMatch = entityIterator.next();
                //entityIterator.remove();
                System.out.println(currentProposer.getIdentifier() + " Is proposing to " + nextPossibleMatch.getIdentifier());
                if (!nextPossibleMatch.isMatched()){
                    currentProposer.match(nextPossibleMatch);
                    proposers.remove(currentProposer);
                    break;
                }
                else {
                    Entity alreadyAcceptedProposer = nextPossibleMatch.match;
                    if (nextPossibleMatch.isMoreInterested(currentProposer)){
                        nextPossibleMatch.unMatch();
                        currentProposer.match(nextPossibleMatch);
                        proposers.add(alreadyAcceptedProposer);
                        proposers.remove(currentProposer);
                        break;
                    }
                }
            }
            proposerCount = proposers.size();
        }
        proposed.forEach(entity -> System.out.println(entity.toStringWithMatch()));
        System.out.println("----------------------Gale Shapley END----------------------");
    }

    //Works only for the Heavy algorithm and if the algorithm doesn't remove preferences while iterating
    public void isMatchStableHeavyV1Print(Set<Entity> entities){
        for (Entity woman : entities){
            System.out.println("Checking Match: " + woman.getIdentifier() + " and: " + woman.match.getIdentifier());
            //Go through all interests of woman
            for (Entity man : woman.getInterests().keySet()){
                //If woman prefers other man
                if (woman.isMoreInterested(man) && man.isMoreInterested(woman)){
                    System.out.println("UNSTABLE MATCH");
                    return;
                }
            }
        }
        System.out.println("No unstable matches found");
    }

    public void run1(){

        //Init people
        Entity man0 = new Entity("man0", 3);
        Entity man1 = new Entity("man1", 3);
        Entity man2 = new Entity("man2", 3);

        Entity woman0 = new Entity("woman0", 3);
        Entity woman1 = new Entity("woman1", 3);
        Entity woman2 = new Entity("woman2", 3);

        man0.addAllInterests(Arrays.asList(woman1, woman2, woman0));
        man1.addAllInterests(Arrays.asList(woman1, woman0, woman2));
        man2.addAllInterests(Arrays.asList(woman1, woman0, woman2));

        woman0.addAllInterests(Arrays.asList(man1, man0, man2));
        woman1.addAllInterests(Arrays.asList(man1, man2, man0));
        woman2.addAllInterests(Arrays.asList(man2, man0, man1));

        //Init proposers
        Set<Entity> proposers = new HashSet<>();
        proposers.add(man0);
        proposers.add(man1);
        proposers.add(man2);

        //Init proposed
        Set<Entity> proposed = new HashSet<>();
        proposed.add(woman0);
        proposed.add(woman1);
        proposed.add(woman2);

        this.findCouplesPrint(proposers, proposed);
        this.isMatchStableHeavyV1Print(proposed);
    }

    public void run2(){

        //Init people
        Entity man0 = new Entity("man0", 4);
        Entity man1 = new Entity("man1", 4);
        Entity man2 = new Entity("man2", 4);
        Entity man3 = new Entity("man3", 4);

        Entity woman4 = new Entity("woman4", 4);
        Entity woman5 = new Entity("woman5", 4);
        Entity woman6 = new Entity("woman6", 4);
        Entity woman7 = new Entity("woman7", 4);

        man0.addAllInterests(Arrays.asList(woman4, woman5, woman6, woman7));
        man1.addAllInterests(Arrays.asList(woman5, woman4, woman6, woman7));
        man2.addAllInterests(Arrays.asList(woman4, woman7, woman6, woman5));
        man3.addAllInterests(Arrays.asList(woman7, woman5, woman6, woman4));

        woman4.addAllInterests(Arrays.asList(man2, man1, man0, man3));
        woman5.addAllInterests(Arrays.asList(man3, man1, man2, man0));
        woman6.addAllInterests(Arrays.asList(man0, man3, man2, man1));
        woman7.addAllInterests(Arrays.asList(man0, man1, man2, man3));

        //Init proposers
        Set<Entity> proposers = new HashSet<>();
        proposers.add(man0);
        proposers.add(man1);
        proposers.add(man2);
        proposers.add(man3);

        //Init proposed
        Set<Entity> proposed = new HashSet<>();
        proposed.add(woman4);
        proposed.add(woman5);
        proposed.add(woman6);
        proposed.add(woman7);

        this.findCouplesPrint(proposers, proposed);
        this.isMatchStableHeavyV1Print(proposed);
    }

    public void runRandom(int size){

        System.out.println("----------------------Generate Data START----------------------");
        //Generate Array of men and woman Entities with numerated names
        ArrayList<Entity> men = new ArrayList<>();
        ArrayList<Entity> women = new ArrayList<>();
        for (int i = 0; i < size; i++){
            men.add(new Entity("man" + i, size));
            women.add(new Entity("women" + i, size));
        }
        System.out.println("Entities created");
        //Add interests randomly to men and women
        for (Entity man : men){
            Collections.shuffle(women);
            man.addAllInterests(women);
        }
        for (Entity woman : women){
            Collections.shuffle(men);
            woman.addAllInterests(men);
        }
        System.out.println("Added interests");

        //Add them to HashSet
        Set<Entity> proposed = new HashSet<>(women);
        Set<Entity> proposers = new HashSet<>(men);
        System.out.println("----------------------Generate Data END----------------------");
        this.findCouplesPrint(proposers, proposed);
        this.isMatchStableHeavyV1Print(proposed);
    }
}
