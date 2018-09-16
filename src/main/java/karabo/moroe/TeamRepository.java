package karabo.moroe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamRepository {

    private Map<String, Team> teams;

    public TeamRepository() {
        this.teams = new HashMap<>();
    }

    public Team findOrCreate(String teamName) {
        if (!teams.containsKey(teamName)) {
            Team newTeam = new Team(teamName);
            teams.put(teamName, newTeam);
            return newTeam;
        }
        return teams.get(teamName);
    }

    public List<Team> findAll() {
        return new ArrayList<>(teams.values());
    }
}
