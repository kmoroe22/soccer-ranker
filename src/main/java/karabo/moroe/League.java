package karabo.moroe;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class League {

    private TeamRepository repository;

    public League(TeamRepository repository) {
        this.repository = repository;
    }

    public void update(Game game) {
        game.getAwayTeam().addGame(game);
        game.getHomeTeam().addGame(game);
    }

    public List<Rank> rankings() {
        List<Team> allTeams = repository.findAll();
        allTeams.sort(Comparator.comparingLong(this::points).reversed().thenComparing(Team::getName));
        int rankCounter = 1;
        LinkedList<Rank> ranks = new LinkedList<>();

        for (Team team : allTeams) {
            long currentPoints = points(team);
            if (!ranks.isEmpty() && ranks.getLast().getPoints() != currentPoints) {
                rankCounter += 1;
            }
            ranks.add(new Rank(rankCounter, team.getName(), currentPoints));
        }
        return ranks;
    }

    private long points(Team team) {
        long winPoints = team.numberOfWins() * 3;
        long drawPoints = team.numberOfDraws();
        return winPoints + drawPoints;
    }


}
