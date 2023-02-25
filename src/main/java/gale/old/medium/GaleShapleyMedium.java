package gale.old.medium;

import java.util.*;

/**
 * Should have fewer computations than LightV1, but uses a lot more memory. Goes over heap space fast
 */
public class GaleShapleyMedium {


    public static void main(String[] args) {
        GaleShapleyMedium galeShapleyMedium = new GaleShapleyMedium();
        galeShapleyMedium.run1();
    }

    public void findCouplesPrint(Queue<Proposer> proposerQueue, List<Proposed> proposed){

        System.out.println("----------------------Gale Shapley START----------------------");
        //Init proposer queue
        while (!proposerQueue.isEmpty()){

            Proposer currentProposer = proposerQueue.poll();
            System.out.println(currentProposer.identifier + " Is proposing now...................");
            while (!currentProposer.interests.isEmpty()){

                Proposed nextPossibleMatch = currentProposer.interests.poll();
                System.out.println(currentProposer.identifier + " Is proposing to " + nextPossibleMatch.identifier);
                if (!nextPossibleMatch.isMatched()){

                    nextPossibleMatch.match(currentProposer);
                    break;
                }
                else {

                    Proposer alreadyAcceptedProposer = nextPossibleMatch.match;
                    if (nextPossibleMatch.isMoreInterested(currentProposer)){
                        nextPossibleMatch.unMatch();
                        nextPossibleMatch.match(currentProposer);
                        proposerQueue.add(alreadyAcceptedProposer);
                        break;
                    }
                }
            }
        }
        proposed.forEach(System.out::println);
        System.out.println("----------------------Gale Shapley END----------------------");
    }

    public void findCouples(Queue<Proposer> proposerQueue, List<Proposed> proposed){

        //Init proposer queue
        while (!proposerQueue.isEmpty()){

            Proposer currentProposer = proposerQueue.poll();
            while (!currentProposer.interests.isEmpty()){

                Proposed nextPossibleMatch = currentProposer.interests.poll();
                if (!nextPossibleMatch.isMatched()){

                    nextPossibleMatch.match(currentProposer);
                    break;
                }
                else {

                    Proposer alreadyAcceptedProposer = nextPossibleMatch.match;
                    if (nextPossibleMatch.isMoreInterested(currentProposer)){
                        nextPossibleMatch.unMatch();
                        nextPossibleMatch.match(currentProposer);
                        proposerQueue.add(alreadyAcceptedProposer);
                        break;
                    }
                }
            }
        }
    }

