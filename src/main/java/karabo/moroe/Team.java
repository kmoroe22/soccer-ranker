package karabo.moroe;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class Team {

    private String name;
    private Collection<Game> games;

    public Team(String name) {
        this.name = name;
        games = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public long numberOfWins() {
        return games.stream().filter(a -> a.winner().filter(b -> b.equals(this)).isPresent()).count();
    }

    public long numberOfDraws() {
        return games.stream().filter(Game::isDraw).count();
    }

    public long numberOfLosses() {
        return games.stream().filter(a -> a.winner().filter(b -> !b.equals(this)).isPresent()).count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
