package karabo.moroe;

import java.util.Optional;

public class Game {

    private Team homeTeam;
    private int homeTeamScore;

    private Team awayTeam;
    private int awayTeamScore;

    public Game(Team homeTeam, int homeTeamScore, Team awayTeam, int awayTeamScore) {
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new IllegalArgumentException("Soccer scores cannot be negative");
        }
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public boolean isDraw() {
        return homeTeamScore == awayTeamScore;
    }

    public Optional<Team> winner() {
        if (isDraw()) {
            return Optional.empty();
        } else if (homeTeamScore > awayTeamScore) {
            return Optional.of(homeTeam);
        } else {
            return Optional.of(awayTeam);
        }
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }
}