    //This needs a copy of the proposers queue as a HashMap and objects because they get mutated in the algorithm
    //If the algorithm doesn't mutate the interest list of the proposers, those extra steps wouldn't be needed
    public void checkStableMatches(HashMap<String, Proposer> proposerCopy, List<Proposed> proposedList){

        //HashMap representing the matches. Needed because Proposers do not have their match saved
        //It is  built with the proposersCopy HashMap which holds a copy for all the Proposers with their identifier as key
        HashMap<Proposer, Proposed> matches = new HashMap<>();
        for (Proposed proposed : proposedList){
            matches.put(proposerCopy.get(proposed.match.identifier), proposed);
        }
        System.out.println("----------------------Check for unstable Matches START----------------------");
        for (Proposed proposed : proposedList){

            Proposer match = proposed.match;
            System.out.println("Checking match: " + proposed.identifier + " and " + match.identifier);
            for (Proposer proposer : proposed.interests.keySet()){

                if (proposed.isMoreInterested(proposer)){

                    System.out.println("\t" + proposed.identifier + " is more interested in " + proposer.identifier);
                    //Here important to take the copy not the proposed's match
                    try {
                        if (proposerCopy.get(proposer.identifier).isMoreInterestedInNewMatch(matches.get(proposer), proposed)){
                            System.out.println("\t\tunstable match found");
                            System.out.println("\t\t" + proposer.identifier + " is more interested in " + proposed.identifier + " than in " + matches.get(proposer).identifier);
                            return;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        System.out.println("No unstable matches found");
        System.out.println("----------------------Check for unstable Matches END----------------------");
    }

    public void run1(){

        //Init people
        Proposer misty = new Proposer("Misty");
        Proposer ashe = new Proposer("Ashe");
        Proposer rocko = new Proposer("Rocko");
        Queue<Proposer> proposers = new LinkedList<>();
        proposers.add(misty);
        proposers.add(ashe);
        proposers.add(rocko);

        Proposed evoli = new Proposed("Evoli");
        Proposed pikachu = new Proposed("Pikachu");
        Proposed schiggy = new Proposed("Schiggy");
        List<Proposed> proposed = new ArrayList<>();
        proposed.add(evoli);
        proposed.add(pikachu);
        proposed.add(schiggy);

        misty.addAllInterests(Arrays.asList(pikachu, evoli, schiggy));
        ashe.addAllInterests(Arrays.asList(pikachu, schiggy, evoli));
        rocko.addAllInterests(Arrays.asList(evoli, schiggy, pikachu));

        evoli.addAllInterests(Arrays.asList(ashe, misty, rocko));
        pikachu.addAllInterests(Arrays.asList(rocko, ashe, misty));
        schiggy.addAllInterests(Arrays.asList(misty, rocko, ashe));

        this.findCouplesPrint(proposers, proposed);
    }

    public void run2(){

            //Init people
            Proposer man0 = new Proposer("man0");
            Proposer man1 = new Proposer("man1");
            Proposer man2 = new Proposer("man2");
            Proposer man3 = new Proposer("man3");
            Queue<Proposer> proposers = new LinkedList<>();
            proposers.add(man0);
            proposers.add(man1);
            proposers.add(man2);
            proposers.add(man3);

            Proposed woman4 = new Proposed("woman4");
            Proposed woman5 = new Proposed("woman5");
            Proposed woman6 = new Proposed("woman6");
            Proposed woman7 = new Proposed("woman7");
            List<Proposed> proposed = new ArrayList<>();
            proposed.add(woman4);
            proposed.add(woman5);
            proposed.add(woman6);
            proposed.add(woman7);

            man0.addAllInterests(Arrays.asList(woman4, woman5, woman6, woman7));
            man1.addAllInterests(Arrays.asList(woman5, woman4, woman6, woman7));
            man2.addAllInterests(Arrays.asList(woman4, woman7, woman6, woman5));
            man3.addAllInterests(Arrays.asList(woman7, woman5, woman6, woman4));

            woman4.addAllInterests(Arrays.asList(man2, man1, man0, man3));
            woman5.addAllInterests(Arrays.asList(man3, man1, man2, man0));
            woman6.addAllInterests(Arrays.asList(man0, man3, man2, man1));
            woman7.addAllInterests(Arrays.asList(man0, man1, man2, man3));
            this.findCouplesPrint(proposers, proposed);
    }

    public void run2WithChecks(){

        //Init people
        Proposer man0 = new Proposer("man0");
        Proposer man1 = new Proposer("man1");
        Proposer man2 = new Proposer("man2");
        Proposer man3 = new Proposer("man3");
        Queue<Proposer> proposers = new LinkedList<>();
        proposers.add(man0);
        proposers.add(man1);
        proposers.add(man2);
        proposers.add(man3);

        Proposed woman4 = new Proposed("woman4");
        Proposed woman5 = new Proposed("woman5");
        Proposed woman6 = new Proposed("woman6");
        Proposed woman7 = new Proposed("woman7");
        List<Proposed> proposed = new ArrayList<>();
        proposed.add(woman4);
        proposed.add(woman5);
        proposed.add(woman6);
        proposed.add(woman7);

        man0.addAllInterests(Arrays.asList(woman4, woman5, woman6, woman7));
        man1.addAllInterests(Arrays.asList(woman5, woman4, woman6, woman7));
        man2.addAllInterests(Arrays.asList(woman4, woman7, woman6, woman5));
        man3.addAllInterests(Arrays.asList(woman7, woman5, woman6, woman4));

        woman4.addAllInterests(Arrays.asList(man2, man1, man0, man3));
        woman5.addAllInterests(Arrays.asList(man3, man1, man2, man0));
        woman6.addAllInterests(Arrays.asList(man0, man3, man2, man1));
        woman7.addAllInterests(Arrays.asList(man0, man1, man2, man3));

        HashMap<String, Proposer> proposerCopy = new HashMap<>();
        proposers.forEach(proposer -> proposerCopy.put(proposer.identifier, new Proposer(proposer.identifier, new LinkedList<>(proposer.interests))));

        this.findCouplesPrint(proposers, proposed);
        this.checkStableMatches(proposerCopy, proposed);
    }

    public void runRandom(int size){

        System.out.println("----------------------Generate Data START----------------------");
        //Generate Array of men and woman Entities with numerated names
        ArrayList<Proposer> men = new ArrayList<>();
        ArrayList<Proposed> women = new ArrayList<>();
        for (int i = 0; i < size; i++){
            men.add(new Proposer("man" + i));
            women.add(new Proposed("women" + i));
        }
        System.out.println("Entities created");
        //Add interests randomly to men and women
        for (Proposer man : men){
            Collections.shuffle(women);
            man.addAllInterests(women);
        }
        System.out.println("Proposer interests added");
        for (Proposed woman : women){
            Collections.shuffle(men);
            woman.addAllInterests(men);
        }
        System.out.println("Added interests");

        Queue<Proposer> proposers = new LinkedList<>(men);
        System.out.println("----------------------Generate Data END----------------------");
        long start = System.nanoTime();
        this.findCouples(proposers, women);
        long finish = System.nanoTime();
        System.out.println(finish-start);
    }

    public void runRandomWithChecks(int size){

        System.out.println("----------------------Generate Data START----------------------");
        //Generate Array of men and woman Entities with numerated names
        ArrayList<Proposer> men = new ArrayList<>();
        ArrayList<Proposed> women = new ArrayList<>();
        for (int i = 0; i < size; i++){
            men.add(new Proposer("man" + i));
            women.add(new Proposed("women" + i));
        }
        Queue<Proposer> proposers = new LinkedList<>(men);
        System.out.println("Entities created");
        //Add interests randomly to men and women
        for (Proposer man : men){
            Collections.shuffle(women);
            man.addAllInterests(women);
        }
        System.out.println("Proposer interests added");
        for (Proposed woman : women){
            Collections.shuffle(men);
            woman.addAllInterests(men);
        }
        System.out.println("Added interests");
        System.out.println("----------------------Generate Data END----------------------");

        HashMap<String, Proposer> proposerCopy = new HashMap<>();
        proposers.forEach(proposer -> proposerCopy.put(proposer.identifier, new Proposer(proposer.identifier, new LinkedList<>(proposer.interests))));

        this.findCouplesPrint(proposers, women);
        this.checkStableMatches(proposerCopy, women);
    }
}
